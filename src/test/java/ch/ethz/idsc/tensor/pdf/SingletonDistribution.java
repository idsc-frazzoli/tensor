// code by jph
package ch.ethz.idsc.tensor.pdf;

import java.util.Random;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.qty.Boole;

/* package */ class SingletonDistribution implements DiscreteDistribution {
  private final int value;
  private final Scalar scalar;

  public SingletonDistribution(int value) {
    this.value = value;
    scalar = RealScalar.of(value);
  }

  @Override
  public Scalar at(Scalar x) {
    return Boole.of(x.equals(scalar));
  }

  @Override // from RandomVariateInterface
  public Scalar randomVariate(Random random) {
    return scalar;
  }

  @Override // from DiscreteDistribution
  public int lowerBound() {
    return Math.subtractExact(value, 3);
  }

  @Override // from DiscreteDistribution
  public Scalar p_equals(int n) {
    return Boole.of(n == value);
  }
}
