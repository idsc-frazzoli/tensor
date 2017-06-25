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

/** Careful:
 * The constructor Mathematica::EmpiricalDistribution[data] has no direct equivalent in the tensor library.
 * 
 * The constructor here takes as input the unscaled pdf which is interpreted over the samples
 * 0, 1, 2, 3, ..., [length of unscaled pdf] - 1
 * 
 * <p>inspired by
 * <a href="https://reference.wolfram.com/language/ref/EmpiricalDistribution.html">EmpiricalDistribution</a> */
public class EmpiricalDistribution extends EvaluatedDiscreteDistribution implements CDF {
  /** @param unscaledPDF vector of non-negative weights over the numbers
   * [0, 1, 2, ... unscaledPDF.length() - 1]
   * @return */
  public static Distribution fromUnscaledPDF(Tensor unscaledPDF) {
    return new EmpiricalDistribution(unscaledPDF);
  }

  // ---
  private final Tensor pdf;
  private final Tensor cdf;

  private EmpiricalDistribution(Tensor unscaledPDF) {
    if (unscaledPDF.flatten(0) //
        .map(Scalar.class::cast) //
        .filter(scalar -> Scalars.lessThan(scalar, RealScalar.ZERO)) //
        .findAny() //
        .isPresent())
      throw TensorRuntimeException.of(unscaledPDF);
    Tensor accumulate = Accumulate.of(unscaledPDF);
    Scalar scale = Last.of(accumulate).Get().invert();
    pdf = unscaledPDF.multiply(scale);
    cdf = accumulate.multiply(scale);
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
    return cdf.length() - 1; // override probably not necessary
  }

  @Override // from AbstractDiscreteDistribution
  protected Scalar protected_p_equals(int n) {
    if (pdf.length() <= n)
      return RealScalar.ZERO;
    return pdf.Get(n);
  }

  @Override // from CDF
  public Scalar p_lessThan(Scalar x) {
    int index = Scalars.intValueExact(Ceiling.of(x.subtract(RealScalar.ONE)));
    if (index < 0)
      return RealScalar.ZERO;
    if (cdf.length() <= index)
      return RealScalar.ONE;
    return cdf.Get(index);
  }

  @Override // from CDF
  public Scalar p_lessEquals(Scalar x) {
    int index = Scalars.intValueExact(Floor.of(x));
    if (index < 0)
      return RealScalar.ZERO;
    if (cdf.length() <= index)
      return RealScalar.ONE;
    return cdf.Get(index);
  }
}
