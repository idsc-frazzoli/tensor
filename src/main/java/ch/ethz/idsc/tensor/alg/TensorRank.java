// code by jph
package ch.ethz.idsc.tensor.alg;

import ch.ethz.idsc.tensor.Tensor;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/TensorRank.html">TensorRank</a> */
public enum TensorRank {
  ;
  /** @return rank of this tensor */
  public static int of(Tensor tensor) {
    return Dimensions.of(tensor).size();
  }
}
