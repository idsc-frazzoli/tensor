// code by jph
package ch.ethz.idsc.tensor.alg;

import java.util.stream.Stream;

import ch.ethz.idsc.tensor.Tensor;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/Insert.html">Insert</a> */
public enum Insert {
  ;
  /** @param tensor
   * @param element
   * @param index
   * @return */
  public static Tensor of(Tensor tensor, Tensor element, int index) {
    return Tensor.of(Stream.concat( //
        tensor.extract(0, index).append(element).stream(), //
        tensor.extract(index, tensor.length()).stream()));
  }
}
