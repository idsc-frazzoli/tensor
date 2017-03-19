// code by jph
package ch.ethz.idsc.tensor;

import java.util.Objects;

/** complex numbers
 * <br/>
 * number() or Comparable interface is not supported */
public class ComplexScalar extends Scalar {
  static final String IMAGINARY = "*I";

  /** @param re
   * @param im
   * @return scalar with re as real part and im as imaginary part */
  public static Scalar of(Scalar re, Scalar im) {
    return im.equals(ZeroScalar.get()) ? re : new ComplexScalar(re, im);
  }

  /** @param re
   * @param im
   * @return scalar with re as real part and im as imaginary part */
  public static Scalar of(Number re, Number im) {
    return of(RealScalar.of(re), RealScalar.of(im));
  }

  public static Scalar fromPolar(Scalar abs, Scalar arg) {
    RealScalar radius = (RealScalar) abs;
    double alpha = arg.number().doubleValue();
    return radius.multiply(of( //
        DoubleScalar.of(Math.cos(alpha)), //
        DoubleScalar.of(Math.sin(alpha)) //
    ));
  }

  private final Scalar re;
  private final Scalar im;

  private ComplexScalar(Scalar re, Scalar im) {
    this.re = re;
    this.im = im;
  }

  /** @return real part */
  public Scalar real() {
    return re;
  }

  /** @return imaginary part */
  public Scalar imag() {
    return im;
  }

  @Override // from Scalar
  public Scalar invert() {
    Scalar mag = re.multiply(re).plus(im.multiply(im)).invert();
    return of(re.multiply(mag), im.negate().multiply(mag));
  }

  @Override // from Tensor
  public Scalar negate() {
    return of(re.negate(), im.negate());
  }

  @Override // from Scalar
  public Scalar conjugate() {
    return of(re, im.negate());
  }

  @Override // from Scalar
  public RealScalar abs() {
    return DoubleScalar.of(Math.hypot( //
        re.abs().number().doubleValue(), //
        im.abs().number().doubleValue()));
  }

  @Override // from Scalar
  public Scalar absSquared() {
    return multiply(conjugate()); // preserves precision
  }

  @Override // from Scalar
  protected Scalar plus(Scalar scalar) {
    if (scalar instanceof ComplexScalar) {
      ComplexScalar complexScalar = (ComplexScalar) scalar;
      return of(re.plus(complexScalar.real()), im.plus(complexScalar.imag()));
    }
    return of(re.plus(scalar), im);
  }

  @Override // from Scalar
  public Scalar multiply(Scalar scalar) {
    if (scalar instanceof ComplexScalar) {
      ComplexScalar cmp = (ComplexScalar) scalar;
      return of( //
          re.multiply(cmp.real()).subtract(im.multiply(cmp.imag())), //
          re.multiply(cmp.imag()).plus(im.multiply(cmp.real())));
    }
    return of(re.multiply(scalar), im.multiply(scalar));
  }

  @Override
  public int hashCode() {
    return Objects.hash(re, im);
  }

  @Override
  public boolean equals(Object object) {
    // null check not required
    if (object instanceof ComplexScalar) {
      ComplexScalar complexScalar = (ComplexScalar) object;
      return re.equals(complexScalar.real()) && im.equals(complexScalar.imag());
    }
    return re.equals(object) && im.equals(ZeroScalar.get());
  }

  @Override
  public String toString() {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(re);
    String imag = im.toString();
    if (!imag.startsWith("-"))
      stringBuilder.append('+');
    stringBuilder.append(im);
    stringBuilder.append(IMAGINARY);
    return stringBuilder.toString();
  }
}
