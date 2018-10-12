// code by jph
package ch.ethz.idsc.tensor.alg;

import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Scalars;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.opt.TensorScalarFunction;
import ch.ethz.idsc.tensor.opt.TensorUnaryOperator;

/** @throws Exception if input is not a vector
 * @throws Exception if vector contains Infinity, or NaN */
public class NormalizeUnlessZero extends Normalize {
  public static TensorUnaryOperator with(TensorScalarFunction tensorScalarFunction) {
    return new NormalizeUnlessZero(tensorScalarFunction);
  }

  // ---
  private NormalizeUnlessZero(TensorScalarFunction tensorScalarFunction) {
    super(tensorScalarFunction);
  }

  @Override // from Normalize
  public Tensor apply(Tensor vector) {
    Scalar norm = tensorScalarFunction.apply(vector); // throws exception if input is not a vector
    return Scalars.isZero(norm) //
        ? vector.copy()
        : normalize(vector, norm);
  }
}
