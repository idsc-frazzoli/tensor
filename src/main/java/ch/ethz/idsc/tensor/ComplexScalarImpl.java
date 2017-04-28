// code by jph
package ch.ethz.idsc.tensor;

import java.util.Objects;

import ch.ethz.idsc.tensor.red.Hypot;
import ch.ethz.idsc.tensor.sca.ArcTan;
import ch.ethz.idsc.tensor.sca.Chop;
import ch.ethz.idsc.tensor.sca.ChopInterface;
import ch.ethz.idsc.tensor.sca.Exp;
import ch.ethz.idsc.tensor.sca.Log;
import ch.ethz.idsc.tensor.sca.N;
import ch.ethz.idsc.tensor.sca.NInterface;
import ch.ethz.idsc.tensor.sca.PowerInterface;
import ch.ethz.idsc.tensor.sca.Sqrt;

/* package */ class ComplexScalarImpl extends AbstractScalar implements ComplexScalar, //
    ChopInterface, NInterface, PowerInterface {
  private final Scalar re;
  private final Scalar im;

  /* package */ ComplexScalarImpl(Scalar re, Scalar im) {
    if (re instanceof ComplexScalar || im instanceof ComplexScalar)
      throw TensorRuntimeException.of(re);
    this.re = re;
    this.im = im;
  }

  @Override // from RealInterface
  public Scalar real() {
    return re;
  }

  @Override // from ImagInterface
  public Scalar imag() {
    return im;
  }

  @Override // from Scalar
  public Scalar invert() {
    Scalar mag = re.multiply(re).add(im.multiply(im)).invert();
    return ComplexScalar.of(re.multiply(mag), im.negate().multiply(mag));
  }

  @Override // from Scalar
  public Scalar negate() {
    return ComplexScalar.of(re.negate(), im.negate());
  }

  @Override // from ConjugateInterface
  public Scalar conjugate() {
    return ComplexScalar.of(re, im.negate());
  }

  @Override // from Scalar
  public Scalar abs() {
    return Hypot.bifunction.apply(re, im);
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

  @Override // from AbstractScalar
  protected Scalar plus(Scalar scalar) {
    if (scalar instanceof ComplexScalarImpl) {
      ComplexScalarImpl z = (ComplexScalarImpl) scalar;
      return ComplexScalar.of(re.add(z.real()), im.add(z.imag()));
    }
    if (scalar instanceof RealScalar)
      return ComplexScalar.of(re.add(scalar), im);
    throw TensorRuntimeException.of(scalar);
  }

  @Override // from SqrtInterface
  public Scalar sqrt() {
    return ComplexScalar.fromPolar( //
        Sqrt.function.apply(abs()), //
        arg().divide(RealScalar.of(2)));
  }

  @Override // from ArgInterface
  public Scalar arg() {
    return ArcTan.of(re, im); // Mathematica::ArcTan[x, y]
  }

  @Override // from ChopInterface
  public Scalar chop(double threshold) {
    return ComplexScalar.of((Scalar) Chop.of(re, threshold), (Scalar) Chop.of(im, threshold));
  }

  @Override // from PowerInterface
  public Scalar power(Scalar exponent) {
    if (exponent instanceof RationalScalar) {
      RationalScalar exp = (RationalScalar) exponent;
      if (exp.isInteger())
        return Scalars.binaryPower(RealScalar.ONE).apply(this, exp.numerator());
    }
    return Exp.function.apply(exponent.multiply(Log.function.apply(this)));
  }

  @Override // from NInterface
  public Scalar n() {
    return ComplexScalar.of(N.function.apply(re), N.function.apply(im));
  }

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
    return re.equals(object) && im.equals(ZeroScalar.get());
  }

  // helper function that formats imaginary part to a String
  private String _imagToString() {
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
    if (!re.equals(ZeroScalar.get())) {
      stringBuilder.append(re);
      if (!imag.startsWith("-"))
        stringBuilder.append('+');
    }
    stringBuilder.append(imag);
    return stringBuilder.toString();
  }
}
