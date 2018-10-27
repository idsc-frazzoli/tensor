// code by jph
package ch.ethz.idsc.tensor.alg;

import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Scalars;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.opt.TensorScalarFunction;
import ch.ethz.idsc.tensor.opt.TensorUnaryOperator;
import ch.ethz.idsc.tensor.red.Norm;

/** @throws Exception if input is not a vector
 * @throws Exception if vector contains Infinity, or NaN */
public class NormalizeUnlessZero extends Normalize {
  /** @param norm
   * @return operator that normalizes vectors using given norm unless given vector has length 0 */
  public static TensorUnaryOperator with(Norm norm) {
    return new NormalizeUnlessZero(norm::ofVector);
  }

  /** @param tensorScalarFunction
   * @return operator that normalizes vectors using given tensorScalarFunction unless given vector has length 0 */
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
