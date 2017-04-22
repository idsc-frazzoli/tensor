// code by jph
package ch.ethz.idsc.tensor.opt;

import java.util.List;

import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.TensorRuntimeException;
import ch.ethz.idsc.tensor.alg.Dimensions;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/ConvexHull.html">ConvexHull</a> */
public enum ConvexHull {
  ;
  /** @param tensor of n x 2 coordinates
   * @return points in TODO WHAT? order with no 3 co-linear points */
  public static Tensor of(Tensor tensor) {
    if (!Dimensions.isArray(tensor))
      throw TensorRuntimeException.of(tensor);
    List<Integer> dims = Dimensions.of(tensor);
    if (dims.get(1) == 2)
      return new GrahamScan(tensor).getConvexHull();
    throw TensorRuntimeException.of(tensor);
  }
}
