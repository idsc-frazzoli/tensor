// code by jph
// credit to spencer
package ch.ethz.idsc.tensor.pdf;

import java.util.Random;

import ch.ethz.idsc.tensor.RationalScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.TensorRuntimeException;
import ch.ethz.idsc.tensor.alg.VectorQ;
import ch.ethz.idsc.tensor.red.Total;
import ch.ethz.idsc.tensor.sca.Clip;

public class PoissonBinomialDistribution implements Distribution, //
    MeanInterface, RandomVariateInterface, VarianceInterface {
  /** Careful:
   * the current implementation uses the input parameter p_vector
   * by reference. That mean outside changes to to the given tensor
   * affect the functionality of the distribution.
   * 
   * @param p_vector with scalar entries in the interval [0, 1]
   * @return */
  public static Distribution of(Tensor p_vector) {
    VectorQ.elseThrow(p_vector);
    if (!p_vector.map(Clip.unit()).equals(p_vector))
      throw TensorRuntimeException.of(p_vector);
    return new PoissonBinomialDistribution(p_vector);
  }

  // ---
  private final Tensor p_vector;

  private PoissonBinomialDistribution(Tensor p_vector) {
    this.p_vector = p_vector;
  }

  @Override // from MeanInterface
  public Scalar mean() {
    return Total.of(p_vector).Get();
  }

  @Override // from RandomVariateInterface
  public Scalar randomVariate(Random random) {
    return RationalScalar.of(p_vector.stream() //
        .map(Scalar.class::cast) //
        .map(Scalar::number) //
        .map(Number::doubleValue) //
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
