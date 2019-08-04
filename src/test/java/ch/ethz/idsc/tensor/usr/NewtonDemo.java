// code by jph
package ch.ethz.idsc.tensor.usr;

import ch.ethz.idsc.tensor.ComplexScalar;
import ch.ethz.idsc.tensor.Parallelize;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.alg.Subdivide;
import ch.ethz.idsc.tensor.img.ArrayPlot;
import ch.ethz.idsc.tensor.img.ColorDataGradients;
import ch.ethz.idsc.tensor.io.AnimationWriter;
import ch.ethz.idsc.tensor.io.Export;
import ch.ethz.idsc.tensor.io.HomeDirectory;
import ch.ethz.idsc.tensor.lie.CirclePoints;
import ch.ethz.idsc.tensor.red.Nest;
import ch.ethz.idsc.tensor.sca.Arg;
import ch.ethz.idsc.tensor.sca.N;
import ch.ethz.idsc.tensor.sca.ScalarUnaryOperator;

/** inspired by Mathematica's documentation of Gamma */
/* package */ class NewtonDemo {
  private static final int RES = StaticHelper.GALLERY_RES;
  private static final int DEPTH = 2;
  private static final Tensor RE = Subdivide.of(-2, +2, RES - 1);
  private static final Tensor IM = Subdivide.of(-2, +2, RES - 1);
  // ---
  private final ScalarUnaryOperator scalarUnaryOperator;

  public NewtonDemo(Tensor coeffs) {
    scalarUnaryOperator = NewtonScalarMethod.polynomial(coeffs).iteration;
  }

  private Scalar function(int y, int x) {
    Scalar seed = ComplexScalar.of(RE.Get(x), IM.Get(y));
    return Arg.of(Nest.of(scalarUnaryOperator, N.DOUBLE.apply(seed), DEPTH));
  }

  static void _animation() throws Exception {
    try (AnimationWriter animationWriter = AnimationWriter.of(HomeDirectory.Pictures("newtondemo.gif"), 100)) {
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
    Export.of(HomeDirectory.Pictures(NewtonDemo.class.getSimpleName() + ".png"), image);
  }
}
// depth3
// Series.of(Tensors.vector(1, 5, 0, 1)), //
// Series.of(Tensors.vector(2, 1, 1))).iteration;
