// code by jph
package ch.ethz.idsc.tensor.mat;

import java.util.function.Predicate;

import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.alg.MatrixQ;
import ch.ethz.idsc.tensor.opt.TensorUnaryOperator;
import ch.ethz.idsc.tensor.sca.Chop;

/** helper functions used in {@link SingularValueDecompositionImpl} */
// LONGTERM several implementations not as efficient as could be
/* package */ enum StaticHelper {
  ;
  /** predicate checks a matrix A for A - f(A) == 0
   * 
   * @param tensor
   * @param tensorUnaryOperator
   * @return */
  static boolean addId(Tensor tensor, Chop chop, TensorUnaryOperator tensorUnaryOperator) {
    return MatrixQ.of(tensor) //
        && chop.close(tensor, tensorUnaryOperator.apply(tensor));
  }

  /** predicate checks a matrix A for A . f(A) == Id
   * 
   * @param tensor
   * @param chop
   * @param tensorUnaryOperator
   * @return */
  static boolean dotId(Tensor tensor, Chop chop, TensorUnaryOperator tensorUnaryOperator) {
    return MatrixQ.of(tensor) //
        && chop.close(tensor.dot(tensorUnaryOperator.apply(tensor)), IdentityMatrix.of(tensor.length()));
  }

  // ---
  static boolean definite(Tensor tensor, Predicate<Scalar> predicate) {
    return MatrixQ.of(tensor) //
        && CholeskyDecomposition.of(tensor).diagonal().stream() //
            .map(Scalar.class::cast).allMatch(predicate);
  }

  // ---
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
