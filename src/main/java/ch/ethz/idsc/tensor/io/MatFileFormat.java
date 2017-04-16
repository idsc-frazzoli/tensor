// code by jph
package ch.ethz.idsc.tensor.io;

import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.alg.Dimensions;

/** "MAT-File Format" by The MathWorks, Inc. */
/* package */ enum MatFileFormat {
  // TODO google for code that does the job
  ;
  // ---
  public static byte[] of(Tensor tensor) {
    Dimensions.of(tensor);
    return null;
  }

  public static Tensor of(byte[] bytes) {
    return null;
  }
}
