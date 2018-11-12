// code by clruch
package ch.ethz.idsc.tensor.pdf;

import java.util.Random;

import ch.ethz.idsc.tensor.RationalScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Scalars;
import ch.ethz.idsc.tensor.sca.Power;

/**
 * Characteristics of a trapezoidal distribution: the graph of the PDF resembles
 * a trapezoid which beginns rising at a until b, has a plateau from b to c, and
 * then falls after c to point d.
 * 
 * Inspired by: https://en.wikipedia.org/wiki/Trapezoidal_distribution
 */
public class TrapezoidalDistribution extends AbstractContinuousDistribution implements InverseCDF, MeanInterface {

	public static Distribution of(Scalar a, Scalar b, Scalar c, Scalar d) {
		return new TrapezoidalDistribution(a, b, c, d);
	}

	// --
	private final Scalar a;
	private final Scalar b;
	private final Scalar c;
	private final Scalar d;
	private final Scalar alpha;

	private TrapezoidalDistribution(Scalar a, Scalar b, Scalar c, Scalar d) {
		if (!Scalars.lessThan(a, d) || !Scalars.lessEquals(a, b) || //
				!Scalars.lessEquals(b, c) || !Scalars.lessThan(c, d))// )
			throw new IllegalArgumentException();
		this.a = a;
		this.b = b;
		this.c = c;
		this.d = d;
		this.alpha = RealScalar.ONE.divide(d.add(c).subtract(a).subtract(b));
	}

	@Override // from CDF
	public Scalar p_lessThan(Scalar x) {
		if (Scalars.lessThan(x, a))
			return RealScalar.ZERO;
		else {
			if (Scalars.lessEquals(a, x) && Scalars.lessThan(x, b)) {
				Scalar term1 = RealScalar.ONE.divide(b.subtract(a));
				Scalar term2 = x.subtract(a).multiply(x.subtract(a));
				return alpha.multiply(term1).multiply(term2);
			}
			if (Scalars.lessEquals(b, x) && Scalars.lessThan(x, c)) {
				Scalar term2 = RationalScalar.of(2, 1).multiply(x).subtract(a).subtract(b);
				return alpha.multiply(term2);
			}
			if (Scalars.lessEquals(c, x) && Scalars.lessThan(x, d)) {
				Scalar term1 = RealScalar.ONE.divide(d.subtract(c));
				Scalar term2 = d.subtract(x).multiply(d.subtract(x));
				return RealScalar.ONE.subtract(alpha.multiply(term1).multiply(term2));
			}
			return RealScalar.ONE;
		}
	}

	@Override // from CDF
	public Scalar p_lessEquals(Scalar x) {
		return p_lessThan(x);
	}

	@Override
	public Scalar at(Scalar x) {
		// support is [a,d]
		if (Scalars.lessEquals(a, x) && Scalars.lessEquals(x, d)) {
			if (Scalars.lessEquals(a, x) && Scalars.lessThan(x, b)) {
				Scalar term1 = RationalScalar.of(2, 1).multiply(alpha);
				Scalar term2 = (x.subtract(a)).divide(b.subtract(a));
				return term1.multiply(term2);
			}
			if (Scalars.lessEquals(b, x) && Scalars.lessThan(x, c)) {
				return RationalScalar.of(2, 1).divide(d.add(c).subtract(a).subtract(b));
			}
			if (Scalars.lessEquals(c, x) && Scalars.lessThan(x, d)) {
				Scalar term1 = RationalScalar.of(2, 1).multiply(alpha);
				Scalar term2 = (d.subtract(x)).divide(d.subtract(c));
				return term1.multiply(term2);
			}
		}
		return RealScalar.ZERO;
	}

	@Override
	public Scalar randomVariate(Random random) {
		return quantile(RealScalar.of(random.nextDouble()));
	}

	@Override
	public Scalar quantile(Scalar p) {
		Scalar yB = p_lessThan(b);
		Scalar yC = p_lessThan(c);

		if (Scalars.lessEquals(p, yB)) { // y<=yB
			Scalar term1 = Power.of(RealScalar.ONE.divide(alpha).multiply(b.subtract(a)).multiply(p),
					RationalScalar.of(1, 2));
			Scalar result = term1.add(a);
			return result;
		}
		// yB < y <= yC
		if (Scalars.lessThan(yB, p) && Scalars.lessEquals(p, yC)) {
			Scalar term1 = (p.multiply(RealScalar.ONE.divide(alpha))).add(a).add(b);
			Scalar result = term1.multiply(RationalScalar.of(1, 2));
			return result;
		}

		// y>yC
		Scalar term1 = (RealScalar.ONE).subtract(p).multiply(RealScalar.ONE.divide(alpha)).multiply(d.subtract(c));
		Scalar term2 = Power.of(term1, RationalScalar.of(1, 2));
		Scalar result = d.subtract(term2);
		return result;
	}

	@Override
	public Scalar mean() {
		Scalar term1 = alpha.multiply(RationalScalar.of(1, 3));
		Scalar term2 = (Power.of(d, 3).subtract(Power.of(c, 3))).divide(d.subtract(c));
		Scalar term3 = (Power.of(b, 3).subtract(Power.of(a, 3))).divide(b.subtract(a));
		return term1.multiply(term2.subtract(term3));
	}
}
