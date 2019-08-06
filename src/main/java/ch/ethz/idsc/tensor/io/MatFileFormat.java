// code by jph
package ch.ethz.idsc.tensor.io;

import ch.ethz.idsc.tensor.Tensor;

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
 * There is no import from MATLAB built in the tensor library. */
/* package */ enum MatFileFormat {
  ;
  static byte[] of(Tensor tensor) {
    throw new UnsupportedOperationException();
  }

  static Tensor parse(byte[] bytes) {
    throw new UnsupportedOperationException();
  }
}
