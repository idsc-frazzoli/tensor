// code by jph
package ch.ethz.idsc.tensor.pdf;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.sca.AbsSquared;
import ch.ethz.idsc.tensor.sca.Exp;

enum StandardNormalDistribution implements Distribution, PDF, CDF {
  INSTANCE;
  // ---
  private static final Scalar den = RealScalar.of(0.398942280401432677939946059934);
  private static final Scalar div = RealScalar.of(-0.5);

  // ---
  @Override // from PDF
  public Scalar at(Scalar x) {
    return den.multiply(Exp.of(AbsSquared.of(x).multiply(div)));
  }

  @Override
  public Scalar p_lessThan(Scalar x) {
    return null; // TODO use lookup tables
  }

  @Override
  public Scalar p_lessEquals(Scalar x) {
    return p_lessThan(x);
  }
}
