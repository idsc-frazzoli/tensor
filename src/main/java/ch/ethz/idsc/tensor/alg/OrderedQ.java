// code by jph
package ch.ethz.idsc.tensor.alg;

import java.util.Iterator;

import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.TensorRuntimeException;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/OrderedQ.html">OrderedQ</a> */
public enum OrderedQ {
  ;
  /** @param tensor
   * @return whether entries in tensor are non-decreasing */
  public static boolean of(Tensor tensor) {
    Iterator<Tensor> iterator = tensor.iterator();
    if (iterator.hasNext()) {
      Tensor prev = iterator.next();
      while (iterator.hasNext()) {
        Tensor next = iterator.next();
        @SuppressWarnings("unchecked")
        Comparable<Tensor> comparable = (Comparable<Tensor>) prev;
        if (0 < comparable.compareTo(next))
          return false;
        prev = next;
      }
    }
    return true;
  }

  /** @param tensor
   * @return given tensor
   * @throws Exception if two entries in given tensor are decreasing */
  public static Tensor require(Tensor tensor) {
    if (of(tensor))
      return tensor;
    throw TensorRuntimeException.of(tensor);
  }
}
