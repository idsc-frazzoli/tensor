// code by jph
package ch.ethz.idsc.tensor.opt;

import java.util.List;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Scalars;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.Unprotect;
import ch.ethz.idsc.tensor.alg.Transpose;
import ch.ethz.idsc.tensor.io.Primitives;
import ch.ethz.idsc.tensor.sca.Ceiling;
import ch.ethz.idsc.tensor.sca.Clip;
import ch.ethz.idsc.tensor.sca.Floor;
import ch.ethz.idsc.tensor.sca.Increment;

/** multi-linear interpolation
 * 
 * <p>valid input for a respective dimension d are in the closed interval
 * <code>[0, Dimensions.of(tensor).get(d) - 1]</code>
 * 
 * <p>Remark: for scalar inverse linear interpolation use {@link Clip#rescale(Scalar)} */
public class LinearInterpolation extends AbstractInterpolation {
  /** @param tensor not instance of {@link Scalar}
   * @return
   * @throws Exception if tensor == null */
  public static Interpolation of(Tensor tensor) {
    return new LinearInterpolation(tensor);
  }

  // ---
  private final Tensor tensor;

  private LinearInterpolation(Tensor tensor) {
    this.tensor = Unprotect.references(tensor); // <- for fast block extraction
  }

  @Override // from Interpolation
  public Tensor get(Tensor index) {
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

  @Override // from Interpolation
  public Tensor at(Scalar index) {
    Scalar floor = Floor.FUNCTION.apply(index);
    Scalar remain = index.subtract(floor);
    int below = floor.number().intValue();
    if (Scalars.isZero(remain))
      return tensor.get(below);
    return Tensors.of(RealScalar.ONE.subtract(remain), remain) //
        .dot(tensor.extract(below, below + 2));
  }
}
