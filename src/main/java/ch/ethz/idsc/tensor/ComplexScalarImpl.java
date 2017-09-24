// code by jph
package ch.ethz.idsc.tensor;

import java.math.BigInteger;
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
import ch.ethz.idsc.tensor.sca.ExactNumberQInterface;
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
    ChopInterface, ExactNumberQInterface, MachineNumberQInterface, NInterface {
  private static final Scalar HALF = RationalScalar.of(1, 2);

  /** creator with package visibility
   * 
   * @param re neither a {@link ComplexScalar}, or {@link Quantity}
   * @param im neither a {@link ComplexScalar}, or {@link Quantity}
   * @return */
  /* package */ static Scalar of(Scalar re, Scalar im) {
    return Scalars.isZero(im) ? re : new ComplexScalarImpl(re, im);
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

  /** function is motivated by the expression c / (c^2 + d^2)
   * for c != 0 the term simplifies to 1 / (c + d^2 / c)
   * the function computes the denominator c + d^2 / c == c + d / c * d
   * 
   * @param c non-zero
   * @param d
   * @return c + d / c * d */
  private static Scalar c_dcd(Scalar c, Scalar d) {
    // if (Scalars.isZero(c)) // <- consistency check during development
    // throw TensorRuntimeException.of(c);
    return c.add(d.divide(c).multiply(d));
  }

  @Override // from Scalar
  public Scalar negate() {
    return of(re.negate(), im.negate());
  }

  private static boolean _isLocal(Scalar scalar) {
    return scalar instanceof ComplexEmbedding && !(scalar instanceof Quantity);
  }

  @Override // from Scalar
  public Scalar multiply(Scalar scalar) {
    if (_isLocal(scalar)) {
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
    if (_isLocal(scalar)) {
      ComplexEmbedding z = (ComplexEmbedding) scalar;
      return _division(re, im, z.real(), z.imag());
    }
    return scalar.under(this);
  }

  @Override // from AbstractScalar
  public Scalar under(Scalar scalar) {
    if (_isLocal(scalar)) {
      ComplexEmbedding z = (ComplexEmbedding) scalar;
      return _division(z.real(), z.imag(), re, im);
    }
    return scalar.divide(this);
  }

  // helper function
  private static Scalar _division(Scalar n_re, Scalar n_im, Scalar d_re, Scalar d_im) {
    if (Scalars.isZero(d_im))
      return of(n_re.divide(d_re), n_im.divide(d_re));
    boolean czero = Scalars.isZero(d_re);
    Scalar r1 = czero ? d_re : c_dcd(d_re, d_im);
    Scalar r2 = c_dcd(d_im, d_re);
    Scalar res_re1 = czero ? n_re.multiply(d_re) : n_re.divide(r1);
    Scalar res_re2 = n_im.divide(r2);
    Scalar res_im1 = czero ? n_im.multiply(d_re) : n_im.divide(r1);
    Scalar res_im2 = n_re.divide(r2).negate();
    return of(res_re1.add(res_re2), res_im1.add(res_im2));
  }

  @Override // from Scalar
  public Scalar reciprocal() {
    return of( //
        Scalars.isZero(re) ? re : c_dcd(re, im).reciprocal(), //
        c_dcd(im, re).reciprocal().negate()); // using the assumption that im is never zero
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
    if (_isLocal(scalar)) {
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

  @Override
  public Scalar log() {
    return of(Log.FUNCTION.apply(abs()), arg());
  }

  @Override // from ComplexEmbedding
  public Scalar imag() {
    return im;
  }

  @Override // from ExactNumberInterface
  public boolean isExactNumber() {
    return ExactNumberQ.of(re) && ExactNumberQ.of(im);
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
    N n = N.in(mathContext);
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
    return ComplexScalar.fromPolar(Sqrt.FUNCTION.apply(abs()), arg().multiply(HALF));
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

  // helper function that formats imaginary part to a String
  private String _imagToString() {
    if (im instanceof RationalScalar) {
      RationalScalar rationalScalar = (RationalScalar) im;
      BigInteger num = rationalScalar.numerator();
      BigInteger den = rationalScalar.denominator();
      if (num.equals(BigInteger.ONE))
        return I_SYMBOL + (den.equals(BigInteger.ONE) ? "" : "/" + den);
      if (num.equals(BigInteger.ONE.negate()))
        return "-" + I_SYMBOL + (den.equals(BigInteger.ONE) ? "" : "/" + den);
    }
    String imag = im.toString();
    if (imag.equals("1"))
      return I_SYMBOL;
    if (imag.equals("-1"))
      return '-' + I_SYMBOL;
    return imag + '*' + I_SYMBOL;
  }

  @Override // from AbstractScalar
  public String toString() {
    StringBuilder stringBuilder = new StringBuilder(48); // initial capacity
    String imag = _imagToString();
    if (Scalars.nonZero(re)) {
      stringBuilder.append(re);
      if (!imag.startsWith("-"))
        stringBuilder.append('+');
    }
    stringBuilder.append(imag);
    return stringBuilder.toString();
  }
}
