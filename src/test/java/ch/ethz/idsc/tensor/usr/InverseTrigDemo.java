// code by jph
package ch.ethz.idsc.tensor.usr;

import java.util.stream.Stream;

import ch.ethz.idsc.tensor.ComplexScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.img.ColorDataGradients;
import ch.ethz.idsc.tensor.sca.ArcCosh;
import ch.ethz.idsc.tensor.sca.ArcSinh;
import ch.ethz.idsc.tensor.sca.ArcTanh;
import ch.ethz.idsc.tensor.sca.Clips;
import ch.ethz.idsc.tensor.sca.Imag;
import ch.ethz.idsc.tensor.sca.Power;
import ch.ethz.idsc.tensor.sca.ScalarUnaryOperator;

/** inspired by Mathematica's documentation of DensityPlot */
/* package */ class InverseTrigDemo extends BivariateEvaluation {
  private static final int EXPONENT = 3;
  // ---
  private final ScalarUnaryOperator[] scalarUnaryOperators;

  InverseTrigDemo(ScalarUnaryOperator... scalarUnaryOperator) {
    super(Clips.absolute(2.0), Clips.absolute(2.0));
    this.scalarUnaryOperators = scalarUnaryOperator;
  }

  @Override
  protected Scalar function(Scalar re, Scalar im) {
    Scalar seed = Power.of(ComplexScalar.of(re, im), EXPONENT);
    return Stream.of(scalarUnaryOperators) //
        .map(scalarUnaryOperator -> scalarUnaryOperator.apply(seed)) //
        .map(Imag.FUNCTION) //
        .reduce(Scalar::add) //
        .get();
  }

  public static void main(String[] args) throws Exception {
    StaticHelper.export( //
        new InverseTrigDemo(ArcSinh.FUNCTION, ArcCosh.FUNCTION, ArcTanh.FUNCTION), //
        ArcCosh.class, ColorDataGradients.THERMOMETER);
  }
}
