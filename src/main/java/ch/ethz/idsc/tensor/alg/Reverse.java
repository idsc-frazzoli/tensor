// code by jph
package ch.ethz.idsc.tensor.alg;

import java.util.stream.IntStream;

import ch.ethz.idsc.tensor.Tensor;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/Reverse.html">Reverse</a> */
public enum Reverse {
  ;
  /** @param tensor
   * @return tensor with entries on first level reversed */
  public static Tensor of(Tensor tensor) {
    return Tensor.of(IntStream.range(0, tensor.length()).boxed() //
        .map(index -> tensor.get(tensor.length() - index - 1)));
  }

  /* package */ static Tensor all(Tensor tensor) {
    if (tensor.length() == -1)
      return tensor;
    return of(Tensor.of(tensor.flatten(0).map(Reverse::all)));
  }
}
