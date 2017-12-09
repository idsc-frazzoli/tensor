// code by jph
package ch.ethz.idsc.tensor.mat;

import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;

/** helper functions used in {@link SingularValueDecompositionImpl} */
/* package */ enum StaticHelper {
  ;
  static void addScaled(int l, int cols, Tensor v, int i, int j, Scalar s) {
    for (int k = l; k < cols; ++k) {
      Scalar a = s.multiply(v.Get(k, i));
      v.set(x -> x.add(a), k, j);
    }
  }

  static void rotate(Tensor m, int length, Scalar c, Scalar s, int i, int j) {
    for (int k = 0; k < length; ++k) {
      Scalar x = m.Get(k, j);
      Scalar z = m.Get(k, i);
      m.set(x.multiply(c).add(z.multiply(s)), k, j);
      m.set(z.multiply(c).subtract(x.multiply(s)), k, i);
    }
  }
}
