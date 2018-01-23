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
import ch.ethz.idsc.tensor.sca.Ceiling;
import ch.ethz.idsc.tensor.sca.Clip;
import ch.ethz.idsc.tensor.sca.Power;
import ch.ethz.idsc.tensor.sca.Sqrt;

/** bin size computations are inspired by Wikipedia:
 * <a href="https://en.wikipedia.org/wiki/Histogram">Histogram</a>
 * 
 * <p>The bin size computation works on samples of type {@link Quantity}. */
public enum BinningMethod implements Function<Tensor, Scalar> {
  /** Scott's normal reference rule:
   * chooses width based on {@link StandardDeviation}
   * Outliers have more influence on result than with Freedman-Diaconis.
   * The method typically yields a width larger than that determined by
   * IQR or SQRT. */
  VARIANCE() {
    @Override
    public Scalar apply(Tensor tensor) {
      return RationalScalar.of(7, 2).multiply(StandardDeviation.ofVector(tensor)).divide(cuberoot(tensor.length()));
    }
  },
  /** Freedman-Diaconis' choice:
   * chooses width based on {@link InterquartileRange} */
  IQR() {
    @Override
    public Scalar apply(Tensor tensor) {
      return RealScalar.of(2).multiply(InterquartileRange.of(tensor)).divide(cuberoot(tensor.length()));
    }
  },
  /** Rice Rule */
  RICE() {
    @Override
    public Scalar apply(Tensor tensor) {
      return division(tensor, Ceiling.FUNCTION.apply(RealScalar.of(2).multiply(cuberoot(tensor.length()))));
    }
  },
  /** Square-root choice:
   * data interval width divided by square root of the number of data points.
   * method is used by Excel histograms and many others.
   * The method typically yields a width smaller than determined by Scott's,
   * or Freedman-Diaconis formula */
  SQRT() {
    @Override
    public Scalar apply(Tensor tensor) {
      return division(tensor, Sqrt.of(RealScalar.of(tensor.length())));
    }
  };
  // ---
  // helper function
  private static Scalar cuberoot(int length) {
    return Power.of(length, RationalScalar.of(1, 3));
  }

  private static Scalar division(Tensor tensor, Scalar k) {
    return Clip.function( //
        tensor.stream().reduce(Min::of).get().Get(), //
        tensor.stream().reduce(Max::of).get().Get()).width().divide(k);
  }
}
