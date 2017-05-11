// code by jph
package ch.ethz.idsc.tensor.opt;

import java.util.List;

import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.TensorRuntimeException;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.alg.Transpose;
import ch.ethz.idsc.tensor.io.ExtractPrimitives;
import ch.ethz.idsc.tensor.sca.Ceiling;
import ch.ethz.idsc.tensor.sca.Floor;
import ch.ethz.idsc.tensor.sca.Increment;

/** multi-linear interpolation
 * 
 * valid input for a respective dimension d are in the closed interval
 * [0, Dimensions.of(tensor).get(d) - 1] */
public class LinearInterpolation implements Interpolation {
  /** @param tensor
   * @return */
  public static Interpolation of(Tensor tensor) {
    return new LinearInterpolation(tensor);
  }

  private final Tensor tensor;

  /* package */ LinearInterpolation(Tensor tensor) {
    this.tensor = tensor;
  }

  @Override
  public Tensor get(Tensor index) {
    if (index.isScalar())
      throw TensorRuntimeException.of(index);
    Tensor floor = Floor.of(index);
    Tensor above = Ceiling.of(index);
    Tensor width = above.subtract(floor).map(Increment.ONE);
    List<Integer> fromIndex = ExtractPrimitives.toListInteger(floor);
    List<Integer> dimensions = ExtractPrimitives.toListInteger(width);
    Tensor block = tensor.block(fromIndex, dimensions);
    Tensor weights = Transpose.of(Tensors.of( //
        above.subtract(index), //
        index.subtract(floor) //
    ));
    for (Tensor weight : weights)
      block = block.length() == 1 ? block.get(0) : weight.dot(block);
    return block;
  }

  @Override
  public Scalar Get(Tensor index) {
    return (Scalar) get(index);
  }
}
