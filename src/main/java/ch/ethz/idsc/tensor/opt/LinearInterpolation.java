// code by jph
package ch.ethz.idsc.tensor.opt;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.TensorRuntimeException;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.alg.Transpose;
import ch.ethz.idsc.tensor.io.ExtractPrimitives;
import ch.ethz.idsc.tensor.sca.Floor;
import ch.ethz.idsc.tensor.sca.Plus;

/** multi-linear interpolation */
public class LinearInterpolation implements Interpolation {
  /** @param tensor
   * @return */
  public static Interpolation of(Tensor tensor) {
    return new LinearInterpolation(tensor);
  }

  final Tensor tensor;

  LinearInterpolation(Tensor tensor) {
    this.tensor = tensor;
  }

  // FIXME does not work at the last valid index, for instance index = {tensor.length()-1}
  @Override
  public Tensor get(Tensor index) {
    if (index.isScalar())
      throw TensorRuntimeException.of(index);
    final int length = index.length();
    Tensor floor = Floor.of(index);
    Tensor above = floor.map(Plus.ONE);
    List<Integer> fromIndex = ExtractPrimitives.toListInteger(floor);
    List<Integer> dimensions = IntStream.range(0, length).boxed() //
        .map(i -> 2).collect(Collectors.toList());
    Tensor block = tensor.block(fromIndex, dimensions);
    Tensor weights = Transpose.of(Tensors.of( //
        above.subtract(index), //
        index.subtract(floor) //
    ));
    for (Tensor weight : weights)
      block = weight.dot(block);
    return block;
  }
}
