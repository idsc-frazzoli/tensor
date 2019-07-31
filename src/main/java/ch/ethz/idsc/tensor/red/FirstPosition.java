// code by jph
package ch.ethz.idsc.tensor.red;

import java.util.OptionalInt;
import java.util.stream.IntStream;

import ch.ethz.idsc.tensor.ScalarQ;
import ch.ethz.idsc.tensor.Tensor;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/FirstPosition.html">FirstPosition</a> */
public enum FirstPosition {
  ;
  /** @param tensor non-null
   * @param element non-null
   * @return smallest index with tensor.get(index).equals(element) or OptionalInt.empty() */
  public static OptionalInt of(Tensor tensor, Tensor element) {
    ScalarQ.thenThrow(tensor);
    return IntStream.range(0, tensor.length()) //
        .filter(index -> element.equals(tensor.get(index))) //
        .findFirst();
  }
}
