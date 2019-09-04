// code by jph
package ch.ethz.idsc.tensor.alg;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.lie.TensorProduct;

/* package */ enum TestHelper {
  ;
  /** @param zeros
   * @return vector */
  static Tensor polynomial(Tensor zeros) {
    Tensor res = Tensors.of(zeros.Get(0).negate(), RealScalar.ONE);
    for (int index = 1; index < zeros.length(); ++index)
      res = TensorProduct.of(res, Tensors.of(zeros.Get(index).negate(), RealScalar.ONE));
    Tensor _res = res;
    Tensor coeffs = Array.zeros(zeros.length() + 1);
    Array.of(list -> {
      Scalar scalar = (Scalar) _res.get(list);
      coeffs.set(scalar::add, list.stream().mapToInt(i -> i).sum());
      return null;
    }, Dimensions.of(res));
    return coeffs;
  }
}
