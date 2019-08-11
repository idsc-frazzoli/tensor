// code by jph
// https://mathematica.stackexchange.com/questions/9167/adapt-colorfunction-in-array-plot
package ch.ethz.idsc.tensor.usr;

import ch.ethz.idsc.tensor.ComplexScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.img.ColorDataGradients;
import ch.ethz.idsc.tensor.opt.Pi;
import ch.ethz.idsc.tensor.red.Nest;
import ch.ethz.idsc.tensor.sca.ArcTan;
import ch.ethz.idsc.tensor.sca.Clips;
import ch.ethz.idsc.tensor.sca.Real;
import ch.ethz.idsc.tensor.sca.Sin;

/** inspired by mathematica's documentation of Gamma */
/* package */ enum SinDemo {
  ;
  static final BivariateEvaluation BIVARIATE_EVALUATION = new BivariateEvaluation( //
      Clips.absolute(Pi.VALUE), Clips.absolute(Pi.VALUE)) {
    @Override
    protected Scalar function(Scalar re, Scalar im) {
      Scalar seed = ComplexScalar.of(re, im);
      return Real.of(ArcTan.of(Nest.of(Sin.FUNCTION, seed, 2)));
    }
  };

  public static void main(String[] args) throws Exception {
    StaticHelper.export(BIVARIATE_EVALUATION, Sin.class, ColorDataGradients.SUNSET);
  }
}
