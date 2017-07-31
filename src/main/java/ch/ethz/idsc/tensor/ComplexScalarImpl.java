// code by jph
package ch.ethz.idsc.tensor;

import java.math.BigInteger;
import java.util.Objects;

import ch.ethz.idsc.tensor.red.Hypot;
import ch.ethz.idsc.tensor.sca.ArcTan;
import ch.ethz.idsc.tensor.sca.Ceiling;
import ch.ethz.idsc.tensor.sca.Chop;
import ch.ethz.idsc.tensor.sca.ChopInterface;
import ch.ethz.idsc.tensor.sca.ExactNumberQInterface;
import ch.ethz.idsc.tensor.sca.Exp;
import ch.ethz.idsc.tensor.sca.Floor;
import ch.ethz.idsc.tensor.sca.Log;
import ch.ethz.idsc.tensor.sca.MachineNumberQInterface;
import ch.ethz.idsc.tensor.sca.N;
import ch.ethz.idsc.tensor.sca.NInterface;
import ch.ethz.idsc.tensor.sca.Round;
import ch.ethz.idsc.tensor.sca.Sqrt;

/* package */ final class ComplexScalarImpl extends AbstractScalar implements ComplexScalar, //
    ChopInterface, ExactNumberQInterface, MachineNumberQInterface, NInterface {
  private static final Scalar HALF = RationalScalar.of(1, 2);
  // ---
  private final Scalar re;
  private final Scalar im;

  /* package */ ComplexScalarImpl(Scalar re, Scalar im) {
    this.re = re;
    this.im = im;
  }

  /***************************************************/
  @Override // from Scalar
  public Scalar abs() {
    return Hypot.BIFUNCTION.apply(re, im); // complex modulus
  }

  @Override // from Scalar
  public Scalar invert() {
    // the textbook solution results is not numerically stable:
    // Scalar mag = re.multiply(re).add(im.multiply(im));
    // return ComplexScalar.of(re.divide(mag), im.negate().divide(mag));
    return ComplexScalar.of( //
        Scalars.isZero(re) ? re : re.add(im.divide(re).multiply(im)).invert(), //
        Scalars.isZero(im) ? im : im.add(re.divide(im).multiply(re)).invert().negate());
  }

  @Override // from Scalar
  public Scalar negate() {
    return ComplexScalar.of(re.negate(), im.negate());
  }

  @Override // from Scalar
  public Scalar multiply(Scalar scalar) {
    if (scalar instanceof ComplexScalarImpl) {
      ComplexScalarImpl z = (ComplexScalarImpl) scalar;
      return ComplexScalar.of( //
          re.multiply(z.real()).subtract(im.multiply(z.imag())), //
          re.multiply(z.imag()).add(im.multiply(z.real())));
    }
    return ComplexScalar.of(re.multiply(scalar), im.multiply(scalar));
  }

  @Override // from Scalar
  public Number number() {
    throw TensorRuntimeException.of(this);
  }

  @Override // from Scalar
  public Scalar zero() {
    return re.zero(); // not symmetric, since re is chosen over im...
  }

  /***************************************************/
  @Override // from AbstractScalar
  protected Scalar plus(Scalar scalar) {
    if (scalar instanceof ComplexScalarImpl) {
      ComplexScalarImpl z = (ComplexScalarImpl) scalar;
      return ComplexScalar.of(re.add(z.real()), im.add(z.imag()));
    }
    if (scalar instanceof RealScalar)
      return ComplexScalar.of(re.add(scalar), im);
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
    return ComplexScalar.of(Ceiling.FUNCTION.apply(re), Ceiling.FUNCTION.apply(im));
  }

  @Override // from ChopInterface
  public Scalar chop(Chop chop) {
    return ComplexScalar.of(chop.apply(re), chop.apply(im));
  }

  @Override // from ComplexEmbedding
  public Scalar conjugate() {
    return ComplexScalar.of(re, im.negate());
  }

  @Override // from RoundingInterface
  public Scalar floor() {
    return ComplexScalar.of(Floor.FUNCTION.apply(re), Floor.FUNCTION.apply(im));
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
    return ComplexScalar.of(N.FUNCTION.apply(re), N.FUNCTION.apply(im));
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
    return ComplexScalar.of(Round.FUNCTION.apply(re), Round.FUNCTION.apply(im));
  }

  @Override // from SqrtInterface
  public Scalar sqrt() {
    return ComplexScalar.fromPolar(Sqrt.FUNCTION.apply(abs()), arg().multiply(HALF));
  }

  /***************************************************/
  @Override // from AbstractScalar
  public int hashCode() {
    return Objects.hash(re, im);
  }

  @Override // from AbstractScalar
  public boolean equals(Object object) {
    // null check not required
    if (object instanceof ComplexScalarImpl) {
      ComplexScalarImpl complexScalar = (ComplexScalarImpl) object;
      return re.equals(complexScalar.real()) && im.equals(complexScalar.imag());
    }
    return re.equals(object) && Scalars.isZero(im);
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
