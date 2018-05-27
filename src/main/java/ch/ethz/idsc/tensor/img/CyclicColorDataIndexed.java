// code by jph
package ch.ethz.idsc.tensor.img;

import java.awt.Color;

import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.sca.Mod;

/** reference implementation of a {@link ColorDataIndexed} with cyclic indexing */
public class CyclicColorDataIndexed extends BaseColorDataIndexed {
  /** @param tensor with dimensions N x 4 where each row encodes {R, G, B, A}
   * @return */
  public static ColorDataIndexed create(Tensor tensor) {
    return new CyclicColorDataIndexed(tensor.copy());
  }
  // ---

  private final Mod mod;

  /** @param tensor with dimensions N x 4 where each row encodes {R, G, B, A} */
  protected CyclicColorDataIndexed(Tensor tensor) {
    super(tensor);
    mod = Mod.function(tensor.length());
  }

  @Override // from ColorDataIndexed
  public Color getColor(int index) {
    index %= colors.length;
    return colors[0 <= index ? index : index + colors.length];
  }

  @Override // from ColorDataIndexed
  public ColorDataIndexed deriveWithAlpha(int alpha) {
    return new CyclicColorDataIndexed(tableWithAlpha(alpha));
  }

  @Override // from BaseColorDataIndexed
  protected int toInt(Scalar scalar) {
    return mod.apply(scalar).number().intValue();
  }
}
