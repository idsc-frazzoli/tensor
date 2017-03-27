// code by jph
package ch.ethz.idsc.tensor.red;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Tensor;

class NormFrobenius extends RankAdapter<RealScalar> {
  @Override
  public RealScalar ofOther(Tensor tensor) {
    return Norm._2.of(Tensor.of(tensor.flatten(-1)));
  }
}
