// code by jph
package ch.ethz.idsc.tensor.usr;

import java.io.File;

import ch.ethz.idsc.tensor.ComplexScalar;
import ch.ethz.idsc.tensor.DoubleScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Scalars;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.alg.Subdivide;
import ch.ethz.idsc.tensor.img.ArrayPlot;
import ch.ethz.idsc.tensor.img.ColorDataGradients;
import ch.ethz.idsc.tensor.io.Export;
import ch.ethz.idsc.tensor.sca.Abs;
import ch.ethz.idsc.tensor.sca.Arg;

enum MandelbrotDemo {
  ;
  // ---
  private static final int RES = 256;
  private static final int DEPTH = 20;
  private static final Tensor RE = Subdivide.of(-1.4, -1.0, RES - 1);
  private static final Tensor IM = Subdivide.of(+0.0, +0.4, RES - 1);
  private static final Scalar TWO = RealScalar.of(2.0);

  private static Scalar function(int x, int y) {
    final Scalar c = ComplexScalar.of(RE.Get(x), IM.Get(y));
    Scalar arg = null;
    Scalar z = c;
    for (int index = 0; index < DEPTH; ++index) {
      z = z.multiply(z).add(c);
      if (Scalars.lessThan(TWO, Abs.of(z)))
        return DoubleScalar.INDETERMINATE;
      if (index <= 6)
        arg = Arg.of(z);
    }
    return arg;
  }

  public static void main(String[] args) throws Exception {
    Tensor matrix = Tensors.matrix(MandelbrotDemo::function, RES, RES);
    Export.of(new File("/home/datahaki/Pictures/mandelbrotdemo.png"), //
        ArrayPlot.of(matrix, ColorDataGradients.RAINBOW));
  }
}
