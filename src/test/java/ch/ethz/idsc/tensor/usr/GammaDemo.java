// code by jph
package ch.ethz.idsc.tensor.usr;

import java.io.File;

import ch.ethz.idsc.tensor.ComplexScalar;
import ch.ethz.idsc.tensor.DoubleScalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
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
  private static final int RES = 256;
  private static final int DEPTH = 2;
  private static final Tensor RE = Subdivide.of(-1.25, -0.6, RES - 1);
  private static final Tensor IM = Subdivide.of(-0.25, +0.25, RES - 1);

  public static Tensor fun(int x, int y) {
    try {
      return Arg.of(Nest.of(Gamma.FUNCTION, ComplexScalar.of(RE.Get(x), IM.Get(y)), DEPTH));
    } catch (Exception exception) {
    }
    return DoubleScalar.INDETERMINATE;
  }

  public static void main(String[] args) throws Exception {
    Tensor matrix = Tensors.matrix(GammaDemo::fun, RES, RES);
    Export.of(new File("/home/datahaki/Pictures/image.png"), //
        ArrayPlot.of(matrix, ColorDataGradients.CLASSIC));
  }
}
