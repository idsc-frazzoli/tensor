// code by jph
package ch.ethz.idsc.tensor.pdf;

import java.util.Random;

import ch.ethz.idsc.tensor.DoubleScalar;
import ch.ethz.idsc.tensor.RationalScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.sca.AbsSquared;
import ch.ethz.idsc.tensor.sca.Exp;

/* package */ enum StandardNormalDistribution implements Distribution, //
    CDF, InverseCDF, PDF, RandomVariateInterface {
  INSTANCE;
  // ---
  private static final Scalar DEN = DoubleScalar.of(0.398942280401432677939946059934);
  private static final Scalar NEGATIVE_HALF = DoubleScalar.of(-0.5);
  private static final Scalar FACTOR = DoubleScalar.of(-Math.sqrt(0.5));
  private static final Scalar NEGATIVE_SRTQ2 = DoubleScalar.of(-Math.sqrt(2));
  private static final Scalar TWO = DoubleScalar.of(2.0);

  // ---
  @Override // from CDF
  public Scalar p_lessThan(Scalar x) {
    // 1/2 Erfc[-(x/Sqrt[2])]
    return Erfc.FUNCTION.apply(x.multiply(FACTOR)).multiply(RationalScalar.HALF);
  }

  @Override // from CDF
  public Scalar p_lessEquals(Scalar x) {
    return p_lessThan(x);
  }

  @Override // from InverseCDF
  public Scalar quantile(Scalar p) {
    return InverseErfc.FUNCTION.apply(p.multiply(TWO)).multiply(NEGATIVE_SRTQ2);
  }

  @Override // from PDF
  public Scalar at(Scalar x) {
    return DEN.multiply(Exp.FUNCTION.apply(AbsSquared.FUNCTION.apply(x).multiply(NEGATIVE_HALF)));
  }

  @Override // from RandomVariateInterface
  public Scalar randomVariate(Random random) {
    return DoubleScalar.of(random.nextGaussian());
  }
}
