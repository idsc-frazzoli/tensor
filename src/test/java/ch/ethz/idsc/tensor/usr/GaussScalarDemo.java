// code by jph
package ch.ethz.idsc.tensor.usr;

import java.io.IOException;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.img.ArrayPlot;
import ch.ethz.idsc.tensor.img.ColorDataGradients;
import ch.ethz.idsc.tensor.img.MeanFilter;
import ch.ethz.idsc.tensor.io.Export;
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
      return RealScalar.of(x.multiply(y).reciprocal().number());
    }
  };

  public static void main(String[] args) throws IOException {
    Tensor tensor = MeanFilter.min(BIVARIATE_EVALUATION.image(PRIME - 1), 2);
    Export.of(StaticHelper.image(GaussScalar.class), ArrayPlot.of(tensor, ColorDataGradients.STARRYNIGHT));
  }
}
