// code by jph
// code from http://www.ics.uci.edu/~eppstein/numth/frap.c
package ch.ethz.idsc.tensor.sca;

import ch.ethz.idsc.tensor.RationalScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.red.Max;

/** Rationalize is <em>not<em> a substitute for {@link Round}, or {@link Floor}.
 * 
 * <code>
 * Rationalize.of(+11.5, 1) == +12
 * Rationalize.of(-11.5, 1) == -11
 * </code>
 * 
 * <p>inspired by
 * <a href="https://reference.wolfram.com/language/ref/Rationalize.html">Rationalize</a> */
public enum Rationalize {
  ;
  /* find rational approximation to given real number
   * David Eppstein / UC Irvine / 8 Aug 1993
   *
   * With corrections from Arno Formella, May 2008
   * usage: a.out r d
   * r is real number to approx
   * d is the maximum denominator allowed
   *
   * based on the theory of continued fractions
   * if x = a1 + 1/(a2 + 1/(a3 + 1/(a4 + ...)))
   * then best approximation is found by truncating this series
   * (with some adjustments in the last term).
   *
   * Note the fraction can be recovered as the first column of the matrix
   * ( a1 1 ) ( a2 1 ) ( a3 1 ) ...
   * ( 1 0 ) ( 1 0 ) ( 1 0 )
   * Instead of keeping the sequence of continued fraction terms,
   * we just keep the last partial product of these matrices. */
  /** @param realScalar for instance Math.PI, or 2./3.
   * @param max denominator
   * @return approximation of realScalar as RationalScalar with denominator bounded by max */
  private static RealScalar ofRealScalar(final RealScalar realScalar, final long max) {
    long m00 = 1; /* initialize matrix */
    long m01 = 0;
    long m10 = 0;
    long m11 = 1;
    double x = realScalar.number().doubleValue();
    long ain;
    // loop finding terms until denominator gets too big
    while (m10 * (ain = toLong(x)) + m11 <= max) {
      long tmp;
      tmp = m00 * ain + m01;
      m01 = m00;
      m00 = tmp;
      tmp = m10 * ain + m11;
      m11 = m10;
      m10 = tmp;
      if (x == ain)
        break; // AF: division by zero
      x = 1 / (x - ain);
      if (x > Long.MAX_VALUE)
        throw new IllegalArgumentException(); // AF: representation failure
    }
    // now remaining x is between 0 and 1/ai
    // approx as either 0 or 1/m where m is max that will fit in maxden
    // first try zero
    RealScalar sol0 = RationalScalar.of(m00, m10);
    // now try other possibility
    ain = (max - m11) / m10;
    RealScalar sol1 = RationalScalar.of(m00 * ain + m01, m10 * ain + m11);
    // System.out.println(realScalar + " -> " + sol0 + " " + sol1);
    final double err0 = sol0.subtract(realScalar).abs().number().doubleValue();
    final double err1 = sol1.subtract(realScalar).abs().number().doubleValue();
    if (err0 == err1)
      return Max.of(sol0, sol1); // ties rounding to positive infinity
    return err0 < err1 ? sol0 : sol1; // choose the one with less error
  }

  /** Returns the closest {@link RationalScalar} to the argument, with ties
   * rounding to positive infinity.
   * 
   * @param tensor for instance Math.PI, or 2./3.
   * @param max denominator
   * @return approximation of realScalar as RationalScalar with denominator bounded by max */
  public static Tensor of(Tensor tensor, long max) {
    return tensor.map(scalar -> ofRealScalar((RealScalar) scalar, max));
  }

  // function used in Rationalize
  private static long toLong(double val) {
    long floor = (long) val;
    return val < floor ? floor - 1 : floor;
  }
}
