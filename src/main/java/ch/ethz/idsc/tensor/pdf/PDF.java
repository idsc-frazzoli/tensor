// code by jph
package ch.ethz.idsc.tensor.pdf;

import java.io.Serializable;

import ch.ethz.idsc.tensor.Scalar;

/** probability density function
 * 
 * {@link DiscreteDistribution}s typically implement the PDF interface
 * through the extension of {@link AbstractDiscreteDistribution}.
 * 
 * <p>inspired by
 * <a href="https://reference.wolfram.com/language/ref/PDF.html">PDF</a> */
public interface PDF extends Serializable {
  /** Example use:
   * PDF pdf = PDF.of(PoissonDistribution.of(RealScalar.of(2)));
   * Scalar probability = pdf.p_equals(RealScalar.of(3));
   * 
   * @param distribution
   * @return probability density function */
  public static PDF of(Distribution distribution) {
    if (distribution instanceof PDF)
      return (PDF) distribution;
    throw new RuntimeException();
  }

  /** "PDF.of(distribution).at(x)" corresponds to Mathematica::PDF[distribution, x]
   * 
   * for {@link DiscreteDistribution}, the function returns the
   * P(X == x), i.e. probability of random variable X == x
   * 
   * for {@link ContinuousDistribution}, the function returns the value
   * of the probability density function [which is <em>not</em> identical to P(X == x)]
   * 
   * @param x
   * @return */
  Scalar at(Scalar x);
}
