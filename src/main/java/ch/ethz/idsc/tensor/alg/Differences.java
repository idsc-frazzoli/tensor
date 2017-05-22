// code by jph
package ch.ethz.idsc.tensor.alg;

import java.util.stream.IntStream;

import ch.ethz.idsc.tensor.Tensor;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/Differences.html">Differences</a> */
/* package */ enum Differences {
  ;
  // ---
  /** @param tensor
   * @return */
  public static Tensor of(Tensor tensor) {
    // BinaryOperator<Tensor> binaryOperator = Tensor::subtract;
    return Tensor.of(IntStream.range(0, tensor.length() - 1).boxed() //
        .parallel() //
        .map(index -> tensor.get(index + 1).subtract(tensor.get(index))));
  }
}
