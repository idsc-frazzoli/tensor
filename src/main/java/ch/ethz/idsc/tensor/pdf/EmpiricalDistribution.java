// code by jph
package ch.ethz.idsc.tensor.pdf;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Scalars;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.TensorRuntimeException;
import ch.ethz.idsc.tensor.alg.Accumulate;
import ch.ethz.idsc.tensor.alg.Last;
import ch.ethz.idsc.tensor.alg.Range;
import ch.ethz.idsc.tensor.sca.Ceiling;
import ch.ethz.idsc.tensor.sca.Floor;
import ch.ethz.idsc.tensor.sca.Sign;

/** Careful:
 * The constructor Mathematica::EmpiricalDistribution[data] has no direct equivalent in the tensor library.
 * 
 * <p>The constructor of the tensor library EmpiricalDistribution takes as input
 * an unscaled pdf vector with scalar entries that are interpreted over the samples
 * <pre>
 * 0, 1, 2, 3, ..., [length of unscaled pdf] - 1
 * </pre>
 * 
 * <p>"unscaled" pdf means that the values in the input vector are not absolute probabilities,
 * but only proportional to the probabilities P[X == i] for i = 0, 1, 2, ... of the EmpiricalDistribution.
 * 
 * <p>An instance of EmpiricalDistribution supports the computation of variance via
 * {@link Expectation#variance(Distribution)}.
 * 
 * <p>Mathematica::HistogramDistribution has a <em>continuous</em> CDF.
 * In contrast, the CDF of Tensor::EmpiricalDistribution has discontinuities.
 * 
 * <p>inspired by
 * <a href="https://reference.wolfram.com/language/ref/EmpiricalDistribution.html">EmpiricalDistribution</a> */
public class EmpiricalDistribution extends EvaluatedDiscreteDistribution implements CDF {
  /** @param unscaledPDF vector of non-negative weights over the numbers
   * [0, 1, 2, ..., unscaledPDF.length() - 1]
   * @return */
  public static Distribution fromUnscaledPDF(Tensor unscaledPDF) {
    return new EmpiricalDistribution(unscaledPDF);
  }

  // ---
  private final Tensor pdf;
  private final Tensor cdf;

  private EmpiricalDistribution(Tensor unscaledPDF) {
    if (unscaledPDF.stream().map(Scalar.class::cast).anyMatch(Sign::isNegative))
      throw TensorRuntimeException.of(unscaledPDF);
    Tensor accumulate = Accumulate.of(unscaledPDF);
    Scalar scale = Last.of(accumulate).Get();
    pdf = unscaledPDF.divide(scale);
    cdf = accumulate.divide(scale);
    inverse_cdf_build();
  }

  @Override // from MeanInterface
  public Scalar mean() {
    return pdf.dot(Range.of(0, pdf.length())).Get();
  }

  @Override // from DiscreteDistribution
  public int lowerBound() {
    return 0;
  }

  @Override // from EvaluatedDiscreteDistribution
  protected int upperBound() {
    return cdf.length() - 1;
  }

  @Override // from AbstractDiscreteDistribution
  protected Scalar protected_p_equals(int n) {
    if (pdf.length() <= n)
      return RealScalar.ZERO;
    return pdf.Get(n);
  }

  @Override // from CDF
  public Scalar p_lessThan(Scalar x) {
    return cdf_get(Ceiling.FUNCTION.apply(x.subtract(RealScalar.ONE)));
  }

  @Override // from CDF
  public Scalar p_lessEquals(Scalar x) {
    return cdf_get(Floor.FUNCTION.apply(x));
  }

  // helper function
  private Scalar cdf_get(Scalar scalar) {
    int index = Scalars.intValueExact(scalar);
    if (index < 0)
      return RealScalar.ZERO;
    if (cdf.length() <= index)
      return RealScalar.ONE;
    return cdf.Get(index);
  }

  @Override // from Object
  public String toString() {
    return String.format("%s[%s]", getClass().getSimpleName(), pdf);
  }
}
