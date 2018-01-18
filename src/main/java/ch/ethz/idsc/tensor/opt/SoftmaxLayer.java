// code by jph
package ch.ethz.idsc.tensor.opt;

import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.alg.Normalize;
import ch.ethz.idsc.tensor.red.Norm;
import ch.ethz.idsc.tensor.sca.Exp;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/SoftmaxLayer.html">SoftmaxLayer</a> */
public enum SoftmaxLayer {
  ;
  /** @param vector
   * @return
   * @throws Exception if vector is empty */
  public static Tensor of(Tensor vector) {
    return Normalize.of(Exp.of(vector), Norm._1);
  }
}
