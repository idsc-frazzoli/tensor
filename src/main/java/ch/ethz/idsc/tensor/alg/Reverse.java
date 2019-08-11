// code by jph
package ch.ethz.idsc.tensor.alg;

import java.util.stream.IntStream;

import ch.ethz.idsc.tensor.ScalarQ;
import ch.ethz.idsc.tensor.Tensor;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/Reverse.html">Reverse</a> */
public enum Reverse {
  ;
  /** Reverse[{a, b, c}] == {c, b, a}
   * 
   * Implementation consistent with Mathematica:
   * Reverse of a scalar is not defined
   * Reverse[ 3.14 ] throws an exception
   * 
   * @param tensor
   * @return tensor with entries on first level reversed
   * @throws Exception if tensor is a scalar */
  public static Tensor of(Tensor tensor) {
    ScalarQ.thenThrow(tensor);
    int last = tensor.length() - 1;
    return Tensor.of(IntStream.range(0, tensor.length()) //
        .map(index -> last - index) //
        .mapToObj(tensor::get));
  }

  /** @param tensor
   * @return tensor with entries on all levels reversed */
  public static Tensor all(Tensor tensor) {
    return ScalarQ.of(tensor) //
        ? tensor
        : of(Tensor.of(tensor.stream().map(Reverse::all)));
  }
}
