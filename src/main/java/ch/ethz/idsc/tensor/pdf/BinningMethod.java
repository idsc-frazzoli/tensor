// code by gjoel and jph
package ch.ethz.idsc.tensor.pdf;

import java.io.Serializable;
import java.util.function.Function;

import ch.ethz.idsc.tensor.RationalScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.red.InterquartileRange;
import ch.ethz.idsc.tensor.red.Max;
import ch.ethz.idsc.tensor.red.Min;
import ch.ethz.idsc.tensor.red.StandardDeviation;
import ch.ethz.idsc.tensor.sca.Power;
import ch.ethz.idsc.tensor.sca.Sqrt;

/** bin size computations are inspired by
 * <a href="https://en.wikipedia.org/wiki/Histogram">Histogram</a> on Wikipedia. */
public enum BinningMethod implements Function<Tensor, Scalar>, Serializable {
  /** chooses width based on {@link InterquartileRange} according to Freedman-Diaconis rule. */
  IQR() {
    @Override
    public Scalar apply(Tensor samples) {
      return RealScalar.of(2).multiply(InterquartileRange.of(samples)).divide(cuberoot(samples.length()));
    }
  }, //
  /** chooses width based on {@link StandardDeviation} according to Scott's rule.
   * Outliers have more influence on result than with Freedman-Diaconis. */
  VARIANCE() {
    @Override
    public Scalar apply(Tensor samples) {
      return RationalScalar.of(7, 2).multiply(StandardDeviation.ofVector(samples)).divide(cuberoot(samples.length()));
    }
  },
  /** square root of the number of data points in the sample.
   * method is used by Excel histograms and many others. */
  SQRT() {
    @Override
    public Scalar apply(Tensor samples) {
      Scalar max = samples.stream().reduce(Max::of).get().Get();
      Scalar min = samples.stream().reduce(Min::of).get().Get();
      return max.subtract(min).divide(Sqrt.of(RealScalar.of(samples.length())));
    }
  };
  // ---
  // helper function
  private static Scalar cuberoot(int length) {
    return Power.of(length, RationalScalar.of(1, 3));
  }
}
