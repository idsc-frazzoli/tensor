// code by jph
package ch.ethz.idsc.tensor.usr;

import ch.ethz.idsc.tensor.Parallelize;
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
import ch.ethz.idsc.tensor.red.Norm2Squared;
import ch.ethz.idsc.tensor.utl.UserHome;

enum MandelbulbDemo {
  ;
  // ---
  private static final int RES = StaticHelper.GALLERY_RES;
  private static final int DEPTH = 40;
  private static final int EXPONENT = 8;
  private static final Tensor RE = Subdivide.of(0.0, +1.0, RES - 1);
  private static final Tensor IM = Subdivide.of(0.0, +1.0, RES - 1);
  private static final Scalar THRESHOLD = RealScalar.of(5.0);

  private static Scalar function(int y, int x) {
    final Tensor c = Tensors.of(RE.Get(x), IM.Get(y), RealScalar.of(.505));
    Tensor X = Tensors.vector(0, 0, 0);
    Scalar nrm = null;
    for (int index = 0; index < DEPTH; ++index) {
      X = NylanderPower.of(X.add(c), EXPONENT);
      if (Scalars.lessThan(THRESHOLD, Norm2Squared.ofVector(X)))
        return RealScalar.ZERO;
      if (index == 6)
        nrm = Norm2Squared.ofVector(X.add(c)); //
    }
    return nrm;
  }

  public static void main(String[] args) throws Exception {
    Tensor matrix = Parallelize.matrix(MandelbulbDemo::function, RES, RES);
    Export.of(UserHome.Pictures(MandelbulbDemo.class.getSimpleName() + ".png"), //
        ArrayPlot.of(matrix, ColorDataGradients.CLASSIC));
  }
}
