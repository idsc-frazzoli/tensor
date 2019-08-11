// code by jph
package ch.ethz.idsc.tensor.img;

import java.awt.Color;

import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.sca.Mod;

/** reference implementation of a {@link ColorDataIndexed} with cyclic indexing */
public class CyclicColorDataIndexed extends BaseColorDataIndexed {
  /** @param tensor with dimensions N x 4 where each row encodes {R, G, B, A}
   * @return
   * @throws Exception if tensor is empty
   * @see StrictColorDataIndexed */
  public static ColorDataIndexed of(Tensor tensor) {
    return new CyclicColorDataIndexed(tensor.copy());
  }

  // ---
  private final Mod mod;

  /** @param tensor with dimensions N x 4 where each row encodes {R, G, B, A} */
  CyclicColorDataIndexed(Tensor tensor) {
    super(tensor);
    mod = Mod.function(tensor.length());
  }

  @Override // from ColorDataIndexed
  public Color getColor(int index) {
    return colors[Math.floorMod(index, colors.length)];
  }

  @Override // from ColorDataIndexed
  public ColorDataIndexed deriveWithAlpha(int alpha) {
    return new CyclicColorDataIndexed(tableWithAlpha(alpha));
  }

  @Override // from BaseColorDataIndexed
  int toInt(Scalar scalar) {
    return mod.apply(scalar).number().intValue();
  }
}
