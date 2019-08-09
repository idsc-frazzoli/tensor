// code by jph
package ch.ethz.idsc.tensor;

import java.util.List;

/* package */ enum UnprotectEval {
  ;
  /** Wikipedia: In computer science, an in-place algorithm is an algorithm which transforms input
   * using no auxiliary data structure. However a small amount of extra storage space is allowed for
   * auxiliary variables. The input is usually overwritten by the output as the algorithm executes.
   * 
   * @param tensor into which given element is inserted at given index
   * @param element
   * @param index
   * @throws Exception if tensor is unmodifiable
   * @throws Exception if index is not from the range {0, 1, ..., tensor.length()} */
  /* package */ static void insert(Tensor tensor, Tensor element, int index) {
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
