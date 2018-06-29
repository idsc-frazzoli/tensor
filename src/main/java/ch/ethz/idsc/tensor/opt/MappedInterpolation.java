// code by jph
package ch.ethz.idsc.tensor.opt;

import java.util.Objects;
import java.util.stream.Collectors;

import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
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
  /** @param tensor non-null
   * @param function
   * @return
   * @throws Exception if given tensor is null */
  public static Interpolation of(Tensor tensor, TensorUnaryOperator function) {
    return new MappedInterpolation(tensor, function);
  }

  // ---
  private final Tensor tensor;
  private final TensorUnaryOperator function;

  /* package */ MappedInterpolation(Tensor tensor, TensorUnaryOperator function) {
    this.tensor = Objects.requireNonNull(tensor);
    this.function = Objects.requireNonNull(function);
  }

  @Override // from Interpolation
  public final Tensor get(Tensor index) {
    return tensor.get(function.apply(index).stream() //
        .map(Scalar.class::cast) //
        .map(Scalar::number) //
        .map(Number::intValue) //
        .collect(Collectors.toList()));
  }

  @Override // from Interpolation
  public final Tensor at(Scalar index) {
    return tensor.get(function.apply(index).Get().number().intValue());
  }
}
