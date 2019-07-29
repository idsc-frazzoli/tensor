// code by jph
package ch.ethz.idsc.tensor.alg;

import ch.ethz.idsc.tensor.ComplexScalar;
import ch.ethz.idsc.tensor.RationalScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Scalars;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.red.Times;
import ch.ethz.idsc.tensor.sca.Power;
import ch.ethz.idsc.tensor.sca.Sqrt;

/* package */ enum RootsCubic {
  ;
  private static final Scalar _3 = RealScalar.of(3);
  private static final Scalar _1_3 = RationalScalar.of(1, 3);
  private static final Scalar _2_3 = RationalScalar.of(2, 3);
  private static final Scalar _4 = RealScalar.of(4);
  private static final Scalar _6 = RealScalar.of(6);
  private static final Scalar _9 = RealScalar.of(9);
  private static final Scalar _27 = RealScalar.of(27);
  private static final Scalar P1_3 = Power.of(2, _1_3);
  private static final Scalar R1_2 = P1_3.negate();
  private static final Scalar R1_3 = _3.reciprocal();
  private static final Scalar R2_2 = ComplexScalar.of(RealScalar.ONE, Sqrt.of(_3)).divide(Power.of(2, _2_3));
  private static final Scalar R2_3 = ComplexScalar.of(RealScalar.ONE, Sqrt.of(_3).negate()).divide(_6).negate();
  private static final Scalar R3_2 = ComplexScalar.of(RealScalar.ONE, Sqrt.of(_3).negate()).divide(Power.of(2, _2_3));
  private static final Scalar R3_3 = ComplexScalar.of(RealScalar.ONE, Sqrt.of(_3)).divide(_6).negate();

  /** @param coeffs vector of length 4
   * @return vector of length 3 */
  static Tensor of(Tensor coeffs) {
    Scalar a = coeffs.Get(0);
    Scalar b = coeffs.Get(1);
    Scalar c = coeffs.Get(2);
    Scalar d = coeffs.Get(3);
    //
    Scalar d3 = d.multiply(_3);
    Scalar s1 = c.divide(d3).negate();
    //
    Scalar c2 = c.multiply(c);
    Scalar bd3_c2 = Times.of(b, d3).subtract(c2);
    //
    Scalar c3 = c2.multiply(c);
    Scalar c3s = Times.of(b, c, d, _9).subtract(c3.add(c3)).subtract(Times.of(a, d, d, _27));
    //
    Scalar det = Power.of( //
        c3s.add(Sqrt.FUNCTION.apply(_4.multiply(Power.of(bd3_c2, _3)).add(Times.of(c3s, c3s)))), _1_3);
    //
    Scalar s2_den = d3.multiply(det);
    //
    Scalar s2 = Scalars.isZero(bd3_c2) ? bd3_c2.zero() : bd3_c2.divide(s2_den);
    Scalar s3 = det.divide(Times.of(P1_3, d));
    return Tensors.of( //
        s1.add(R1_2.multiply(s2)).add(R1_3.multiply(s3)), //
        s1.add(R2_2.multiply(s2)).add(R2_3.multiply(s3)), //
        s1.add(R3_2.multiply(s2)).add(R3_3.multiply(s3)));
  }
}
