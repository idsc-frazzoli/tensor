// code by jph
package ch.ethz.idsc.tensor.io;

import java.io.File;

import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.mat.HilbertMatrix;

enum ExportDemo {
  ;
  public static void main(String[] args) throws Exception {
    File dir = new File("src/test/resources/io");
    if (!dir.isDirectory())
      throw new RuntimeException(dir.toString());
    Tensor matrix = HilbertMatrix.of(6, 8);
    Export.of(new File(dir, "hilbert6x8.tensor"), matrix);
  }
}
