// code by jph
package ch.ethz.idsc.tensor.alg;

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
import ch.ethz.idsc.tensor.sca.Sign;
import ch.ethz.idsc.tensor.sca.Sqrt;

/** class exists only for testing */
/* package */ enum RootsDegree3Full {
  ;
  private static final Scalar _3 = RealScalar.of(3);
  private static final Scalar _1_3 = RationalScalar.of(1, 3);
  private static final Scalar _2_3 = RationalScalar.of(2, 3);
  private static final Scalar _4 = RealScalar.of(4);
  private static final Scalar _6 = RealScalar.of(6);
  private static final Scalar _9 = RealScalar.of(9);
  private static final Scalar _18 = RealScalar.of(18);
  private static final Scalar _27 = RealScalar.of(27);
  private static final Scalar P1_3 = Power.of(2, _1_3);
  private static final Scalar R1_2 = P1_3.negate();
  private static final Scalar R2_2 = ComplexScalar.of(RealScalar.ONE, Sqrt.of(_3)).divide(Power.of(2, _2_3));
  private static final Scalar R2_3 = ComplexScalar.of(RealScalar.ONE, Sqrt.of(_3).negate()).divide(_6).negate();
  private static final Scalar R3_2 = ComplexScalar.of(RealScalar.ONE, Sqrt.of(_3).negate()).divide(Power.of(2, _2_3));
  private static final Scalar R3_3 = ComplexScalar.of(RealScalar.ONE, Sqrt.of(_3)).divide(_6).negate();

  /** @param coeffs vector of length 4
   * @return vector of length 3 */
  static Tensor of(Tensor coeffs) {
    // naming convention of wikipedia
    Scalar d = coeffs.Get(0);
    Scalar c = coeffs.Get(1);
    Scalar b = coeffs.Get(2);
    Scalar a = coeffs.Get(3);
    Scalar D = discriminant(d, c, b, a);
    //
    Scalar _3a = a.multiply(_3);
    Scalar b_3a = b.divide(_3a).negate();
    //
    Scalar b2 = b.multiply(b);
    Scalar D0 = Times.of(_3a, c).subtract(b2); // wikipedia up to sign
    //
    Scalar b3 = b2.multiply(b);
    if (Chop._11.allZero(D)) {
      if (Chop._11.allZero(D0))
        return Tensors.of(b_3a, b_3a, b_3a);
      Scalar dr = Times.of(_9, a, d).subtract(b.multiply(c)).divide(D0.add(D0)).negate();
      Scalar srn = Times.of(_4, a, b, c).subtract(Times.of(_3a, _3a, d)).subtract(b3);
      Scalar srd = Times.of(a, D0.negate());
      return Tensors.of(dr, dr, srn.divide(srd));
    }
    //
    Scalar D1 = Times.of(_9, a, b, c).subtract(b3.add(b3)).subtract(Times.of(_27, a, a, d)); // wikipedia up to sign
    Scalar res = Sqrt.FUNCTION.apply(Times.of(_27, a, a, D).negate());
    // either sign in front of the square root may be chosen unless D0 = 0 in which case
    // the sign must be chosen so that the two terms inside the cube root do not cancel.
    Scalar cp = D1.add(res);
    Scalar cn = D1.subtract(res);
    Scalar C = Power.of(Scalars.lessThan(cn.abs(), cp.abs()) ? cp : cn, _1_3);
    //
    Scalar s2 = D0.divide(_3a).divide(C);
    Scalar s3 = C.divide(Times.of(P1_3, a));
    Tensor roots = Tensors.of( //
        b_3a.add(R1_2.multiply(s2)).add(_1_3.multiply(s3)), //
        b_3a.add(R2_2.multiply(s2)).add(R2_3.multiply(s3)), //
        b_3a.add(R3_2.multiply(s2)).add(R3_3.multiply(s3)));
    boolean isReal = Stream.of(d, c, b, a).map(Imag.FUNCTION).allMatch(Scalars::isZero);
    if (isReal) {
      if (Sign.isPositiveOrZero(D)) {
        // positive: the equation has three distinct real roots
        // zero: the equation has a multiple root and all of its roots are real
        return Real.of(roots);
      }
      // the equation has one real root and two non-real complex conjugate roots
    }
    return roots;
  }

  private static Scalar discriminant(Scalar d, Scalar c, Scalar b, Scalar a) {
    Scalar c1 = Times.of(_18, a, b, c, d);
    Scalar c2 = Times.of(_4, b, b, b, d);
    Scalar c3 = Times.of(b, b, c, c);
    Scalar c4 = Times.of(_4, a, c, c, c);
    Scalar c5 = Times.of(_27, a, a, d, d);
    return c1.subtract(c2).add(c3).subtract(c4).subtract(c5);
  }
}
