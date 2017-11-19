// code by jph
package ch.ethz.idsc.tensor.io;

import ch.ethz.idsc.tensor.Tensor;

public enum StringScalarQ {
  ;
  /** @param tensor
   * @return true if given tensor is instance of {@link StringScalar} */
  public static boolean of(Tensor tensor) {
    return tensor instanceof StringScalar;
  }

  /** @param tensor
   * @return true if any scalar entries in given tensor satisfies the predicate {@link StringScalarQ#of(Tensor)} */
  public static boolean any(Tensor tensor) {
    return tensor.flatten(-1).anyMatch(StringScalarQ::of);
  }
}
