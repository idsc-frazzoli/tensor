// code by jph
package ch.ethz.idsc.tensor.img;

import ch.ethz.idsc.tensor.Tensor;

/* package */ abstract class AbstractExtract {
  final Tensor tensor;
  final int radius;

  AbstractExtract(Tensor tensor, int radius) {
    this.tensor = tensor;
    this.radius = radius;
  }
}
