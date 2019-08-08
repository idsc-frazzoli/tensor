// code by jph
package ch.ethz.idsc.tensor.usr;

import ch.ethz.idsc.tensor.ComplexScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.alg.Series;
import ch.ethz.idsc.tensor.img.ColorDataGradients;
import ch.ethz.idsc.tensor.red.Nest;
import ch.ethz.idsc.tensor.sca.Arg;
import ch.ethz.idsc.tensor.sca.Clips;
import ch.ethz.idsc.tensor.sca.ScalarUnaryOperator;

/** inspired by Mathematica's documentation of Gamma */
/* package */ class NewtonDemo extends BivariateEvaluation {
  private static final int DEPTH = 2;

  public static BivariateEvaluation of(Tensor coeffs) {
    return new NewtonDemo(coeffs);
  }

  // ---
  private final ScalarUnaryOperator scalarUnaryOperator;

  private NewtonDemo(Tensor coeffs) {
    super(Clips.absolute(2.0), Clips.absolute(2.0));
    scalarUnaryOperator = NewtonScalarMethod.polynomial(coeffs).iteration;
  }

  @Override
  protected Scalar function(Scalar re, Scalar im) {
    return Arg.of(Nest.of(scalarUnaryOperator, ComplexScalar.of(re, im), DEPTH));
  }

  public static void main(String[] args) throws Exception {
    StaticHelper.export(of(Tensors.vector(1, 5, 0, 1)), Series.class, ColorDataGradients.PARULA);
  }
}
// depth3
// Series.of(Tensors.vector(1, 5, 0, 1)), //
// Series.of(Tensors.vector(2, 1, 1))).iteration;
