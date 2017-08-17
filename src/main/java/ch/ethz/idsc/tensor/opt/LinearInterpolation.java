// code by jph
package ch.ethz.idsc.tensor.opt;

import java.util.List;
import java.util.Objects;

import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.Unprotect;
import ch.ethz.idsc.tensor.alg.Transpose;
import ch.ethz.idsc.tensor.io.Primitives;
import ch.ethz.idsc.tensor.sca.Ceiling;
import ch.ethz.idsc.tensor.sca.Floor;
import ch.ethz.idsc.tensor.sca.Increment;

/** multi-linear interpolation
 * 
 * valid input for a respective dimension d are in the closed interval
 * [0, Dimensions.of(tensor).get(d) - 1] */
public class LinearInterpolation extends AbstractInterpolation {
  /** @param tensor
   * @return
   * @throws Exception if tensor == null */
  public static Interpolation of(Tensor tensor) {
    return new LinearInterpolation(tensor);
  }

  // ---
  private final Tensor tensor;

  private LinearInterpolation(Tensor tensor) {
    if (Objects.isNull(tensor))
      throw new RuntimeException();
    this.tensor = Unprotect.references(tensor); // <- for faster block extraction
  }

  @Override // from AbstractInterpolation
  protected final Tensor _get(Tensor index) {
    Tensor floor = Floor.of(index);
    Tensor above = Ceiling.of(index);
    Tensor width = above.subtract(floor).map(Increment.ONE);
    List<Integer> fromIndex = Primitives.toListInteger(floor);
    List<Integer> dimensions = Primitives.toListInteger(width);
    Tensor block = tensor.block(fromIndex, dimensions); // <- copy() for empty fromIndex and dimensions
    Tensor weights = Transpose.of(Tensors.of( //
        above.subtract(index), //
        index.subtract(floor)));
    for (Tensor weight : weights)
      block = block.length() == 1 ? block.get(0) : weight.dot(block);
    return block;
  }
}
