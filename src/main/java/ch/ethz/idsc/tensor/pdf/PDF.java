// code by jph
package ch.ethz.idsc.tensor.pdf;

import java.io.Serializable;

import ch.ethz.idsc.tensor.Scalar;

/** probability density function
 * 
 * <p>inspired by
 * <a href="https://reference.wolfram.com/language/ref/PDF.html">PDF</a> */
public interface PDF extends Serializable {
  /** @param distribution
   * @return probability density function */
  public static PDF of(Distribution distribution) {
    if (distribution instanceof PDF)
      return (PDF) distribution;
    if (distribution instanceof ContinuousDistribution)
      return new ContinuousPDF((ContinuousDistribution) distribution);
    throw new RuntimeException();
  }

  /** @param x
   * @return P(X == x), i.e. probability of random variable X == x */
  Scalar p_equals(Scalar x);
}
