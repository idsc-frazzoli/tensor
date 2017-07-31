// code by jph
package ch.ethz.idsc.tensor.usr;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.img.ColorDataFunction;
import ch.ethz.idsc.tensor.img.DefaultColorDataGradient;
import ch.ethz.idsc.tensor.sca.Clip;

// EXPERIMENTAL
/* package */ class LumaColorDataFunction {
  private static final Tensor WEIGHTS = Tensors.vector(0.45, 0.50, 0.4, 0);
  private static final Clip CLIP = Clip.function(0, 255);
  // ---

  public static ColorDataFunction of(Tensor tensor) {
    Tensor nrm = Tensors.empty();
    Scalar max = Tensors.vector(96, 96, 96, 0).dot(WEIGHTS).Get();
    for (Tensor rgba : tensor) {
      Scalar w = rgba.dot(WEIGHTS).Get();
      Tensor color = rgba.multiply(max.divide(w));
      color.set(RealScalar.of(255), 3);
      System.out.println(rgba + " -> " + color);
      color = color.map(CLIP);
      nrm.append(color);
    }
    return DefaultColorDataGradient.of(tensor);
  }
}
