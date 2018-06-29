// code by jph
// adapted from http://www.ics.uci.edu/~eppstein/numth/frap.c
package ch.ethz.idsc.tensor.sca;

import ch.ethz.idsc.tensor.IntegerQ;
import ch.ethz.idsc.tensor.RationalScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Scalars;
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
public class Rationalize implements ScalarUnaryOperator {
  /** @param max positive integer
   * @return {@link ScalarUnaryOperator} that returns the closest {@link RationalScalar} to the
   * given argument, with denominator less or equals to max, and ties rounding to positive infinity.
   * @throws Exception if max does not satisfy {@link IntegerQ}, or is negative */
  public static ScalarUnaryOperator withDenominatorLessEquals(Scalar max) {
    return new Rationalize(max);
  }

  /** @param max positive integer
   * @return {@link ScalarUnaryOperator} that returns the closest {@link RationalScalar} to the
   * given argument, with denominator less or equals to max, and ties rounding to positive infinity.
   * @throws Exception if max does not satisfy {@link IntegerQ}, or is negative */
  public static ScalarUnaryOperator withDenominatorLessEquals(long max) {
    return withDenominatorLessEquals(RealScalar.of(max));
  }
  // ---

  private final Scalar max;

  private Rationalize(Scalar max) {
    this.max = Sign.requirePositive(IntegerQ.require(max));
  }

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
  /** @param scalar for instance Math.PI, or 2./3.
   * @param max denominator
   * @return approximation of given scalar as {@link RationalScalar} with denominator bounded by max */
  @Override
  public Scalar apply(final Scalar scalar) {
    Scalar m00 = RealScalar.ONE; /* initialize matrix */
    Scalar m01 = RealScalar.ZERO;
    Scalar m10 = RealScalar.ZERO;
    Scalar m11 = RealScalar.ONE;
    // ---
    Scalar x = scalar;
    Scalar ain = Floor.FUNCTION.apply(x);
    // loop finding terms until denominator gets too big
    while (Scalars.lessEquals(affine(m10, m11, ain), max)) {
      Scalar tmp = affine(m00, m01, ain);
      m01 = m00;
      m00 = tmp;
      tmp = affine(m10, m11, ain);
      m11 = m10;
      m10 = tmp;
      if (x.equals(ain))
        break; // AF: division by zero
      x = x.subtract(ain).reciprocal();
      ain = Floor.FUNCTION.apply(x);
    }
    // ---
    // now remaining x is between 0 and 1/ai (?)
    // approx as either 0 or 1/m where m is max that will fit in maxden
    Scalar sol0 = m00.divide(m10); // first try zero
    Scalar sol1 = affine(sol0, m01, max.subtract(m11)).divide(max); // now try other possibility
    RationalScalar rs = (RationalScalar) sol1;
    if (Scalars.lessThan(max, RealScalar.of(rs.denominator())))
      return sol0;
    Scalar err0 = sol0.subtract(scalar).abs();
    Scalar err1 = sol1.subtract(scalar).abs();
    if (err0.equals(err1))
      return Max.of(sol0, sol1); // ties rounding to positive infinity
    return Scalars.lessThan(err0, err1) ? sol0 : sol1; // choose the one with less error
  }

  // helper function
  private static Scalar affine(Scalar m0, Scalar m1, Scalar x) {
    return m0.multiply(x).add(m1);
  }
}
