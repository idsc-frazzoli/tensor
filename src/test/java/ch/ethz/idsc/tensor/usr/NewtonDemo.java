// code by jph
package ch.ethz.idsc.tensor.usr;

import ch.ethz.idsc.tensor.ComplexScalar;
import ch.ethz.idsc.tensor.DoubleScalar;
import ch.ethz.idsc.tensor.Parallelize;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.alg.Multinomial;
import ch.ethz.idsc.tensor.alg.Subdivide;
import ch.ethz.idsc.tensor.img.ArrayPlot;
import ch.ethz.idsc.tensor.img.ColorDataGradients;
import ch.ethz.idsc.tensor.io.Export;
import ch.ethz.idsc.tensor.red.Nest;
import ch.ethz.idsc.tensor.sca.Arg;
import ch.ethz.idsc.tensor.sca.N;
import ch.ethz.idsc.tensor.sca.ScalarUnaryOperator;
import ch.ethz.idsc.tensor.utl.UserHome;

/** inspired by Mathematica's documentation of Gamma */
enum NewtonDemo {
  ;
  // ---
  private static final int RES = StaticHelper.GALLERY_RES;
  private static final int DEPTH = 2;
  private static final Tensor RE = Subdivide.of(-2, +2, RES - 1);
  private static final Tensor IM = Subdivide.of(-2, +2, RES - 1);
  private static final ScalarUnaryOperator FUNCTION = //
      z -> z.subtract(Multinomial.horner(Tensors.vector(1, 5, 0, 1), z).divide(Multinomial.horner(Tensors.vector(5, 0, 3), z)));

  private static Scalar function(int y, int x) {
    Scalar seed = ComplexScalar.of(RE.Get(x), IM.Get(y));
    try {
      return Arg.of(Nest.of(FUNCTION, N.DOUBLE.apply(seed), DEPTH));
    } catch (Exception exception) {
      System.out.println("fail=" + seed);
    }
    return DoubleScalar.INDETERMINATE;
  }

  public static void main(String[] args) throws Exception {
    Tensor matrix = Parallelize.matrix(NewtonDemo::function, RES, RES);
    Export.of(UserHome.Pictures(NewtonDemo.class.getSimpleName() + ".png"), //
        ArrayPlot.of(matrix, ColorDataGradients.DENSITY));
  }
}
