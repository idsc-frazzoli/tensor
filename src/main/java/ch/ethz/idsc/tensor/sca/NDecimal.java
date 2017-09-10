// code by jph
package ch.ethz.idsc.tensor.sca;

import java.math.MathContext;

import ch.ethz.idsc.tensor.Scalar;

/* package */ class NDecimal extends N {
  private final MathContext mathContext;

  NDecimal(MathContext mathContext) {
    this.mathContext = mathContext;
  }

  @Override
  public Scalar apply(Scalar scalar) {
    if (scalar instanceof NInterface) {
      NInterface nInterface = (NInterface) scalar;
      return nInterface.n(mathContext);
    }
    return scalar;
  }
}
