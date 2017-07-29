// code by jph
package ch.ethz.idsc.tensor.usr;

import java.io.File;

import ch.ethz.idsc.tensor.ComplexScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.alg.Subdivide;
import ch.ethz.idsc.tensor.img.ArrayPlot;
import ch.ethz.idsc.tensor.img.ColorDataGradients;
import ch.ethz.idsc.tensor.io.Export;
import ch.ethz.idsc.tensor.sca.ArcCosh;
import ch.ethz.idsc.tensor.sca.Imag;
import ch.ethz.idsc.tensor.sca.Power;
import ch.ethz.idsc.tensor.sca.ScalarUnaryOperator;

/** inspired by mathematica's documentation of DensityPlot */
enum InverseTrigDemo {
  ;
  // ---
  private static final ScalarUnaryOperator SUO = ArcCosh.FUNCTION;
  private static final int RES = 256;
  private static final int EXPONENT = 3;
  private static final Tensor RE = Subdivide.of(-2.0, +2.0, RES - 1);
  private static final Tensor IM = Subdivide.of(-2.0, +2.0, RES - 1);

  private static Scalar function(int x, int y) {
    return Imag.of(SUO.apply(Power.of(ComplexScalar.of(RE.Get(x), IM.Get(y)), EXPONENT)));
  }

  public static void main(String[] args) throws Exception {
    Tensor matrix = Tensors.matrix(InverseTrigDemo::function, RES, RES);
    Export.of(new File("/home/datahaki/Pictures/inversetrigdemo.png"), //
        ArrayPlot.of(matrix, ColorDataGradients.THERMOMETER));
  }
}
