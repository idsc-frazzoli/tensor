// code by jph
package ch.ethz.idsc.tensor;

/** @see ExactScalarQ */
public enum ExactTensorQ {
  ;
  /** @param tensor
   * @return true if all scalar entries in given tensor satisfy the predicate {@link ExactScalarQ#of(Tensor)} */
  public static boolean of(Tensor tensor) {
    return tensor.flatten(-1).allMatch(ExactScalarQ::of);
  }

  /** @param tensor
   * @return given tensor
   * @throws Exception if given tensor does not have all entries in exact precision */
  public static Tensor require(Tensor tensor) {
    if (of(tensor))
      return tensor;
    throw TensorRuntimeException.of(tensor);
  }
}
