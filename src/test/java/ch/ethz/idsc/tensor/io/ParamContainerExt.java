// code by jph
package ch.ethz.idsc.tensor.io;

import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;

class ParamContainerExt extends ParamContainer {
  public static final ParamContainerExt INSTANCE = TensorProperties.wrap(new ParamContainerExt()) //
      .set(ResourceData.properties("/io/ParamContainerExt.properties"));
  // ---
  public Tensor onlyInExt = Tensors.vector(1, 2, 3);
  @SuppressWarnings("unused")
  private Scalar _private;

  public ParamContainerExt() {
    string = "fromConstructor";
  }
}
