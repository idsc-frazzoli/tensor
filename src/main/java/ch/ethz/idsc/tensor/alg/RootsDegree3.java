// code by jph
package ch.ethz.idsc.tensor.alg;

import java.io.Serializable;
import java.util.stream.Stream;

import ch.ethz.idsc.tensor.ComplexScalar;
import ch.ethz.idsc.tensor.RationalScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Scalars;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.red.Times;
import ch.ethz.idsc.tensor.sca.Chop;
import ch.ethz.idsc.tensor.sca.Imag;
import ch.ethz.idsc.tensor.sca.Power;
import ch.ethz.idsc.tensor.sca.Real;
import ch.ethz.idsc.tensor.sca.ScalarUnaryOperator;
import ch.ethz.idsc.tensor.sca.Sign;
import ch.ethz.idsc.tensor.sca.Sqrt;

/** converts general cubic polynomial to depressed form, i.e.
 * the quadratic term vanishes.
 * 
 * numerical tests suggest that the zero check tolerance is
 * lower when using the depressed form.
 * 
 * therefore the implementation below is chosen as default when
 * computing roots of cubic polynomials. */
/* package */ class RootsDegree3 implements Serializable {
  private static final Scalar _3 = RealScalar.of(3);
  private static final Scalar N1_2 = RationalScalar.of(-1, 2);
  private static final Scalar _1_3 = RationalScalar.of(1, 3);
  private static final Scalar _2_3 = RationalScalar.of(2, 3);
  private static final Scalar _4 = RealScalar.of(4);
  private static final Scalar _6 = RealScalar.of(6);
  private static final Scalar _27 = RealScalar.of(27);
  private static final Scalar P1_3 = Power.of(2, _1_3);
  private static final Scalar R1_2 = P1_3.negate();
  private static final Scalar R2_2 = ComplexScalar.of(RealScalar.ONE, Sqrt.of(_3)).divide(Power.of(2, _2_3));
  private static final Scalar R2_3 = ComplexScalar.of(RealScalar.ONE, Sqrt.of(_3).negate()).divide(_6).negate();
  private static final Scalar R3_2 = ComplexScalar.of(RealScalar.ONE, Sqrt.of(_3).negate()).divide(Power.of(2, _2_3));
  private static final Scalar R3_3 = ComplexScalar.of(RealScalar.ONE, Sqrt.of(_3)).divide(_6).negate();
  private static final ScalarUnaryOperator POWER_1_3 = Power.function(_1_3);
  private static final Tensor ZEROS = Array.zeros(3);

  public static Tensor of(Tensor coeffs) {
    return new RootsDegree3(coeffs).roots();
  }

  // ---
  private final Scalar d;
  private final Scalar c;
  private final Scalar a;
  private final Scalar shift;

  public RootsDegree3(Tensor _coeffs) {
    Scalar _d = _coeffs.Get(0);
    Scalar _c = _coeffs.Get(1);
    Scalar _b = _coeffs.Get(2);
    Scalar _a = _coeffs.Get(3);
    shift = _b.divide(_a).multiply(_1_3).negate();
    Scalar bs = _b.multiply(shift);
    Scalar bs2 = bs.multiply(shift);
    d = _d.add(bs2.multiply(_2_3)).add(_c.multiply(shift));
    c = _c.add(bs);
    a = _a;
  }

  public Tensor roots() {
    return _roots().map(shift::add);
  }

  /** @param coeffs vector of length 4
   * @return vector of length 3 */
  private Tensor _roots() {
    Scalar _27aa = _27.multiply(a).multiply(a);
    Scalar D1 = _27aa.multiply(d); // wikipedia up to sign
    Scalar Dn = Times.of(_4, a, c, c, c).add(D1.multiply(d));
    //
    Scalar _3a = _3.multiply(a);
    Scalar _3ac = _3a.multiply(c); // D0 wikipedia up to sign
    //
    if (Chop._13.allZero(Dn)) {
      if (Chop._13.allZero(_3ac))
        return ZEROS;
      Scalar _3d_c = d.divide(c).multiply(_3);
      Scalar dr = _3d_c.multiply(N1_2);
      return Tensors.of(dr, dr, _3d_c);
    }
    //
    Scalar res = Sqrt.FUNCTION.apply(_27aa.multiply(Dn));
    Scalar D1n = D1.negate();
    // either sign in front of the square root may be chosen unless D0 = 0 in which case
    // the sign must be chosen so that the two terms inside the cube root do not cancel.
    Scalar cp = D1n.add(res);
    Scalar cn = D1n.subtract(res);
    Scalar C = POWER_1_3.apply(Scalars.lessThan(cn.abs(), cp.abs()) ? cp : cn);
    //
    Scalar s2 = c.divide(C);
    Scalar s3 = C.divide(a).divide(P1_3);
    Tensor roots = Tensors.of( //
        R1_2.multiply(s2).add(_1_3.multiply(s3)), //
        R2_2.multiply(s2).add(R2_3.multiply(s3)), //
        R3_2.multiply(s2).add(R3_3.multiply(s3)));
    boolean isReal = Stream.of(d, c, a).map(Imag.FUNCTION).allMatch(Scalars::isZero);
    if (isReal) {
      if (Sign.isNegativeOrZero(Dn)) { // discriminant
        // positive: the equation has three distinct real roots
        // zero: the equation has a multiple root and all of its roots are real
        return Real.of(roots);
      }
      // the equation has one real root and two non-real complex conjugate roots
    }
    return roots;
  }
}
