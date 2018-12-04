// code by jph
package ch.ethz.idsc.tensor.pdf;

import ch.ethz.idsc.tensor.Scalar;

/** The Triangular distribution is a special case of a trapezoidal distribution.
 * 
 * <p>inspired by
 * <a href="https://en.wikipedia.org/wiki/Triangular_distribution">Triangular_distribution</a> */
public enum TriangularDistribution {
  ;
  /** @param a
   * @param b
   * @param c
   * @return
   * @throws Exception unless a <= b <= c and a < c */
  public static Distribution of(Scalar a, Scalar b, Scalar c) {
    return TrapezoidalDistribution.of(a, b, b, c);
  }
}
