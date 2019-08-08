// code by jph
package ch.ethz.idsc.tensor.usr;

import java.io.IOException;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.img.ColorDataGradients;
import ch.ethz.idsc.tensor.num.GaussScalar;
import ch.ethz.idsc.tensor.sca.Clips;

/* package */ enum GaussScalarDemo {
  ;
  private static final int PRIME = 719;
  private static final BivariateEvaluation BIVARIATE_EVALUATION = new BivariateEvaluation( //
      Clips.interval(1, PRIME - 1), //
      Clips.interval(1, PRIME - 1)) {
    @Override
    protected Scalar function(Scalar re, Scalar im) {
      GaussScalar x = GaussScalar.of(re.number().intValue(), PRIME);
      GaussScalar y = GaussScalar.of(im.number().intValue(), PRIME);
      return RealScalar.of(x.divide(y).number());
    }
  };

  public static void main(String[] args) throws IOException {
    StaticHelper.export(BIVARIATE_EVALUATION, GaussScalar.class, ColorDataGradients.STARRYNIGHT);
  }
}
