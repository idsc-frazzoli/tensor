// code by jph
package ch.ethz.idsc.tensor.red;

import java.util.Optional;
import java.util.function.BinaryOperator;

import ch.ethz.idsc.tensor.Tensor;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/Fold.html">Fold</a> */
public enum Fold {
  ;
  /** @param binaryOperator
   * @param tensor
   * @return */
  public static Optional<Tensor> of(BinaryOperator<Tensor> binaryOperator, Tensor tensor) {
    return tensor.flatten(0).reduce(binaryOperator);
  }

  /** function same as of() except that stream is processed in parallel for associative binaryOperator
   * 
   * @param binaryOperator
   * @param tensor
   * @return */
  public static Optional<Tensor> parallel(BinaryOperator<Tensor> binaryOperator, Tensor tensor) {
    return tensor.flatten(0).parallel().reduce(binaryOperator);
  }
}
