// code by jph
package ch.ethz.idsc.tensor.io;

import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.TensorRuntimeException;

/** "MAT-File Format" by The MathWorks, Inc.
 * 
 * There is no simple way to parse a mat file:
 * a file may contain multiple arrays, structures and cells
 * moreover the data may be compressed.
 * 
 * The tensor library rejects complexity, therefore,
 * the format is not natively supported.
 * 
 * For export to MATLAB see {@link MatlabExport}.
 * But there is no import native provided by the
 * tensor library at the moment. */
@Deprecated
/* package */ enum MatFileFormat {
  ;
  public static byte[] of(Tensor tensor) {
    throw TensorRuntimeException.of(tensor);
  }

  public static Tensor parse(byte[] bytes) {
    throw new RuntimeException();
  }
}
