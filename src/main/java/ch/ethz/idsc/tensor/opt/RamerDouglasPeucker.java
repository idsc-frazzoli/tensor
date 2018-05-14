// code by jph
package ch.ethz.idsc.tensor.opt;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Scalars;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.TensorRuntimeException;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.Unprotect;
import ch.ethz.idsc.tensor.alg.Join;
import ch.ethz.idsc.tensor.alg.Last;
import ch.ethz.idsc.tensor.alg.MatrixQ;
import ch.ethz.idsc.tensor.red.Norm;

/** Quote from Wikipedia:
 * The algorithm is widely used in robotics to perform simplification and denoising
 * of range data acquired by a rotating range scanner.
 * In this field it is known as the split-and-merge algorithm and is attributed to Duda and Hart.
 * 
 * The expected complexity of this algorithm is O(n log n).
 * However, the worst-case complexity is O(n^2). */
public enum RamerDouglasPeucker {
  ;
  /** @param tensor
   * @param epsilon
   * @return */
  public static Tensor of(Tensor tensor, Scalar epsilon) {
    if (tensor.length() == 0)
      return Tensors.empty();
    MatrixQ.require(tensor);
    if (Unprotect.dimension1(tensor) == 2) {
      if (tensor.length() == 1)
        return tensor;
      return recur(tensor, epsilon);
    }
    throw TensorRuntimeException.of(tensor);
  }

  // helper function
  private static Tensor recur(Tensor tensor, Scalar epsilon) {
    if (tensor.length() == 2)
      return tensor;
    if (tensor.length() <= 1)
      throw TensorRuntimeException.of(tensor);
    Tensor first = tensor.get(0);
    Tensor last = Last.of(tensor);
    Tensor diff = last.subtract(first);
    Scalar norm = Norm._2.ofVector(diff);
    if (Scalars.isZero(norm)) // LONGTERM not sure what to do when |diff| == 0
      throw TensorRuntimeException.of(tensor);
    Tensor vector = diff.divide(norm);
    Tensor cross2 = Tensors.of(vector.Get(1).negate(), vector.Get(0));
    Scalar dmax = RealScalar.ZERO;
    int split = -1;
    for (int index = 1; index < tensor.length() - 1; ++index) {
      Tensor lever = tensor.get(index).subtract(first);
      Scalar dist = lever.dot(cross2).Get().abs();
      if (Scalars.lessThan(dmax, dist)) {
        dmax = dist;
        split = index;
      }
    }
    if (Scalars.lessThan(epsilon, dmax)) {
      Tensor lo = recur(tensor.extract(0, split + 1), epsilon);
      Tensor hi = recur(tensor.extract(split, tensor.length()), epsilon);
      return Join.of(lo.extract(0, lo.length() - 1), hi);
    }
    return Tensors.of(first, last);
  }
}
