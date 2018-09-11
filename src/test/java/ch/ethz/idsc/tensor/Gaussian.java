// concept by njw
// adapted by jph
package ch.ethz.idsc.tensor;

import java.io.Serializable;
import java.math.MathContext;
import java.util.Objects;

import ch.ethz.idsc.tensor.pdf.Distribution;
import ch.ethz.idsc.tensor.pdf.MeanInterface;
import ch.ethz.idsc.tensor.pdf.NormalDistribution;
import ch.ethz.idsc.tensor.pdf.VarianceInterface;
import ch.ethz.idsc.tensor.sca.AbsSquared;
import ch.ethz.idsc.tensor.sca.N;
import ch.ethz.idsc.tensor.sca.NInterface;
import ch.ethz.idsc.tensor.sca.Sign;
import ch.ethz.idsc.tensor.sca.Sqrt;

/** Gaussian encodes the parameters of a NormalDistribution
 * which consist of mean and variance.
 * 
 * Gaussians do not strictly form a field. In particular,
 * reciprocal, and multiplication with another Gaussian
 * throw an exception.
 * 
 * implementation for demonstration purpose */
public class Gaussian extends AbstractScalar implements //
    MeanInterface, NInterface, VarianceInterface, Serializable {
  /** additive zero */
  private static final Scalar ZERO = of(RealScalar.ZERO, RealScalar.ZERO);

  /** @param mean
   * @param variance non-negative
   * @return */
  public static Scalar of(Scalar mean, Scalar variance) {
    return new Gaussian(mean, Sign.requirePositiveOrZero(variance));
  }

  /** see description above
   * 
   * @param mean
   * @param variance
   * @return */
  public static Scalar of(Number mean, Number variance) {
    return of(RealScalar.of(mean), RealScalar.of(variance));
  }

  // ---
  private final Scalar mean;
  private final Scalar variance; // sigma ^ 2

  private Gaussian(Scalar mean, Scalar variance) {
    this.mean = mean;
    this.variance = variance;
  }

  @Override
  public Scalar multiply(Scalar scalar) {
    if (scalar instanceof Gaussian)
      throw TensorRuntimeException.of(this, scalar);
    return of(mean.multiply(scalar), variance.multiply(AbsSquared.FUNCTION.apply(scalar)));
  }

  @Override
  public Scalar negate() {
    return of(mean.negate(), variance);
  }

  @Override
  public Scalar reciprocal() {
    throw TensorRuntimeException.of(this);
  }

  @Override
  public Scalar abs() {
    throw TensorRuntimeException.of(this);
  }

  @Override
  public Number number() {
    throw TensorRuntimeException.of(this);
  }

  @Override
  public Scalar zero() {
    return ZERO;
  }

  @Override
  protected Scalar plus(Scalar scalar) {
    if (scalar instanceof Gaussian) {
      Gaussian gaussian = (Gaussian) scalar;
      return of(mean.add(gaussian.mean), variance.add(gaussian.variance));
    }
    return of(mean.add(scalar), variance);
  }

  /***************************************************/
  @Override
  public Scalar n() {
    return of(N.DOUBLE.apply(mean), N.DOUBLE.apply(variance));
  }

  @Override
  public Scalar n(MathContext mathContext) {
    N n = N.in(mathContext.getPrecision());
    return of(n.apply(mean), n.apply(variance));
  }

  /***************************************************/
  @Override // from MeanInterface
  public Scalar mean() {
    return mean;
  }

  @Override // from VarianceInterface
  public Scalar variance() {
    return variance;
  }

  public Distribution distribution() {
    return NormalDistribution.of(mean, Sqrt.FUNCTION.apply(variance));
  }

  @Override
  public int hashCode() {
    return Objects.hash(mean, variance);
  }

  @Override
  public boolean equals(Object object) {
    if (object instanceof Gaussian) {
      Gaussian gaussian = (Gaussian) object;
      return mean.equals(gaussian.mean) && variance.equals(gaussian.variance);
    }
    return false;
  }

  @Override
  public String toString() {
    return mean + "~" + variance;
  }
}
