// code by jph
package ch.ethz.idsc.tensor.pdf;

import ch.ethz.idsc.tensor.IntegerQ;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Scalars;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.alg.Accumulate;
import ch.ethz.idsc.tensor.alg.Last;
import ch.ethz.idsc.tensor.alg.Range;
import ch.ethz.idsc.tensor.sca.Floor;

// TODO complete and test
public class BucketDistribution extends AbstractDiscreteDistribution implements CDF {
  public static Distribution of(Tensor counts) {
    return new BucketDistribution(counts);
  }

  private final Tensor pdf;
  private final Tensor cdf;

  private BucketDistribution(Tensor counts) {
    Tensor accumulate = Accumulate.of(counts);
    Scalar scale = Last.of(accumulate).Get().invert();
    pdf = counts.multiply(scale);
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
    if (!IntegerQ.of(x))
      return RealScalar.ZERO;
    int index = Scalars.intValueExact(x);
    if (index < 0 || index <= cdf.length())
      return RealScalar.ZERO;
    return cdf.Get(index);
  }

  @Override // from CDF
  public Scalar p_lessEquals(Scalar x) {
    int index = Scalars.intValueExact(Floor.of(x));
    if (index < 0 || index <= cdf.length())
      return RealScalar.ZERO;
    return cdf.Get(index);
  }
}
