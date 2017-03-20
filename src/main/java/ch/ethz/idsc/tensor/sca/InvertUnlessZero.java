// code by jph
package ch.ethz.idsc.tensor.sca;

import java.util.function.Function;

import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.ZeroScalar;

/** @return scalar == zero ? zero : scalar.invert() */
public enum InvertUnlessZero implements Function<Scalar, Scalar> {
  function;
  // ---
  @Override
  public Scalar apply(Scalar scalar) {
    return scalar.equals(ZeroScalar.get()) ? ZeroScalar.get() : scalar.invert();
  }

  public static Tensor of(Tensor tensor) {
    return tensor.map(InvertUnlessZero.function);
  }
}
