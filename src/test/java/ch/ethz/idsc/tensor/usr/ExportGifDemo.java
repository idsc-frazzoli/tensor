// code by jph
package ch.ethz.idsc.tensor.usr;

import java.io.IOException;

import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.io.Export;
import ch.ethz.idsc.tensor.utl.UserHome;

enum ExportGifDemo {
  ;
  public static void main(String[] args) throws IOException {
    Tensor matrix = Tensors.matrix((i, j) -> Tensors.vector(255 - i, j, 0, j < 128 ? 255 : i), 256, 256);
    Export.of(UserHome.file("redgreen.gif"), matrix);
  }
}
