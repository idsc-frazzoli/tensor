// code by clruch
package ch.ethz.idsc.tensor.pdf;

import java.util.Random;

import ch.ethz.idsc.tensor.RationalScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Scalars;
import ch.ethz.idsc.tensor.TensorRuntimeException;
import ch.ethz.idsc.tensor.sca.Clip;
import ch.ethz.idsc.tensor.sca.Clips;
import ch.ethz.idsc.tensor.sca.Sqrt;

/** Characteristics of a trapezoidal distribution: the graph of the PDF resembles
 * a trapezoid which begins rising at a until b, has a plateau from b to c, and
 * then falls after c to point d.
 * 
 * <p>A special case is a triangular distribution where b == c. In that case,
 * the plateau has width zero.
 * 
 * <p>inspired by
 * <a href="https://en.wikipedia.org/wiki/Trapezoidal_distribution">TrapezoidalDistribution</a> */
public class TrapezoidalDistribution extends AbstractContinuousDistribution implements InverseCDF, MeanInterface {
  private static final Scalar _1_3 = RationalScalar.of(1, 3);

  /** @param a
   * @param b
   * @param c
   * @param d
   * @return
   * @throws Exception unless a <= b <= c <= d and a < d */
  public static Distribution of(Scalar a, Scalar b, Scalar c, Scalar d) {
    if (Scalars.lessThan(c, b) || Scalars.lessEquals(d, a))
      throw TensorRuntimeException.of(b, c);
    return new TrapezoidalDistribution(a, b, c, d);
  }

  // ---
  private final Clip clip;
  private final Scalar a;
  private final Scalar b;
  private final Scalar c;
  private final Scalar d;
  private final Scalar alpha_inv;
  private final Scalar alpha;

  private TrapezoidalDistribution(Scalar a, Scalar b, Scalar c, Scalar d) {
    clip = Clips.interval(a, d);
    this.a = a;
    this.b = clip.requireInside(b);
    this.c = clip.requireInside(c);
    this.d = d;
    alpha_inv = d.add(c).subtract(a).subtract(b);
    this.alpha = alpha_inv.reciprocal();
  }

  @Override // from CDF
  public Scalar p_lessThan(Scalar x) {
    if (Scalars.lessThan(x, a))
      return RealScalar.ZERO;
    if (Scalars.lessThan(x, b)) {
      Scalar term1 = RealScalar.ONE.divide(b.subtract(a));
      Scalar term2 = x.subtract(a).multiply(x.subtract(a));
      return alpha.multiply(term1).multiply(term2);
    }
    if (Scalars.lessEquals(x, c)) {
      Scalar term2 = x.add(x).subtract(a).subtract(b);
      return alpha.multiply(term2);
    }
    if (Scalars.lessThan(x, d)) {
      Scalar term1 = RealScalar.ONE.divide(d.subtract(c));
      Scalar term2 = d.subtract(x).multiply(d.subtract(x));
      return RealScalar.ONE.subtract(alpha.multiply(term1).multiply(term2));
    }
    return RealScalar.ONE;
  }

  @Override // from CDF
  public Scalar p_lessEquals(Scalar x) {
    return p_lessThan(x);
  }

  @Override // from PDF
  public Scalar at(Scalar x) {
    if (clip.isInside(x)) { // support is [a, d]
      Scalar two_alpha = alpha.add(alpha);
      if (Scalars.lessThan(x, b)) {
        Scalar term = x.subtract(a).divide(b.subtract(a));
        return two_alpha.multiply(term);
      }
      if (Scalars.lessEquals(x, c))
        return two_alpha;
      // here is case c < x <= d
      Scalar term = d.subtract(x).divide(d.subtract(c));
      return two_alpha.multiply(term);
    }
    return RealScalar.ZERO;
  }

  @Override // from RandomVariateInterface
  public Scalar randomVariate(Random random) {
    return quantile(RealScalar.of(random.nextDouble()));
  }

  @Override // from InverseCDF
  public Scalar quantile(Scalar p) {
    Scalar yB = p_lessThan(b);
    Scalar yC = p_lessThan(c);
    if (Scalars.lessEquals(p, yB)) { // y <= yB
      Scalar term1 = Sqrt.FUNCTION.apply(alpha_inv.multiply(b.subtract(a)).multiply(p));
      return term1.add(a);
    }
    // yB < y <= yC
    if (Scalars.lessEquals(p, yC)) {
      Scalar term1 = p.multiply(alpha_inv).add(a).add(b);
      return term1.multiply(RationalScalar.HALF);
    }
    // yC < y
    Scalar term1 = RealScalar.ONE.subtract(p).multiply(alpha_inv).multiply(d.subtract(c));
    Scalar term2 = Sqrt.FUNCTION.apply(term1);
    return d.subtract(term2);
  }

  @Override // from MeanInterface
  public Scalar mean() {
    Scalar cd = c.multiply(c).add(c.multiply(d)).add(d.multiply(d));
    Scalar ab = a.multiply(a).add(a.multiply(b)).add(b.multiply(b));
    return alpha.multiply(cd.subtract(ab)).multiply(_1_3);
  }
}
