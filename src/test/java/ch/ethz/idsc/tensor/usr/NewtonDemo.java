// code by jph
package ch.ethz.idsc.tensor.usr;

import ch.ethz.idsc.tensor.ComplexScalar;
import ch.ethz.idsc.tensor.DoubleScalar;
import ch.ethz.idsc.tensor.Parallelize;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.alg.Series;
import ch.ethz.idsc.tensor.alg.Subdivide;
import ch.ethz.idsc.tensor.img.ArrayPlot;
import ch.ethz.idsc.tensor.img.ColorDataGradients;
import ch.ethz.idsc.tensor.io.AnimationWriter;
import ch.ethz.idsc.tensor.io.Export;
import ch.ethz.idsc.tensor.lie.CirclePoints;
import ch.ethz.idsc.tensor.red.Nest;
import ch.ethz.idsc.tensor.sca.Arg;
import ch.ethz.idsc.tensor.sca.N;
import ch.ethz.idsc.tensor.sca.ScalarUnaryOperator;
import ch.ethz.idsc.tensor.utl.UserHome;

/** inspired by Mathematica's documentation of Gamma */
class NewtonDemo {
  private static final int RES = StaticHelper.GALLERY_RES;
  private static final int DEPTH = 2;
  private static final Tensor RE = Subdivide.of(-2, +2, RES - 1);
  private static final Tensor IM = Subdivide.of(-2, +2, RES - 1);
  // ---
  private final ScalarUnaryOperator COEFFS;
  private final ScalarUnaryOperator DERIVE;
  private final ScalarUnaryOperator FUNCTION;

  public NewtonDemo(Tensor coeffs) {
    COEFFS = Series.of(coeffs);
    DERIVE = Series.of(Multinomial.derivative(coeffs));
    FUNCTION = z -> z.subtract(COEFFS.apply(z).divide(DERIVE.apply(z)));
  }

  private Scalar function(int y, int x) {
    Scalar seed = ComplexScalar.of(RE.Get(x), IM.Get(y));
    try {
      return Arg.of(Nest.of(FUNCTION, N.DOUBLE.apply(seed), DEPTH));
    } catch (Exception exception) {
      System.out.println("fail=" + seed);
    }
    return DoubleScalar.INDETERMINATE;
  }

  static void _animation() throws Exception {
    try (AnimationWriter animationWriter = AnimationWriter.of(UserHome.Pictures("newtondemo.gif"), 100)) {
      for (Tensor s : CirclePoints.of(20)) {
        Scalar z = ComplexScalar.of(s.Get(0), s.Get(0));
        NewtonDemo newtonDemo = new NewtonDemo(Tensors.of(RealScalar.ONE, RealScalar.of(5), RealScalar.ZERO, z));
        Tensor matrix = Parallelize.matrix(newtonDemo::function, RES, RES);
        Tensor image = ArrayPlot.of(matrix, ColorDataGradients.PARULA);
        animationWriter.append(image);
      }
    }
    System.out.println("done");
  }

  public static void main(String[] args) throws Exception {
    NewtonDemo newtonDemo = new NewtonDemo(Tensors.vector(1, 5, 0, 1));
    Tensor matrix = Parallelize.matrix(newtonDemo::function, RES, RES);
    Tensor image = ArrayPlot.of(matrix, ColorDataGradients.PARULA);
    Export.of(UserHome.Pictures(NewtonDemo.class.getSimpleName() + ".png"), image);
  }
}
