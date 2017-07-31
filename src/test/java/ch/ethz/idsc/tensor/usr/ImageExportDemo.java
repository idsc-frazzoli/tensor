// code by jph
package ch.ethz.idsc.tensor.usr;

import ch.ethz.idsc.tensor.GaussScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.io.Export;

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

  public static void main(String[] args) throws Exception {
    _im1();
    _im2();
  }
}
