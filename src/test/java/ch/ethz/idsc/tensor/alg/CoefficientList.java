// code by jph
package ch.ethz.idsc.tensor.alg;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.lie.TensorProduct;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/CoefficientList.html">CoefficientList</a> */
/* package */ enum CoefficientList {
  ;
  /** The following equality holds up to permutation and numerical precision:
   * <pre>
   * Roots.of(CoefficientList.of(roots)) == roots
   * </pre>
   * 
   * @param roots vector of length 1, 2, or 3
   * @return coefficients of polynomial */
  public static Tensor of(Tensor roots) {
    Tensor box = linear(roots.Get(0));
    for (int index = 1; index < roots.length(); ++index)
      box = TensorProduct.of(box, linear(roots.Get(index)));
    Tensor _box = box;
    Tensor coeffs = Array.zeros(roots.length() + 1);
    Array.of(list -> {
      Scalar scalar = (Scalar) _box.get(list);
      coeffs.set(scalar::add, list.stream().mapToInt(i -> i).sum());
      return null;
    }, Dimensions.of(box));
    return coeffs;
  }

  private static Tensor linear(Scalar scalar) {
    return Tensors.of(scalar.negate(), RealScalar.ONE);
  }
}
