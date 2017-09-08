// code by jph
package ch.ethz.idsc.tensor;

import java.util.List;

/** Notice:
 * 
 * THE USE OF 'UNPROTECT' IN THE APPLICATION LAYER IS NOT RECOMMENDED !
 * 
 * <p>inspired by
 * <a href="https://reference.wolfram.com/language/ref/Unprotect.html">Unprotect</a> */
public enum Unprotect {
  ;
  /** @param tensor
   * @return
   * @throws Exception if tensor is a scalar, or first level entries do not have regular length */
  public static int dimension1(Tensor tensor) {
    TensorImpl impl = (TensorImpl) tensor;
    List<Tensor> list = impl.list;
    int length = list.get(0).length();
    if (list.stream().anyMatch(entry -> entry.length() != length))
      throw TensorRuntimeException.of(tensor);
    return length;
  }

  /** @param tensor
   * @return tensor that overrides functions block, extract for access by reference */
  public static Tensor references(Tensor tensor) {
    if (tensor instanceof UnmodifiableTensor)
      throw TensorRuntimeException.of(tensor);
    return ScalarQ.of(tensor) ? tensor : ViewTensor.wrap(tensor);
  }
}
