// code by jph
package ch.ethz.idsc.tensor;

import java.io.Serializable;
import java.math.MathContext;
import java.util.Objects;

import ch.ethz.idsc.tensor.qty.Quantity;
import ch.ethz.idsc.tensor.red.Hypot;
import ch.ethz.idsc.tensor.sca.ArcTan;
import ch.ethz.idsc.tensor.sca.Ceiling;
import ch.ethz.idsc.tensor.sca.Chop;
import ch.ethz.idsc.tensor.sca.ChopInterface;
import ch.ethz.idsc.tensor.sca.ComplexEmbedding;
import ch.ethz.idsc.tensor.sca.Cos;
import ch.ethz.idsc.tensor.sca.Cosh;
import ch.ethz.idsc.tensor.sca.ExactScalarQInterface;
import ch.ethz.idsc.tensor.sca.Exp;
import ch.ethz.idsc.tensor.sca.Floor;
import ch.ethz.idsc.tensor.sca.Log;
import ch.ethz.idsc.tensor.sca.MachineNumberQInterface;
import ch.ethz.idsc.tensor.sca.N;
import ch.ethz.idsc.tensor.sca.NInterface;
import ch.ethz.idsc.tensor.sca.Round;
import ch.ethz.idsc.tensor.sca.Sin;
import ch.ethz.idsc.tensor.sca.Sinh;
import ch.ethz.idsc.tensor.sca.Sqrt;

/* package */ final class ComplexScalarImpl extends AbstractScalar implements ComplexScalar, //
    ChopInterface, ExactScalarQInterface, MachineNumberQInterface, NInterface, Serializable {
  /** creator with package visibility
   * 
   * @param re neither a {@link ComplexScalar}, or {@link Quantity}
   * @param im neither a {@link ComplexScalar}, or {@link Quantity}
   * @return */
  /* package */ static Scalar of(Scalar re, Scalar im) {
    return Scalars.isZero(im) ? re : new ComplexScalarImpl(re, im);
  }

  /** @param scalar
   * @return true if operation is carried out in {@link ComplexScalarImpl} */
  private static boolean isLocal(Scalar scalar) {
    return scalar instanceof ComplexEmbedding && !(scalar instanceof Quantity);
  }

  // ---
  private final Scalar re;
  private final Scalar im;

  /* package */ ComplexScalarImpl(Scalar re, Scalar im) {
    this.re = re;
    this.im = im;
  }

  /***************************************************/
  @Override // from Scalar
  public Scalar abs() { // "complex modulus"
    return Hypot.BIFUNCTION.apply(re, im);
  }

  @Override // from Scalar
  public Scalar negate() {
    return of(re.negate(), im.negate());
  }

  @Override // from Scalar
  public Scalar multiply(Scalar scalar) {
    if (isLocal(scalar)) {
      ComplexEmbedding z = (ComplexEmbedding) scalar;
      Scalar z_re = z.real();
      Scalar z_im = z.imag();
      return of( //
          re.multiply(z_re).subtract(im.multiply(z_im)), //
          re.multiply(z_im).add(im.multiply(z_re)));
    }
    return scalar.multiply(this);
  }

  @Override // from AbstractScalar
  public Scalar divide(Scalar scalar) {
    if (isLocal(scalar)) {
      ComplexEmbedding z = (ComplexEmbedding) scalar;
      return ComplexHelper.division(re, im, z.real(), z.imag());
    }
    return scalar.under(this);
  }

  @Override // from AbstractScalar
  public Scalar under(Scalar scalar) {
    if (isLocal(scalar)) {
      ComplexEmbedding z = (ComplexEmbedding) scalar;
      return ComplexHelper.division(z.real(), z.imag(), re, im);
    }
    return scalar.divide(this);
  }

  @Override // from Scalar
  public Scalar reciprocal() {
    return of( //
        Scalars.isZero(re) ? re : ComplexHelper.c_dcd(re, im).reciprocal(), //
        ComplexHelper.c_dcd(im, re).reciprocal().negate()); // using the assumption that im is never zero
  }

  @Override // from Scalar
  public Number number() {
    throw TensorRuntimeException.of(this);
  }

  @Override // from Scalar
  public Scalar zero() {
    return re.zero().add(im.zero());
  }

  /***************************************************/
  @Override // from AbstractScalar
  protected Scalar plus(Scalar scalar) {
    if (isLocal(scalar)) {
      ComplexEmbedding z = (ComplexEmbedding) scalar;
      return of(re.add(z.real()), im.add(z.imag()));
    }
    return scalar.add(this);
  }

  /***************************************************/
  @Override // from ArcTanInterface
  public Scalar arcTan(Scalar x) {
    return StaticHelper.arcTan(x, this);
  }

  @Override // from ArgInterface
  public Scalar arg() {
    return ArcTan.of(re, im); // Mathematica::ArcTan[x, y]
  }

  @Override // from RoundingInterface
  public Scalar ceiling() {
    return of(Ceiling.FUNCTION.apply(re), Ceiling.FUNCTION.apply(im));
  }

  @Override // from ChopInterface
  public Scalar chop(Chop chop) {
    return of(chop.apply(re), chop.apply(im));
  }

  @Override // from ComplexEmbedding
  public Scalar conjugate() {
    return of(re, im.negate());
  }

  @Override // from ExpInterface
  public Scalar exp() {
    // construct in polar coordinates
    return ComplexScalar.fromPolar(Exp.FUNCTION.apply(real()), imag());
  }

  @Override // from RoundingInterface
  public Scalar floor() {
    return of(Floor.FUNCTION.apply(re), Floor.FUNCTION.apply(im));
  }

  @Override // from LogInterface
  public Scalar log() {
    return of(Log.FUNCTION.apply(abs()), arg());
  }

  @Override // from ComplexEmbedding
  public Scalar imag() {
    return im;
  }

  @Override // from ExactNumberInterface
  public boolean isExactScalar() {
    return ExactScalarQ.of(re) && ExactScalarQ.of(im);
  }

  @Override // MachineNumberQInterface
  public boolean isMachineNumber() {
    return MachineNumberQ.of(re) && MachineNumberQ.of(im);
  }

  @Override // from NInterface
  public Scalar n() {
    return of(N.DOUBLE.apply(re), N.DOUBLE.apply(im));
  }

  @Override // from NInterface
  public Scalar n(MathContext mathContext) {
    N n = N.in(mathContext.getPrecision());
    return of(n.apply(re), n.apply(im));
  }

  @Override // from PowerInterface
  public Scalar power(Scalar exponent) {
    if (IntegerQ.of(exponent)) {
      RationalScalar rationalScalar = (RationalScalar) exponent;
      return Scalars.binaryPower(RealScalar.ONE).apply(this, rationalScalar.numerator());
    }
    return Exp.FUNCTION.apply(exponent.multiply(Log.FUNCTION.apply(this)));
  }

  @Override // from ComplexEmbedding
  public Scalar real() {
    return re;
  }

  @Override // from RoundingInterface
  public Scalar round() {
    return of(Round.FUNCTION.apply(re), Round.FUNCTION.apply(im));
  }

  @Override // from SqrtInterface
  public Scalar sqrt() {
    return ComplexScalar.fromPolar(Sqrt.FUNCTION.apply(abs()), arg().multiply(RationalScalar.HALF));
  }

  @Override // from TrigonometryInterface
  public Scalar cos() {
    return of( //
        Cos.of(re).multiply(Cosh.of(im)), //
        Sin.of(re).multiply(Sinh.of(im)).negate());
  }

  @Override // from TrigonometryInterface
  public Scalar cosh() {
    return of( //
        Cosh.of(re).multiply(Cos.of(im)), //
        Sinh.of(re).multiply(Sin.of(im)));
  }

  @Override // from TrigonometryInterface
  public Scalar sin() {
    return of( //
        Sin.of(re).multiply(Cosh.of(im)), //
        Cos.of(re).multiply(Sinh.of(im)));
  }

  @Override // from TrigonometryInterface
  public Scalar sinh() {
    return of( //
        Sinh.of(re).multiply(Cos.of(im)), //
        Cosh.of(re).multiply(Sin.of(im)));
  }

  /***************************************************/
  @Override // from AbstractScalar
  public int hashCode() {
    return Objects.hash(re, im);
  }

  @Override // from AbstractScalar
  public boolean equals(Object object) {
    if (object instanceof ComplexEmbedding) {
      ComplexEmbedding z = (ComplexEmbedding) object;
      return re.equals(z.real()) && im.equals(z.imag());
    }
    return false;
  }

  @Override // from AbstractScalar
  public String toString() {
    StringBuilder stringBuilder = new StringBuilder(48); // initial capacity
    String imag = ScalarParser.imagToString(im);
    if (Scalars.nonZero(re)) {
      stringBuilder.append(re);
      if (!imag.startsWith("-"))
        stringBuilder.append('+');
    }
    stringBuilder.append(imag);
    return stringBuilder.toString();
  }
}
