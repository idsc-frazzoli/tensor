// code by jph
package ch.ethz.idsc.tensor.img;

import java.awt.Color;
import java.util.List;
import java.util.stream.Collectors;

import ch.ethz.idsc.tensor.NumberQ;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.sca.Mod;

/** reference implementation of a {@link ColorDataIndexed} with cyclic indexing */
public class CyclicColorDataIndexed implements ColorDataIndexed {
  /** @param tensor with dimensions N x 4 where each row encodes {R, G, B, A}
   * @return */
  public static ColorDataIndexed create(Tensor tensor) {
    return new CyclicColorDataIndexed(tensor.copy());
  }

  /** matrix with dimensions N x 4 where each row encodes {R, G, B, A} */
  private final Tensor tensor;
  private final Mod mod;
  private final List<Color> list;

  /** matrix with dimensions N x 4 where each row encodes {R, G, B, A} */
  /* package */ CyclicColorDataIndexed(Tensor tensor) {
    this.tensor = tensor;
    mod = Mod.function(tensor.length());
    list = tensor.stream().map(ColorFormat::toColor).collect(Collectors.toList());
  }

  @Override // from ScalarTensorFunction
  public Tensor apply(Scalar scalar) {
    return NumberQ.of(scalar) //
        ? tensor.get(mod.apply(scalar).number().intValue())
        : StaticHelper.transparent();
  }

  @Override // from ColorDataIndexed
  public Color getColor(int index) {
    index %= list.size();
    return list.get(0 <= index ? index : index + list.size());
  }

  @Override // from ColorDataIndexed
  public ColorDataIndexed deriveWithAlpha(int alpha) {
    Tensor tensor = this.tensor.copy();
    Scalar scalar = RealScalar.of(alpha);
    tensor.set(entry -> scalar, Tensor.ALL, 3);
    return new CyclicColorDataIndexed(tensor);
  }
}
