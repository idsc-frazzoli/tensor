// code by jph
package ch.ethz.idsc.tensor.pdf;

import java.util.Random;

import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.sca.Floor;

/** The probability density for value x in a histogram distribution is a piecewise constant function.
 * 
 * <p>inspired by
 * <a href="https://reference.wolfram.com/language/ref/HistogramDistribution.html">HistogramDistribution</a> */
public class HistogramDistribution implements Distribution, //
    PDF, RandomVariateInterface {
  /** Example:
   * HistogramDistribution[{10.2, 3.2, 11.5, 7.3, 3.8, 9.8}, 0, 2]
   * 
   * @param samples vector with scalar entries all greater or equal given minimum
   * @param min lower bound of all samples
   * @param width of bins over which to assume uniform distribution, i.e. constant PDF
   * @return */
  public static Distribution of(Tensor samples, Scalar min, Scalar width) {
    return new HistogramDistribution(samples, min, width);
  }

  // ---
  private final Distribution distribution;
  private final Scalar min;
  private final Scalar width;

  public HistogramDistribution(Tensor samples, Scalar min, Scalar width) {
    distribution = EmpiricalDistribution.fromUnscaledPDF( //
        BinCounts.of(samples.map(scalar -> scalar.subtract(min)), width));
    this.min = min;
    this.width = width;
  }

  @Override // from RandomVariateInterface
  public Scalar randomVariate(Random random) {
    return RandomVariate.of(distribution, random) //
        .add(RandomVariate.of(UniformDistribution.unit(), random)) //
        .multiply(width).add(min);
  }

  @Override
  public Scalar at(Scalar x) {
    return PDF.of(distribution).at(Floor.FUNCTION.apply(x.subtract(min).divide(width)));
  }
}
