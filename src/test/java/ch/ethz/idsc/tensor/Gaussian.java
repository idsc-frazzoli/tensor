// concept by njw
// adapted by jph
package ch.ethz.idsc.tensor;

import java.math.MathContext;
import java.util.Objects;

import ch.ethz.idsc.tensor.pdf.Distribution;
import ch.ethz.idsc.tensor.pdf.NormalDistribution;
import ch.ethz.idsc.tensor.sca.AbsSquared;
import ch.ethz.idsc.tensor.sca.N;
import ch.ethz.idsc.tensor.sca.NInterface;
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
    NInterface {
  /** additive zero */
  private static final Scalar ZERO = of(RealScalar.ZERO, RealScalar.ZERO);

  /** [if variance equals 0, then given mean is returned]
   * 
   * @param mean
   * @param variance non-negative
   * @return */
  public static Scalar of(Scalar mean, Scalar variance) {
    if (Scalars.lessThan(variance, variance.zero()))
      throw TensorRuntimeException.of(mean, variance);
    // if (Scalars.isZero(variance))
    // return mean;
    return new Gaussian(mean, variance);
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
    return of(N.FUNCTION.apply(mean), N.FUNCTION.apply(variance));
  }

  @Override
  public Scalar n(MathContext mathContext) {
    return of(N.apply(mean, mathContext), N.apply(variance, mathContext));
  }

  /***************************************************/
  public Scalar mean() {
    return mean;
  }

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
