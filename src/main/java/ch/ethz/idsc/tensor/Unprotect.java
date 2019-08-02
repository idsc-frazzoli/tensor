// code by jph
package ch.ethz.idsc.tensor;

import java.util.List;
import java.util.stream.Stream;

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
   * @param list
   * @return tensor backed by given list
   * @see TableBuilder */
  public static Tensor using(List<Tensor> list) {
    return new TensorImpl(list);
  }

  /** THE USE OF THIS FUNCTION IN THE APPLICATION LAYER IS NOT RECOMMENDED !
   * 
   * @param tensors
   * @return It That Must Not Be Described
   * @see Tensors#of(Tensor...) */
  public static Tensor byRef(Tensor... tensors) {
    return Tensor.of(Stream.of(tensors));
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

  /** Wikipedia: In computer science, an in-place algorithm is an algorithm which transforms input
   * using no auxiliary data structure. However a small amount of extra storage space is allowed for
   * auxiliary variables. The input is usually overwritten by the output as the algorithm executes.
   * 
   * @param tensor into which given element is inserted at given index
   * @param element
   * @param index
   * @throws Exception if tensor is unmodifiable
   * @throws Exception if index is not from the range {0, 1, ..., tensor.length()} */
  public static void insert(Tensor tensor, Tensor element, int index) {
    list(tensor).add(index, element.copy());
  }

  /** @param tensor
   * @return list that is member of given tensor
   * @throws Exception if tensor is unmodifiable */
  /* package */ static List<Tensor> list(Tensor tensor) {
    if (tensor instanceof UnmodifiableTensor)
      throw TensorRuntimeException.of(tensor);
    TensorImpl impl = (TensorImpl) tensor;
    return impl.list;
  }
}
