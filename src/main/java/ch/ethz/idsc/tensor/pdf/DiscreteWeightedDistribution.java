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

/** DiscreteWeightedDistribution has no direct equivalent in Mathematica */
public class DiscreteWeightedDistribution extends AbstractDiscreteDistribution implements CDF {
  /** weights are non-negative
   * 
   * @param weights over the numbers [0, 1, 2, ... weights.length() - 1]
   * @return */
  public static Distribution of(Tensor weights) {
    return new DiscreteWeightedDistribution(weights);
  }

  // ---
  private final Tensor pdf;
  private final Tensor cdf;

  private DiscreteWeightedDistribution(Tensor weights) {
    if (weights.flatten(0) //
        .map(Scalar.class::cast) //
        .filter(scalar -> Scalars.lessThan(scalar, RealScalar.ZERO)) //
        .findAny() //
        .isPresent())
      throw TensorRuntimeException.of(weights);
    Tensor accumulate = Accumulate.of(weights);
    Scalar scale = Last.of(accumulate).Get().invert();
    pdf = weights.multiply(scale);
    cdf = accumulate.multiply(scale);
  }

  @Override // from Distribution
  public Scalar mean() {
    return pdf.dot(Range.of(0, pdf.length())).Get();
  }

  @Override // from Distribution
  public Scalar variance() {
    // TODO implement
    return null;
  }

  @Override // from DiscreteDistribution
  public int lowerBound() {
    return 0;
  }

  @Override // from PDF
  public Scalar p_equals(int n) {
    if (n < 0 || pdf.length() <= n)
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
