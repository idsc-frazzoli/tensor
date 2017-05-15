// code by jph
package ch.ethz.idsc.tensor.opt;

import java.util.function.Function;

import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.TensorRuntimeException;
import ch.ethz.idsc.tensor.io.ExtractPrimitives;
import ch.ethz.idsc.tensor.sca.Ceiling;
import ch.ethz.idsc.tensor.sca.Floor;
import ch.ethz.idsc.tensor.sca.Round;

/** interpolation maps a given tensor to an integer index via a user specified function.
 * 
 * common usage examples are:
 * {@link Round#of(Tensor)}
 * {@link Floor#of(Tensor)}
 * {@link Ceiling#of(Tensor)} */
public class MappedInterpolation implements Interpolation {
  /** @param tensor
   * @param function
   * @return */
  public static Interpolation of(Tensor tensor, Function<Tensor, Tensor> function) {
    return new MappedInterpolation(tensor, function);
  }

  private final Tensor tensor;
  private final Function<Tensor, Tensor> function;

  /* package */ MappedInterpolation(Tensor tensor, Function<Tensor, Tensor> function) {
    this.tensor = tensor;
    this.function = function;
  }

  @Override
  public final Tensor get(Tensor index) {
    if (index.isScalar())
      throw TensorRuntimeException.of(index);
    return tensor.get(ExtractPrimitives.toListInteger(function.apply(index)));
  }

  @Override
  public Scalar Get(Tensor index) {
    return (Scalar) get(index);
  }
}
