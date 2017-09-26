// code by jph
package ch.ethz.idsc.tensor.usr;

import ch.ethz.idsc.tensor.DoubleScalar;
import ch.ethz.idsc.tensor.Parallelize;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.alg.Subdivide;
import ch.ethz.idsc.tensor.img.ArrayPlot;
import ch.ethz.idsc.tensor.img.ColorDataGradients;
import ch.ethz.idsc.tensor.io.Export;
import ch.ethz.idsc.tensor.sca.Cos;
import ch.ethz.idsc.tensor.sca.Power;
import ch.ethz.idsc.tensor.utl.UserHome;

enum WeierstrassDemo {
  ;
  // ---
  private static final int RES = StaticHelper.GALLERY_RES;
  private static final int DEPTH = 10;
  private static final Tensor RE = Subdivide.of(0.25, 1, RES - 1);
  private static final Tensor IM = Subdivide.of(0.25, 1, RES - 1);

  private static Scalar function(int y, int x) {
    Scalar v = RE.Get(x);
    Scalar a = IM.Get(y);
    Scalar s = RealScalar.ZERO;
    // b = 7.0 has to be a positive odd integer
    for (int n = 0; n < DEPTH; ++n)
      s = s.add(Power.of(a, n).multiply(Cos.of(Power.of(7.0, n).multiply(DoubleScalar.of(Math.PI)).multiply(v))));
    return s;
  }

  public static void main(String[] args) throws Exception {
    Tensor matrix = Parallelize.matrix(WeierstrassDemo::function, RES, RES);
    Export.of(UserHome.Pictures(WeierstrassDemo.class.getSimpleName() + ".png"), //
        ArrayPlot.of(matrix, ColorDataGradients.ALPINE));
  }
}
