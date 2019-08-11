// code by jph
package ch.ethz.idsc.tensor.usr;

import ch.ethz.idsc.tensor.ComplexScalar;
import ch.ethz.idsc.tensor.DoubleScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.img.ColorDataGradients;
import ch.ethz.idsc.tensor.red.Nest;
import ch.ethz.idsc.tensor.sca.Arg;
import ch.ethz.idsc.tensor.sca.Beta;
import ch.ethz.idsc.tensor.sca.Clips;

/** inspired by Mathematica's documentation of Beta */
/* package */ enum BetaDemo {
  ;
  private static final int DEPTH = 2;
  private static final BivariateEvaluation BIVARIATE_EVALUATION = new BivariateEvaluation( //
      Clips.absolute(2.0), //
      Clips.absolute(2.0)) {
    @Override
    protected Scalar function(Scalar re, Scalar im) {
      Scalar seed = ComplexScalar.of(re, im);
      try {
        return Arg.of(Nest.of(z -> Beta.of(z, z), seed, DEPTH));
      } catch (Exception exception) {
        // ---
      }
      return DoubleScalar.INDETERMINATE;
    }
  };

  public static void main(String[] args) throws Exception {
    StaticHelper.export(BIVARIATE_EVALUATION, Beta.class, ColorDataGradients.HUE);
  }
}
