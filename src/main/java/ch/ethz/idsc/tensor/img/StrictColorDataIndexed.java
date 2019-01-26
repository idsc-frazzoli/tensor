// code by jph
package ch.ethz.idsc.tensor.img;

import java.awt.Color;

import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.TensorRuntimeException;
import ch.ethz.idsc.tensor.Tensors;

/** reference implementation of a {@link ColorDataIndexed} with strict indexing
 * 
 * color indices are required to be in the range 0, 1, ..., tensor.length() - 1 */
public class StrictColorDataIndexed extends BaseColorDataIndexed {
  /** @param tensor with dimensions N x 4 where each row encodes {R, G, B, A}
   * @return
   * @throws Exception if given tensor is empty */
  public static ColorDataIndexed create(Tensor tensor) {
    if (Tensors.isEmpty(tensor))
      throw TensorRuntimeException.of(tensor);
    return new StrictColorDataIndexed(tensor.copy());
  }

  // ---
  /** @param tensor with dimensions N x 4 where each row encodes {R, G, B, A} */
  protected StrictColorDataIndexed(Tensor tensor) {
    super(tensor);
  }

  @Override // from ColorDataIndexed
  public Color getColor(int index) {
    return colors[index];
  }

  @Override // from ColorDataIndexed
  public ColorDataIndexed deriveWithAlpha(int alpha) {
    return new StrictColorDataIndexed(tableWithAlpha(alpha));
  }

  @Override // from BaseColorDataIndexed
  protected int toInt(Scalar scalar) {
    return scalar.number().intValue();
  }
}
