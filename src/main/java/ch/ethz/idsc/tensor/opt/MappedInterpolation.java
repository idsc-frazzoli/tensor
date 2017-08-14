// code by jph
package ch.ethz.idsc.tensor.opt;

import java.util.function.Function;

import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.io.Primitives;
import ch.ethz.idsc.tensor.sca.Ceiling;
import ch.ethz.idsc.tensor.sca.Floor;
import ch.ethz.idsc.tensor.sca.Round;

/** interpolation maps a given tensor to an integer index via a user specified function.
 * 
 * common usage examples are:
 * {@link Round#of(Tensor)}
 * {@link Floor#of(Tensor)}
 * {@link Ceiling#of(Tensor)} */
public class MappedInterpolation extends AbstractInterpolation {
  /** @param tensor
   * @param function
   * @return */
  public static Interpolation of(Tensor tensor, Function<Tensor, Tensor> function) {
    return new MappedInterpolation(tensor, function);
  }

  // ---
  private final Tensor tensor;
  private final Function<Tensor, Tensor> function;

  /* package */ MappedInterpolation(Tensor tensor, Function<Tensor, Tensor> function) {
    if (tensor == null)
      throw new RuntimeException();
    this.tensor = tensor;
    this.function = function;
  }

  @Override // from AbstractInterpolation
  protected final Tensor _get(Tensor index) {
    return tensor.get(Primitives.toListInteger(function.apply(index)));
  }
}
