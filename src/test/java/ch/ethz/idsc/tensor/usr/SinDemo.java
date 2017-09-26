// code by jph
// https://mathematica.stackexchange.com/questions/9167/adapt-colorfunction-in-array-plot
package ch.ethz.idsc.tensor.usr;

import ch.ethz.idsc.tensor.Parallelize;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.alg.Subdivide;
import ch.ethz.idsc.tensor.img.ArrayPlot;
import ch.ethz.idsc.tensor.img.ColorDataGradients;
import ch.ethz.idsc.tensor.io.Export;
import ch.ethz.idsc.tensor.sca.Sin;
import ch.ethz.idsc.tensor.utl.UserHome;

/** inspired by mathematica's documentation of Gamma */
enum SinDemo {
  ;
  // ---
  private static final int RES = StaticHelper.GALLERY_RES;
  private static final Tensor RE = Subdivide.of(-3, 3, RES - 1);
  private static final Tensor IM = Subdivide.of(-3, 3, RES - 1);

  private static Scalar function(int y, int x) {
    Scalar sx = RE.Get(x);
    Scalar sy = IM.Get(y);
    return sx.add(Sin.of(Tensors.of(RealScalar.of(3), sy).dot(Tensors.of(sx, sy))));
  }

  public static void main(String[] args) throws Exception {
    Tensor matrix = Parallelize.matrix(SinDemo::function, RES, RES);
    Export.of(UserHome.Pictures(SinDemo.class.getSimpleName() + ".png"), //
        ArrayPlot.of(matrix, ColorDataGradients.SUNSET));
  }
}
