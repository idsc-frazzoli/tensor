// code by jph
package ch.ethz.idsc.tensor.pdf;

import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.qty.Quantity;

/** probability density function
 * 
 * {@link DiscreteDistribution}s typically implement the PDF interface
 * through the extension of {@link AbstractDiscreteDistribution}.
 * 
 * <p>inspired by
 * <a href="https://reference.wolfram.com/language/ref/PDF.html">PDF</a> */
public interface PDF {
  /** Example use:
   * PDF pdf = PDF.of(PoissonDistribution.of(RealScalar.of(2)));
   * Scalar probability = pdf.p_equals(RealScalar.of(3));
   * 
   * @param distribution
   * @return probability density function
   * @throws Exception if given distribution does not implement PDF */
  static PDF of(Distribution distribution) {
    return (PDF) distribution;
  }

  /** "PDF.of(distribution).at(x)" corresponds to Mathematica::PDF[distribution, x]
   * 
   * <p>For {@link DiscreteDistribution}, the function returns the
   * P(X == x), i.e. probability of random variable X == x
   * 
   * <p>For continuous distributions, the function
   * <ul>
   * <li>returns the value of the probability density function, which is <em>not</em> identical to P(X == x)]
   * <li>may return a scalar of type {@link Quantity} with a physical unit so that the integral {@link CDF}
   * results in a unit-less {@link Scalar}.
   * </ul>
   * 
   * @param x
   * @return */
  Scalar at(Scalar x);
}
