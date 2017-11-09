// code by gjoel
package ch.ethz.idsc.tensor.img;

import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.red.Mean;

public enum MeanFilter {
  ;

  public static Tensor of(Tensor vector, int radius) {
    return StaticHelper.apply(vector, radius, Mean::of);
  }
}
