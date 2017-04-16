// code by jph
package ch.ethz.idsc.tensor;

import java.util.Objects;

import ch.ethz.idsc.tensor.red.Hypot;
import ch.ethz.idsc.tensor.sca.ArgInterface;
import ch.ethz.idsc.tensor.sca.Chop;
import ch.ethz.idsc.tensor.sca.ChopInterface;
import ch.ethz.idsc.tensor.sca.ConjugateInterface;
import ch.ethz.idsc.tensor.sca.ImagInterface;
import ch.ethz.idsc.tensor.sca.N;
import ch.ethz.idsc.tensor.sca.NInterface;
import ch.ethz.idsc.tensor.sca.RealInterface;
import ch.ethz.idsc.tensor.sca.Sqrt;
import ch.ethz.idsc.tensor.sca.SqrtInterface;

/** complex number
 * 
 * <p>number() or Comparable interface is not supported */
public class ComplexScalar extends AbstractScalar implements //
    ArgInterface, ConjugateInterface, ChopInterface, ImagInterface, NInterface, //
    RealInterface, SqrtInterface {
  static final String IMAGINARY_SUFFIX = "*I";

  /** @param re
   * @param im
   * @return scalar with re as real part and im as imaginary part
   * @throws Exception if re or im are {@link ComplexScalar} */
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
    double alpha = arg.number().doubleValue();
    return abs.multiply(of( //
        DoubleScalar.of(Math.cos(alpha)), //
        DoubleScalar.of(Math.sin(alpha)) //
    ));
  }

  private final Scalar re;
  private final Scalar im;

  private ComplexScalar(Scalar re, Scalar im) {
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
    return of(re.multiply(mag), im.negate().multiply(mag));
  }

  @Override // from Scalar
  public Scalar negate() {
    return of(re.negate(), im.negate());
  }

  @Override // from ConjugateInterface
  public Scalar conjugate() {
    return of(re, im.negate());
  }

  @Override // from Scalar
  public Scalar abs() {
    return Hypot.bifunction.apply(re, im);
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

  @Override // from Scalar
  public Number number() {
    throw TensorRuntimeException.of(this);
  }

  @Override // from AbstractScalar
  protected Scalar plus(Scalar scalar) {
    if (scalar instanceof ComplexScalar) {
      ComplexScalar complexScalar = (ComplexScalar) scalar;
      return of(re.add(complexScalar.real()), im.add(complexScalar.imag()));
    }
    if (scalar instanceof RealScalar)
      return of(re.add(scalar), im);
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
    return DoubleScalar.of(Math.atan2( //
        imag().number().doubleValue(), //
        real().number().doubleValue() //
    ));
  }

  @Override // from ChopInterface
  public Scalar chop(double threshold) {
    return of((Scalar) Chop.of(re, threshold), (Scalar) Chop.of(im, threshold));
  }

  @Override // from NInterface
  public Scalar n() {
    return ComplexScalar.of(N.function.apply(re), N.function.apply(im));
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
