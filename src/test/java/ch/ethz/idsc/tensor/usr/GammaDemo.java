// code by jph
package ch.ethz.idsc.tensor.usr;

import ch.ethz.idsc.tensor.ComplexScalar;
import ch.ethz.idsc.tensor.DoubleScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.img.ArrayPlot;
import ch.ethz.idsc.tensor.img.ColorDataGradients;
import ch.ethz.idsc.tensor.io.Export;
import ch.ethz.idsc.tensor.io.HomeDirectory;
import ch.ethz.idsc.tensor.red.Nest;
import ch.ethz.idsc.tensor.sca.Arg;
import ch.ethz.idsc.tensor.sca.Clips;
import ch.ethz.idsc.tensor.sca.Gamma;

/** inspired by Mathematica's documentation of Gamma */
/* package */ enum GammaDemo {
  ;
  private static final int DEPTH = 2;
  // ---
  public static final BivariateEvaluation BIVARIATE_EVALUATION = new BivariateEvaluation( //
      Clips.interval(-1.25, -0.6), //
      Clips.interval(-0.25, +0.25)) {
    @Override
    protected Scalar function(Scalar re, Scalar im) {
      Scalar seed = ComplexScalar.of(re, im);
      try {
        return Arg.of(Nest.of(Gamma.FUNCTION, seed, DEPTH));
      } catch (Exception exception) {
        System.out.println("fail=" + seed);
      }
      return DoubleScalar.INDETERMINATE;
    }
  };

  public static void main(String[] args) throws Exception {
    Tensor matrix = BIVARIATE_EVALUATION.image(192);
    Export.of(HomeDirectory.Pictures(GammaDemo.class.getSimpleName() + ".png"), //
        ArrayPlot.of(matrix, ColorDataGradients.HUE));
  }
}
