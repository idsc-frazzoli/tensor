// code by jph
package ch.ethz.idsc.tensor.usr;

import ch.ethz.idsc.tensor.ComplexScalar;
import ch.ethz.idsc.tensor.DoubleScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.alg.Subdivide;
import ch.ethz.idsc.tensor.img.ArrayPlot;
import ch.ethz.idsc.tensor.img.ColorDataGradients;
import ch.ethz.idsc.tensor.io.Export;
import ch.ethz.idsc.tensor.red.Nest;
import ch.ethz.idsc.tensor.sca.Arg;
import ch.ethz.idsc.tensor.sca.Gamma;

/** inspired by mathematica's documentation of Gamma */
enum GammaDemo {
  ;
  // ---
  private static final int RES = StaticHelper.GALLERY_RES;
  private static final int DEPTH = 2;
  private static final Tensor RE = Subdivide.of(-1.25, -0.6, RES - 1);
  private static final Tensor IM = Subdivide.of(-0.25, +0.25, RES - 1);

  private static Scalar function(int x, int y) {
    Scalar seed = ComplexScalar.of(RE.Get(x), IM.Get(y));
    try {
      return Arg.of(Nest.of(Gamma.FUNCTION, seed, DEPTH));
    } catch (Exception exception) {
      System.out.println("fail=" + seed);
    }
    return DoubleScalar.INDETERMINATE;
  }

  public static void main(String[] args) throws Exception {
    // 3.90[s] serial vs. 2.43[s] parallel
    long tic = System.nanoTime();
    Tensor matrix = StaticHelper.parallel(GammaDemo::function, RES, RES);
    System.out.println((System.nanoTime() - tic) * 1e-9);
    Export.of(UserHome.Pictures("gammademo.png"), //
        ArrayPlot.of(matrix, ColorDataGradients.HUE));
  }
}
