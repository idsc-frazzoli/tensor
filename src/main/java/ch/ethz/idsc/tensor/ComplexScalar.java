// code by jph
package ch.ethz.idsc.tensor;

import java.util.Objects;

import ch.ethz.idsc.tensor.red.Hypot;

/** complex number
 * 
 * <p>number() or Comparable interface is not supported */
public class ComplexScalar extends BasicScalar {
  static final String IMAGINARY_SUFFIX = "*I";

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
    Scalar mag = re.multiply(re).add(im.multiply(im)).invert();
    return of(re.multiply(mag), im.negate().multiply(mag));
  }

  @Override // from Scalar
  public Scalar negate() {
    return of(re.negate(), im.negate());
  }

  @Override // from Scalar
  public Scalar conjugate() {
    return of(re, im.negate());
  }

  @Override // from Scalar
  public RealScalar abs() {
    return (RealScalar) Hypot.bifunction.apply(re, im);
  }

  @Override // from Scalar
  public Scalar absSquared() {
    return multiply(conjugate()); // preserves precision
  }

  @Override // from Scalar
  public Scalar multiply(Scalar scalar) {
    if (scalar instanceof ComplexScalar) {
      ComplexScalar cmp = (ComplexScalar) scalar;
      return of( //
          re.multiply(cmp.real()).subtract(im.multiply(cmp.imag())), //
          re.multiply(cmp.imag()).add(im.multiply(cmp.real())));
    }
    return of(re.multiply(scalar), im.multiply(scalar));
  }

  @Override // from AbstractScalar
  protected Scalar plus(Scalar scalar) {
    if (scalar instanceof ComplexScalar) {
      ComplexScalar complexScalar = (ComplexScalar) scalar;
      return of(re.add(complexScalar.real()), im.add(complexScalar.imag()));
    }
    return of(re.add(scalar), im);
  }

  @Override // from Scalar
  public int hashCode() {
    return Objects.hash(re, im);
  }

  @Override // from Scalar
  public boolean equals(Object object) {
    // null check not required
    if (object instanceof ComplexScalar) {
      ComplexScalar complexScalar = (ComplexScalar) object;
      return re.equals(complexScalar.real()) && im.equals(complexScalar.imag());
    }
    return re.equals(object) && im.equals(ZeroScalar.get());
  }

  @Override // from Scalar
  public String toString() {
    StringBuilder stringBuilder = new StringBuilder(48); // initial capacity
    stringBuilder.append(re);
    String imag = im.toString();
    if (!imag.startsWith("-"))
      stringBuilder.append('+');
    stringBuilder.append(im);
    stringBuilder.append(IMAGINARY_SUFFIX);
    return stringBuilder.toString();
  }
}
