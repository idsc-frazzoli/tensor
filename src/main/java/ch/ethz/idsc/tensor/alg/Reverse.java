// code by jph
package ch.ethz.idsc.tensor.alg;

import java.util.stream.IntStream;

import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.ScalarQ;
import ch.ethz.idsc.tensor.Tensor;

/** consistent with Mathematica:
 * Reverse of a scalar is not defined
 * Reverse[ 3.14 ] throws an exception
 * 
 * <p>inspired by
 * <a href="https://reference.wolfram.com/language/ref/Reverse.html">Reverse</a> */
public enum Reverse {
  ;
  /** Reverse[{a, b, c}] == {c, b, a}
   * 
   * @param tensor
   * @return tensor with entries on first level reversed
   * @throws Exception if tensor is a {@link Scalar} */
  public static Tensor of(Tensor tensor) {
    ScalarQ.thenThrow(tensor);
    int length = tensor.length();
    return Tensor.of(IntStream.range(0, length).map(index -> length - index - 1).mapToObj(tensor::get));
  }

  /** @param tensor
   * @return tensor with entries on all levels reversed */
  public static Tensor all(Tensor tensor) {
    if (ScalarQ.of(tensor))
      return tensor;
    return of(Tensor.of(tensor.stream().map(Reverse::all)));
  }
}
