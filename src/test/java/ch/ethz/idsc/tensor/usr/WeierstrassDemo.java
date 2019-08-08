// code by jph
package ch.ethz.idsc.tensor.usr;

import ch.ethz.idsc.tensor.DoubleScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.img.ColorDataGradients;
import ch.ethz.idsc.tensor.sca.Clips;
import ch.ethz.idsc.tensor.sca.Cos;
import ch.ethz.idsc.tensor.sca.Power;

/* package */ enum WeierstrassDemo {
  ;
  private static final int DEPTH = 10;
  private static final BivariateEvaluation INSTANCE = new BivariateEvaluation( //
      Clips.interval(0.25, 1.0), //
      Clips.interval(0.25, 1.0)) {
    @Override
    protected Scalar function(Scalar re, Scalar im) {
      Scalar s = DoubleScalar.of(0.0);
      // b = 7.0 has to be a positive odd integer
      for (int n = 0; n < DEPTH; ++n)
        s = s.add(Power.of(im, n).multiply(Cos.of(Power.of(7.0, n).multiply(DoubleScalar.of(Math.PI)).multiply(re))));
      return s;
    }
  };

  public static void main(String[] args) throws Exception {
    StaticHelper.export(INSTANCE, DoubleScalar.class, ColorDataGradients.ALPINE);
  }
}
