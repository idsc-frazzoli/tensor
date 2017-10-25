// code by jph and gjoel
package ch.ethz.idsc.tensor.pdf;

import java.util.Random;

import ch.ethz.idsc.tensor.RationalScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.opt.LinearInterpolation;
import ch.ethz.idsc.tensor.qty.Quantity;
import ch.ethz.idsc.tensor.red.Min;
import ch.ethz.idsc.tensor.red.Quantile;
import ch.ethz.idsc.tensor.red.StandardDeviation;
import ch.ethz.idsc.tensor.sca.AbsSquared;
import ch.ethz.idsc.tensor.sca.Clip;
import ch.ethz.idsc.tensor.sca.Floor;
import ch.ethz.idsc.tensor.sca.Increment;
import ch.ethz.idsc.tensor.sca.Power;
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
 * <p>Two automatic bin size computations are provided: Freedman-Diaconis, and Scott.
 * 
 * <p>inspired by
 * <a href="https://reference.wolfram.com/language/ref/HistogramDistribution.html">HistogramDistribution</a> */
public class HistogramDistribution implements Distribution, //
    CDF, InverseCDF, MeanInterface, PDF, RandomVariateInterface, VarianceInterface {
  /** Example:
   * HistogramDistribution[{10.2, -1.6, 3.2, -0.4, 11.5, 7.3, 3.8, 9.8}, 2]
   * 
   * <p>The implementation also supports input of type {@link Quantity}.
   * 
   * @param samples vector
   * @param width of bins over which to assume uniform distribution, i.e. constant PDF
   * @return */
  public static Distribution of(Tensor samples, Scalar width) {
    return new HistogramDistribution(samples, width);
  }

  /** @param samples
   * @return */
  public static Distribution of(Tensor samples) {
    return freedman(samples);
  }

  /** chooses width based on inter-quantile range, IQR, according to Freedman-Diaconis rule.
   * 
   * @param samples with IQR > 0
   * @return
   * @throws Exception if IQR == 0 */
  public static Distribution freedman(Tensor samples) {
    Tensor quartiles = Quantile.of(samples, Tensors.vector(0.25, 0.75));
    Scalar den = Power.of(RealScalar.of(samples.length()), RationalScalar.of(1, 3));
    Scalar width = RealScalar.of(2).multiply(quartiles.Get(1).subtract(quartiles.Get(0))).divide(den);
    return of(samples, width);
  }

  /** chooses width based on {@link StandardDeviation} according to Scott's rule.
   * Outliers have more influence on result than with Freedman-Diaconis.
   * 
   * @param samples
   * @return */
  public static Distribution scott(Tensor samples) {
    Scalar den = Power.of(RealScalar.of(samples.length()), RationalScalar.of(1, 3));
    Scalar width = RationalScalar.of(7, 2).multiply(StandardDeviation.ofVector(samples)).divide(den);
    return of(samples, width);
  }

  // ---
  private final ScalarUnaryOperator discrete;
  private final ScalarUnaryOperator original;
  private final Distribution distribution;
  private final Scalar width;
  private final Scalar width_half;

  private HistogramDistribution(Tensor samples, Scalar width) {
    Scalar min = Floor.toMultipleOf(width).apply(samples.stream().reduce(Min::of).get().Get());
    discrete = scalar -> scalar.subtract(min).divide(width);
    original = scalar -> scalar.multiply(width).add(min);
    distribution = EmpiricalDistribution.fromUnscaledPDF(BinCounts.of(samples.map(discrete)));
    this.width = width;
    width_half = width.divide(RealScalar.of(2));
  }

  @Override // from PDF
  public Scalar at(Scalar x) {
    return PDF.of(distribution).at(Floor.FUNCTION.apply(discrete.apply(x)));
  }

  @Override // from MeanInterface
  public Scalar mean() {
    return original.apply(Expectation.mean(distribution)).add(width_half);
  }

  @Override // from CDF
  public Scalar p_lessThan(Scalar x) {
    Scalar xlo = discrete.apply(Floor.toMultipleOf(width).apply(x));
    Scalar ofs = Clip.function(xlo, Increment.ONE.apply(xlo)).rescale(discrete.apply(x));
    CDF cdf = CDF.of(distribution);
    return LinearInterpolation.of(Tensors.of(cdf.p_lessThan(xlo), cdf.p_lessEquals(xlo))).Get(Tensors.of(ofs));
  }

  @Override // from CDF
  public Scalar p_lessEquals(Scalar x) {
    return p_lessThan(x);
  }

  @Override // from InverseCDF
  public Scalar quantile(Scalar p) {
    Scalar x_floor = InverseCDF.of(distribution).quantile(p);
    CDF cdf = CDF.of(distribution);
    return original.apply(x_floor.add( //
        Clip.function(cdf.p_lessThan(x_floor), cdf.p_lessEquals(x_floor)).rescale(p)));
  }

  @Override // from RandomVariateInterface
  public Scalar randomVariate(Random random) {
    return original.apply(RandomVariate.of(distribution, random) //
        .add(RandomVariate.of(UniformDistribution.unit(), random)));
  }

  @Override // from VarianceInterface
  public Scalar variance() {
    return Expectation.variance(distribution).add(RationalScalar.of(1, 12)).multiply(AbsSquared.of(width));
  }

  /** @return width of each bin */
  // EXPERIMENTAL
  public Scalar width() {
    return width;
  }
}
