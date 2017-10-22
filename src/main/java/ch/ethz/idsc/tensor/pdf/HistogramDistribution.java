// code by jph
package ch.ethz.idsc.tensor.pdf;

import java.util.Random;

import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.qty.Quantity;
import ch.ethz.idsc.tensor.red.Min;
import ch.ethz.idsc.tensor.sca.Floor;
import ch.ethz.idsc.tensor.sca.ScalarUnaryOperator;

/** A histogram distribution approximates an unknown continuous distribution using
 * a collection of observed samples from the distribution.
 * 
 * <p>The current implementation is characterized by the following properties
 * <ul>
 * <li>the probability density for value x in of histogram distribution is a piecewise constant function, and
 * <li>the user-specified, constant width of each bin.
 * </ul>
 * 
 * <p>The implementation combines
 * {@link EmpiricalDistribution}, {@link BinCounts}, and {@link UniformDistribution#unit()}.
 * 
 * <p>Other approximation methods are possible and may be available in the future.
 * 
 * <p>inspired by
 * <a href="https://reference.wolfram.com/language/ref/HistogramDistribution.html">HistogramDistribution</a> */
public class HistogramDistribution implements Distribution, //
    PDF, RandomVariateInterface {
  /** Example:
   * HistogramDistribution[{10.2, 3.2, 11.5, 7.3, 3.8, 9.8}, 2]
   * 
   * <p>The implementation also supports input of type {@link Quantity}.
   * 
   * @param samples vector
   * @param width of bins over which to assume uniform distribution, i.e. constant PDF
   * @return */
  // TODO binning methods FreedmanDiaconis, Knuth, Scott, Sturges, Wand
  public static Distribution of(Tensor samples, Scalar width) {
    return new HistogramDistribution(samples, width);
  }

  // ---
  private final ScalarUnaryOperator interval_trf;
  private final Distribution distribution;
  private final Scalar min;
  private final Scalar width;

  private HistogramDistribution(Tensor samples, Scalar width) {
    this.min = Floor.toMultipleOf(width).apply(samples.stream().reduce(Min::of).get().Get());
    interval_trf = x -> x.subtract(min).divide(width);
    distribution = EmpiricalDistribution.fromUnscaledPDF(BinCounts.of(samples.map(interval_trf)));
    this.width = width;
  }

  @Override // from RandomVariateInterface
  public Scalar randomVariate(Random random) {
    return RandomVariate.of(distribution, random) //
        .add(RandomVariate.of(UniformDistribution.unit(), random)) //
        .multiply(width).add(min);
  }

  @Override // from PDF
  public Scalar at(Scalar x) {
    return PDF.of(distribution).at(Floor.FUNCTION.apply(interval_trf.apply(x)));
  }
}
