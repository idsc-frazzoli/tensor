// code by jph
package ch.ethz.idsc.tensor;

/** predicate that determines if a given tensor is instance of {@link Scalar} */
public enum ScalarQ {
  ;
  /** equivalent predicate is
   * {@code tensor.length() == Scalar.LENGTH}
   * 
   * @param tensor
   * @return true if tensor is instance of {@link Scalar} */
  public static boolean of(Tensor tensor) {
    return tensor instanceof Scalar;
  }

  /** @param tensor
   * @throws Exception if given tensor is an instance of {@link Scalar} */
  public static void thenThrow(Tensor tensor) {
    if (of(tensor))
      throw TensorRuntimeException.of(tensor);
  }
}
