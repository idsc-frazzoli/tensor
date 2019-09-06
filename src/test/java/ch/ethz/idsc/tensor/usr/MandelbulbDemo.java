// code by jph
package ch.ethz.idsc.tensor.usr;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Scalars;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.img.ColorDataGradients;
import ch.ethz.idsc.tensor.lie.NylanderPower;
import ch.ethz.idsc.tensor.red.Norm2Squared;
import ch.ethz.idsc.tensor.sca.Clips;

/* package */ enum MandelbulbDemo {
  ;
  private static final int DEPTH = 40;
  private static final int EXPONENT = 8;
  private static final Scalar THRESHOLD = RealScalar.of(5.0);
  private static final BivariateEvaluation BIVARIATE_EVALUATION = new BivariateEvaluation( //
      Clips.positive(1.0), //
      Clips.positive(1.0)) {
    @Override
    protected Scalar function(Scalar re, Scalar im) {
      Tensor c = Tensors.of(re, im, RealScalar.of(0.505));
      Tensor x = Tensors.vector(0.0, 0.0, 0.0);
      Scalar nrm = null;
      for (int index = 0; index < DEPTH; ++index) {
        x = NylanderPower.of(x.add(c), EXPONENT);
        if (Scalars.lessThan(THRESHOLD, Norm2Squared.ofVector(x)))
          return RealScalar.ZERO;
        if (index == 6)
          nrm = Norm2Squared.ofVector(x.add(c)); //
      }
      return nrm;
    }
  };

  public static void main(String[] args) throws Exception {
    StaticHelper.export(BIVARIATE_EVALUATION, NylanderPower.class, ColorDataGradients.CLASSIC);
  }
}
