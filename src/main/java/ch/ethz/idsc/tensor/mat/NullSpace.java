// code by jph
package ch.ethz.idsc.tensor.mat;

import java.util.stream.IntStream;

import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.alg.Transpose;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/NullSpace.html">NullSpace</a> */
public enum NullSpace {
  ;
  /** @param m
   * @return (cols - rank()) x cols matrix */
  public static Tensor of(Tensor m) {
    return of(SingularValueDecomposition.of(m));
  }

  public static Tensor of(SingularValueDecomposition svd) {
    double w_threshold = svd.getThreshold();
    Tensor vt = Transpose.of(svd.getV());
    return Tensor.of(IntStream.range(0, svd.getW().length()).boxed() //
        .filter(index -> svd.getW().Get(index).abs().number().doubleValue() < w_threshold) //
        .map(vt::get));
  }
}
