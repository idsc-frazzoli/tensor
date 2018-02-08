// code by jph
package ch.ethz.idsc.tensor.pdf;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Scalars;
import ch.ethz.idsc.tensor.TensorRuntimeException;
import ch.ethz.idsc.tensor.sca.Ceiling;
import ch.ethz.idsc.tensor.sca.Clip;
import ch.ethz.idsc.tensor.sca.Floor;
import ch.ethz.idsc.tensor.sca.Log;
import ch.ethz.idsc.tensor.sca.Power;

/** Careful:
 * if parameter p is in exact precision, the probabilities may evaluate
 * to long exact fractions and slow down the computation.
 * 
 * <p>inspired by
 * <a href="https://reference.wolfram.com/language/ref/GeometricDistribution.html">GeometricDistribution</a> */
public class GeometricDistribution extends AbstractDiscreteDistribution implements CDF, VarianceInterface {
  /** @param p with 0 < p <= 1 denotes probability P(X==0) == p */
  public static Distribution of(Scalar p) {
    if (p.equals(RealScalar.ONE))
      return BinomialDistribution.of(0, p);
    if (Scalars.lessEquals(p, RealScalar.ZERO) || Scalars.lessEquals(RealScalar.ONE, p))
      throw TensorRuntimeException.of(p);
    return new GeometricDistribution(p);
  }

  // ---
  private final Scalar p;
  private final Scalar _1_p; // _1_p == 1 - p

  private GeometricDistribution(Scalar p) {
    this.p = p;
    this._1_p = RealScalar.ONE.subtract(p);
  }

  @Override // from MeanInterface
  public Scalar mean() {
    return p.reciprocal().subtract(RealScalar.ONE);
  }

  @Override // from VarianceInterface
  public Scalar variance() {
    return _1_p.divide(p.multiply(p));
  }

  @Override // from DiscreteDistribution
  public int lowerBound() {
    return 0;
  }

  @Override // from InverseCDF
  public Scalar quantile(Scalar p) {
    return protected_quantile(Clip.unit().requireInside(p));
  }

  @Override // from AbstractDiscreteDistribution
  protected Scalar protected_quantile(Scalar p) { // p shadows member, ok
    Scalar num = Log.FUNCTION.apply(RealScalar.ONE.subtract(p));
    Scalar den = Log.FUNCTION.apply(_1_p);
    return Floor.FUNCTION.apply(num.divide(den));
  }

  @Override // from AbstractDiscreteDistribution
  protected Scalar protected_p_equals(int n) {
    // PDF[GeometricDistribution[p], n] == (1 - p) ^ n * p
    return p.multiply(Power.of(_1_p, n));
  }

  @Override // from CDF
  public Scalar p_lessThan(Scalar x) {
    return Scalars.lessEquals(x, RealScalar.ZERO) //
        ? RealScalar.ZERO
        : RealScalar.ONE.subtract(Power.of(_1_p, Ceiling.FUNCTION.apply(x)));
  }

  @Override // from CDF
  public Scalar p_lessEquals(Scalar x) {
    return Scalars.lessThan(x, RealScalar.ZERO) //
        ? RealScalar.ZERO
        : RealScalar.ONE.subtract(Power.of(_1_p, RealScalar.ONE.add(Floor.FUNCTION.apply(x))));
  }

  @Override // from Object
  public String toString() {
    return String.format("%s[%s]", getClass().getSimpleName(), p);
  }
}
