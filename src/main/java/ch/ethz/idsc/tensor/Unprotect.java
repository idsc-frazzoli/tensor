// code by jph
package ch.ethz.idsc.tensor;

import java.util.List;

/** The use of Unprotect in the application later is not recommended.
 * 
 * <p>inspired by
 * <a href="https://reference.wolfram.com/language/ref/Unprotect.html">Unprotect</a> */
public enum Unprotect {
  ;
  /** @param tensor
   * @return
   * @throws Exception if tensor is a scalar, or first level entries don't have regular length */
  public static int dimension1(Tensor tensor) {
    TensorImpl impl = (TensorImpl) tensor;
    List<Tensor> list = impl.list;
    int length = list.get(0).length();
    if (list.stream().anyMatch(entry -> entry.length() != length))
      throw TensorRuntimeException.of(tensor);
    return length;
  }
}
