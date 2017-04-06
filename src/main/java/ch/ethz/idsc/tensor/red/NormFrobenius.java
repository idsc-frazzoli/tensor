// code by jph
package ch.ethz.idsc.tensor.red;

import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;

class NormFrobenius extends RankAdapter<Scalar> {
  @Override
  public Scalar ofOther(Tensor tensor) {
    return Norm._2.of(Tensor.of(tensor.flatten(-1)));
  }
}
