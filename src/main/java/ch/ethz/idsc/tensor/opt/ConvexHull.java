// code by jph
package ch.ethz.idsc.tensor.opt;

import java.util.List;

import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.TensorRuntimeException;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.alg.Dimensions;
import ch.ethz.idsc.tensor.mat.MatrixQ;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/ConvexHull.html">ConvexHull</a> */
public enum ConvexHull {
  ;
  /** @param tensor of n x 2 coordinates
   * @return points in counter-clockwise order with no 3 co-linear points
   * careful: when (x,y) are taken as pixel coordinates, the ordering appears clockwise */
  public static Tensor of(Tensor tensor) {
    if (tensor.length() == 0)
      return Tensors.empty();
    if (!MatrixQ.of(tensor))
      throw TensorRuntimeException.of(tensor);
    List<Integer> dims = Dimensions.of(tensor);
    if (dims.get(1) == 2)
      return new GrahamScan(tensor).getConvexHull();
    throw TensorRuntimeException.of(tensor);
  }
}
