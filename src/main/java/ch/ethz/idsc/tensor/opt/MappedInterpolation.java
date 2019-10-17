// code by jph
package ch.ethz.idsc.tensor.opt;

import java.io.Serializable;
import java.util.Objects;
import java.util.stream.Collectors;

import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Scalars;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.sca.Ceiling;
import ch.ethz.idsc.tensor.sca.Floor;
import ch.ethz.idsc.tensor.sca.Round;
import ch.ethz.idsc.tensor.sca.ScalarUnaryOperator;

/** interpolation maps a given tensor to an integer index via a user specified function.
 * 
 * common usage examples are:
 * {@link Round#of(Tensor)}
 * {@link Floor#of(Tensor)}
 * {@link Ceiling#of(Tensor)} */
public class MappedInterpolation extends AbstractInterpolation implements Serializable {
  /** @param tensor non-null
   * @param function non-null
   * @return
   * @throws Exception if given tensor is null */
  public static Interpolation of(Tensor tensor, ScalarUnaryOperator function) {
    return new MappedInterpolation( //
        tensor, //
        Objects.requireNonNull(function));
  }

  // ---
  private final Tensor tensor;
  private final ScalarUnaryOperator function;

  /* package */ MappedInterpolation(Tensor tensor, ScalarUnaryOperator function) {
    this.tensor = Objects.requireNonNull(tensor);
    this.function = function;
  }

  @Override // from Interpolation
  public final Tensor get(Tensor index) {
    return tensor.get(index.stream() //
        .map(Scalar.class::cast) //
        .map(function) //
        .map(Scalars::intValueExact) //
        .collect(Collectors.toList()));
  }

  @Override // from Interpolation
  public final Tensor at(Scalar index) {
    return tensor.get(Scalars.intValueExact(function.apply(index)));
  }
}
