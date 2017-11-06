// code by gjoel and jph
package ch.ethz.idsc.tensor.pdf;

import java.util.function.Function;

import ch.ethz.idsc.tensor.RationalScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.qty.Quantity;
import ch.ethz.idsc.tensor.red.InterquartileRange;
import ch.ethz.idsc.tensor.red.Max;
import ch.ethz.idsc.tensor.red.Min;
import ch.ethz.idsc.tensor.red.StandardDeviation;
import ch.ethz.idsc.tensor.sca.Power;
import ch.ethz.idsc.tensor.sca.Sqrt;

/** bin size computations are inspired by Wikipedia:
 * <a href="https://en.wikipedia.org/wiki/Histogram">Histogram</a>
 * 
 * <p>The bin size computation works on samples of type {@link Quantity}. */
public enum BinningMethod implements Function<Tensor, Scalar> {
  /** chooses width based on {@link StandardDeviation} according to Scott's rule.
   * Outliers have more influence on result than with Freedman-Diaconis.
   * The method typically yields a width larger than that determined by
   * IQR or SQRT. */
  VARIANCE() {
    @Override
    public Scalar apply(Tensor tensor) {
      return RationalScalar.of(7, 2).multiply(StandardDeviation.ofVector(tensor)).divide(cuberoot(tensor.length()));
    }
  },
  /** chooses width based on {@link InterquartileRange} according to Freedman-Diaconis rule. */
  IQR() {
    @Override
    public Scalar apply(Tensor tensor) {
      return RealScalar.of(2).multiply(InterquartileRange.of(tensor)).divide(cuberoot(tensor.length()));
    }
  }, //
  /** square root of the number of data points in the given tensor.
   * method is used by Excel histograms and many others.
   * The method typically yields a width smaller than determined by Scott's,
   * or Freedman-Diaconis formula */
  SQRT() {
    @Override
    public Scalar apply(Tensor tensor) {
      Scalar max = tensor.stream().reduce(Max::of).get().Get();
      Scalar min = tensor.stream().reduce(Min::of).get().Get();
      return max.subtract(min).divide(Sqrt.of(RealScalar.of(tensor.length())));
    }
  };
  // ---
  // helper function
  private static Scalar cuberoot(int length) {
    return Power.of(length, RationalScalar.of(1, 3));
  }
}
