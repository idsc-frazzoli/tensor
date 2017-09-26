// code by jph
package ch.ethz.idsc.tensor.usr;

import ch.ethz.idsc.tensor.ComplexScalar;
import ch.ethz.idsc.tensor.DoubleScalar;
import ch.ethz.idsc.tensor.Parallelize;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.alg.Subdivide;
import ch.ethz.idsc.tensor.img.ArrayPlot;
import ch.ethz.idsc.tensor.img.ColorDataGradients;
import ch.ethz.idsc.tensor.io.Export;
import ch.ethz.idsc.tensor.red.Nest;
import ch.ethz.idsc.tensor.sca.Arg;
import ch.ethz.idsc.tensor.sca.Gamma;
import ch.ethz.idsc.tensor.utl.UserHome;

/** inspired by Mathematica's documentation of Gamma */
enum GammaDemo {
  ;
  // ---
  private static final int RES = StaticHelper.GALLERY_RES;
  private static final int DEPTH = 2;
  private static final Tensor RE = Subdivide.of(-1.25, -0.6, RES - 1);
  private static final Tensor IM = Subdivide.of(-0.25, +0.25, RES - 1);

  private static Scalar function(int y, int x) {
    Scalar seed = ComplexScalar.of(RE.Get(x), IM.Get(y));
    try {
      return Arg.of(Nest.of(Gamma.FUNCTION, seed, DEPTH));
    } catch (Exception exception) {
      System.out.println("fail=" + seed);
    }
    return DoubleScalar.INDETERMINATE;
  }

  public static void main(String[] args) throws Exception {
    Tensor matrix = Parallelize.matrix(GammaDemo::function, RES, RES);
    Export.of(UserHome.Pictures(GammaDemo.class.getSimpleName() + ".png"), //
        ArrayPlot.of(matrix, ColorDataGradients.HUE));
  }
}
