// code by jph
// credit to spencer
package ch.ethz.idsc.tensor.pdf;

import java.util.Random;

import ch.ethz.idsc.tensor.RationalScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.red.Total;
import ch.ethz.idsc.tensor.sca.Clips;

/** <a href="https://en.wikipedia.org/wiki/Poisson_binomial_distribution">wikipedia</a> */
public class PoissonBinomialDistribution implements Distribution, //
    MeanInterface, RandomVariateInterface, VarianceInterface {
  /** @param p_vector with scalar entries in the interval [0, 1]
   * @return
   * @throws Exception if any entry in given p_vector is outside the unit interval */
  public static Distribution of(Tensor p_vector) {
    return new PoissonBinomialDistribution(Tensor.of(p_vector.stream() //
        .map(Scalar.class::cast) //
        .map(Clips.unit()::requireInside)));
  }

  // ---
  private final Tensor p_vector;

  private PoissonBinomialDistribution(Tensor p_vector) {
    this.p_vector = p_vector;
  }

  @Override // from MeanInterface
  public Scalar mean() {
    return Total.ofVector(p_vector);
  }

  @Override // from RandomVariateInterface
  public Scalar randomVariate(Random random) {
    return RationalScalar.of(p_vector.stream() //
        .map(Scalar.class::cast) //
        .map(Scalar::number) //
        .mapToDouble(Number::doubleValue) //
        .filter(p -> random.nextDouble() < p) //
        .count(), 1);
  }

  @Override // from VarianceInterface
  public Scalar variance() {
    return p_vector.stream() //
        .map(Scalar.class::cast) //
        .map(p -> RealScalar.ONE.subtract(p).multiply(p)) //
        .reduce(Scalar::add) //
        .orElse(RealScalar.ZERO);
  }
}
