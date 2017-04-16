// code by jph
package ch.ethz.idsc.tensor.alg;

import ch.ethz.idsc.tensor.Tensor;

/** {@link Flatten} is for convenience to wrap the Stream<Tensor>
 * returned by Tensor::flatten into a {@link Tensor}.
 * 
 * <p>{@link Flatten} undoes the work of {@link Partition}.
 * 
 * <p>inspired by
 * <a href="https://reference.wolfram.com/language/ref/Flatten.html">Flatten</a> */
public enum Flatten {
  ;
  /** @param tensor
   * @param level
   * @return */
  public static Tensor of(Tensor tensor, int level) {
    return Tensor.of(tensor.flatten(level));
  }
}
