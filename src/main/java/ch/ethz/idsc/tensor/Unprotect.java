// code by jph
package ch.ethz.idsc.tensor;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import ch.ethz.idsc.tensor.io.TableBuilder;

/** Notice:
 * 
 * <b>THE USE OF 'UNPROTECT' IN THE APPLICATION LAYER IS NOT RECOMMENDED !</b>
 * 
 * <p>inspired by
 * <a href="https://reference.wolfram.com/language/ref/Unprotect.html">Unprotect</a> */
public enum Unprotect {
  ;
  /** THE USE OF THIS FUNCTION IN THE APPLICATION LAYER IS NOT RECOMMENDED !
   * 
   * @param initialCapacity non-negative
   * @return empty tensor that allows the initialCapacity-fold invocation of
   * {@link Tensor#append(Tensor)} before allocating more memory
   * @throws Exception if initialCapacity is negative */
  public static Tensor empty(int initialCapacity) {
    return new TensorImpl(new ArrayList<>(initialCapacity));
  }

  /** THE USE OF THIS FUNCTION IN THE APPLICATION LAYER IS NOT RECOMMENDED !
   * 
   * @return empty tensor with elements stored in a linked list
   * @see TableBuilder */
  public static Tensor emptyLinkedList() {
    return new TensorImpl(new LinkedList<>());
  }

  /** THE USE OF THIS FUNCTION IN THE APPLICATION LAYER IS NOT RECOMMENDED !
   * 
   * @param tensor
   * @return
   * @throws Exception if tensor is a scalar, or first level entries do not have regular length */
  public static int dimension1(Tensor tensor) {
    TensorImpl impl = (TensorImpl) tensor;
    List<Tensor> list = impl.list;
    int length = list.get(0).length();
    if (list.stream().skip(1).anyMatch(entry -> entry.length() != length))
      throw TensorRuntimeException.of(tensor);
    return length;
  }

  /** THE USE OF THIS FUNCTION IN THE APPLICATION LAYER IS NOT RECOMMENDED !
   * 
   * @param tensor
   * @return tensor that overrides functions
   * {@link Tensor#block(List, List)}, and
   * {@link Tensor#extract(int, int)}
   * for access by reference
   * @throws Exception if given tensor is unmodifiable, or an instance of {@link Scalar} */
  public static Tensor references(Tensor tensor) {
    if (tensor instanceof UnmodifiableTensor)
      throw TensorRuntimeException.of(tensor);
    return ViewTensor.wrap(tensor);
  }

  /** THE USE OF THIS FUNCTION IN THE APPLICATION LAYER IS NOT RECOMMENDED !
   * 
   * @param tensor
   * @return list that is member of given tensor
   * @throws Exception if tensor is unmodifiable */
  public static List<Tensor> list(Tensor tensor) {
    if (tensor instanceof UnmodifiableTensor)
      throw TensorRuntimeException.of(tensor);
    TensorImpl impl = (TensorImpl) tensor;
    return impl.list;
  }
}
