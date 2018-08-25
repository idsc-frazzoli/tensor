// code by jph
package ch.ethz.idsc.tensor.usr;

import java.io.File;
import java.io.IOException;
import java.util.function.UnaryOperator;

import ch.ethz.idsc.tensor.GaussScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.img.ArrayPlot;
import ch.ethz.idsc.tensor.img.ColorDataGradients;
import ch.ethz.idsc.tensor.io.Export;
import ch.ethz.idsc.tensor.io.Import;
import ch.ethz.idsc.tensor.utl.UserHome;

enum ImageExportDemo {
  ;
  public static void _im1() throws Exception {
    int n = 251;
    Export.of(UserHome.Pictures("image.png"), Tensors.matrix((i, j) -> GaussScalar.of(i * j, n), n, n));
  }

  public static void _im2() throws Exception {
    int n = 251;
    Export.of(UserHome.Pictures("image2.png"), Tensors.matrix((i, j) -> //
    Tensors.of(RealScalar.of(i), RealScalar.of(j), GaussScalar.of(i + 2 * j, n), GaussScalar.of(i * j, n)), n, n));
  }

  public static void _im3() throws Exception {
    int n = 251;
    Tensor matrix = Tensors.matrix((i, j) -> //
    GaussScalar.of(i + 14 * j + i * i + i * j * 3, n), n, n);
    // System.out.println(Pretty.of(Rescale.of(matrix)));
    UnaryOperator<Scalar> asd = s -> RealScalar.of(s.number());
    matrix.map(asd);
    Tensor image = ArrayPlot.of(matrix.map(asd), ColorDataGradients.AURORA);
    Export.of(UserHome.Pictures("image3.png"), image);
  }

  public static void jpg2gif() throws IOException {
    File file = UserHome.file("display.jpg");
    if (file.isFile()) {
      Tensor tensor = Import.of(file);
      Export.of(UserHome.file("display.jpg.gif"), tensor);
    }
  }

  public static void main(String[] args) throws Exception {
    _im1();
    _im2();
    _im3();
    jpg2gif();
  }
}
