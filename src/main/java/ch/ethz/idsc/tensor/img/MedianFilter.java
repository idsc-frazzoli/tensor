// code by gjoel
package ch.ethz.idsc.tensor.img;

import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.red.Median;

public enum MedianFilter {
  ;

  public static Tensor of(Tensor vector, int radius) {
    return StaticHelper.apply(vector, radius, Median::of);
  }
}
