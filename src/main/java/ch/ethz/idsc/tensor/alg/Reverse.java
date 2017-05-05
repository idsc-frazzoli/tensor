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
    int length = tensor.length();
    return Tensor.of(IntStream.range(0, length).map(index -> length - index - 1).boxed().map(tensor::get));
  }

  // TODO for now, function all is used for testing... what next?
  /* package */ static Tensor all(Tensor tensor) {
    if (tensor.isScalar())
      return tensor;
    return of(Tensor.of(tensor.flatten(0).map(Reverse::all)));
  }
}
