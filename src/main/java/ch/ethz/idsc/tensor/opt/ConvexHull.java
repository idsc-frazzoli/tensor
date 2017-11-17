// code by jph
package ch.ethz.idsc.tensor.opt;

import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.TensorRuntimeException;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.Unprotect;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/ConvexHull.html">ConvexHull</a> */
public enum ConvexHull {
  ;
  /** Careful: clusters of three points in numerical precision with cross product
   * of norm below 1e-15 are treated as a single coordinate.
   * 
   * <p>When {x, y} are taken as pixel coordinates, the ordering appears clockwise.
   * 
   * @param tensor of n x 2 coordinates
   * @return points in counter-clockwise order with no 3 co-linear points
   * @throws Exception for input of invalid format */
  public static Tensor of(Tensor tensor) {
    if (Tensors.isEmpty(tensor))
      return Tensors.empty();
    if (Unprotect.dimension1(tensor) == 2)
      return new GrahamScan(tensor).getConvexHull();
    throw TensorRuntimeException.of(tensor);
  }
}
