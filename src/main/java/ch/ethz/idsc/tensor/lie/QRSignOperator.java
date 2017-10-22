// code by jph
package ch.ethz.idsc.tensor.lie;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.sca.ScalarUnaryOperator;
import ch.ethz.idsc.tensor.sca.Sign;

/* package */ enum QRSignOperator implements ScalarUnaryOperator {
  STABILITY() {
    @Override
    public Scalar apply(Scalar xk) {
      return Sign.isPositive(xk) ? ONE_NEGATE : RealScalar.ONE;
    }
  }, //
  ORIENTATION() {
    @Override
    public Scalar apply(Scalar xk) {
      return ONE_NEGATE;
    }
  };
  // ---
  private static final Scalar ONE_NEGATE = RealScalar.ONE.negate();
}
