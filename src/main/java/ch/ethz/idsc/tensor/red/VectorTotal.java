// code by jph
package ch.ethz.idsc.tensor.red;

import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.opt.TensorScalarFunction;

/** Mathematica allows
 * Normalize[{-1, 2, 3}, Total] == {-1/4, 1/2, 3/4}
 * 
 * @param vector
 * @return sum of scalars in given vector or RealScalar.ZERO if given vector is empty
 * @throws Exception if given vector does not have all entries as scalar types */
public enum VectorTotal implements TensorScalarFunction {
  FUNCTION;
  // ---
  @Override
  public Scalar apply(Tensor vector) {
    return Total.of(vector).Get();
  }
}
