// code by jph
package ch.ethz.idsc.tensor.usr;

import ch.ethz.idsc.tensor.ComplexScalar;
import ch.ethz.idsc.tensor.DoubleScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Scalars;
import ch.ethz.idsc.tensor.img.ColorDataGradients;
import ch.ethz.idsc.tensor.sca.Abs;
import ch.ethz.idsc.tensor.sca.Arg;
import ch.ethz.idsc.tensor.sca.Clips;

/* package */ enum MandelbrotDemo {
  ;
  private static final int DEPTH = 20;
  private static final Scalar TWO = RealScalar.of(2.0);
  private static final BivariateEvaluation BIVARIATE_EVALUATION = new BivariateEvaluation( //
      Clips.interval(-1.4, -1.0), //
      Clips.interval(+0.0, +0.4)) {
    @Override
    protected Scalar function(Scalar re, Scalar im) {
      final Scalar c = ComplexScalar.of(re, im);
      Scalar arg = null;
      Scalar z = c;
      for (int index = 0; index < DEPTH; ++index) {
        z = z.multiply(z).add(c);
        if (Scalars.lessThan(TWO, Abs.of(z)))
          return DoubleScalar.INDETERMINATE;
        if (index <= 6)
          arg = Arg.of(z);
      }
      return arg;
    }
  };

  public static void main(String[] args) throws Exception {
    StaticHelper.export(BIVARIATE_EVALUATION, ComplexScalar.class, ColorDataGradients.RAINBOW);
  }
}
