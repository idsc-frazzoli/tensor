// code by jph
package ch.ethz.idsc.tensor.usr;

import java.io.File;

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
import ch.ethz.idsc.tensor.lie.NylanderPower;
import ch.ethz.idsc.tensor.red.Norm;

enum MandelbulbDemo {
  ;
  // ---
  private static final int RES = 256;
  private static final int DEPTH = 40;
  private static final int EXPONENT = 8;
  private static final Tensor RE = Subdivide.of(-1.0, +1.0, RES - 1);
  private static final Tensor IM = Subdivide.of(-1.0, +1.0, RES - 1);
  private static final Scalar THREE = RealScalar.of(5.0);

  private static Scalar function(int x, int y) {
    final Tensor c = Tensors.of(RE.Get(x), IM.Get(y), RealScalar.of(.45));
    Tensor X = Tensors.vector(0, 0, 0);
    Scalar nrm = null;
    for (int index = 0; index < DEPTH; ++index) {
      X = NylanderPower.of(X.add(c), EXPONENT);
      if (Scalars.lessThan(THREE, Norm._2SQUARED.of(X)))
        return DoubleScalar.INDETERMINATE;
      if (index == 2)
        nrm = Norm._2SQUARED.of(X.add(c)); //
    }
    return nrm;
  }

  public static void main(String[] args) throws Exception {
    Tensor matrix = Tensors.matrix(MandelbulbDemo::function, RES, RES);
    Export.of(new File("/home/datahaki/Pictures/mandelbulbdemo.png"), //
        ArrayPlot.of(matrix, ColorDataGradients.CLASSIC));
  }
}
