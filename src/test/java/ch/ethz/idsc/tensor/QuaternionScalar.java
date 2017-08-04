// code by jph
package ch.ethz.idsc.tensor;

import java.util.Objects;

import ch.ethz.idsc.tensor.sca.AbsSquared;
import ch.ethz.idsc.tensor.sca.ComplexEmbedding;
import ch.ethz.idsc.tensor.sca.Sqrt;

// EXPERIMENTAL
final class QuaternionScalar extends AbstractScalar implements //
    ComplexEmbedding {
  public static Scalar of(Number re, Number im, Number jm, Number km) {
    return of( //
        RealScalar.of(re), //
        RealScalar.of(im), //
        RealScalar.of(jm), //
        RealScalar.of(km));
  }

  // TODO probably better to represent quaternion as 2 complex scalars!
  public static Scalar of(Scalar re, Scalar im, Scalar jm, Scalar km) {
    if (Scalars.isZero(im) && Scalars.isZero(jm) && Scalars.isZero(km))
      return re;
    return new QuaternionScalar(re, im, jm, km);
  }

  private final Scalar re;
  private final Scalar im;
  private final Scalar jm;
  private final Scalar km;

  private QuaternionScalar(Scalar re, Scalar im, Scalar jm, Scalar km) {
    this.re = re;
    this.im = im;
    this.jm = jm;
    this.km = km;
  }

  @Override // from AbstractScalar
  protected Scalar plus(Scalar scalar) {
    if (scalar instanceof QuaternionScalar) {
      QuaternionScalar quaternionScalar = (QuaternionScalar) scalar;
      return of( //
          re.add(quaternionScalar.re), //
          im.add(quaternionScalar.im), //
          jm.add(quaternionScalar.jm), //
          km.add(quaternionScalar.km));
    }
    if (scalar instanceof ComplexScalar) {
      ComplexScalar complexScalar = (ComplexScalar) scalar;
      return of(re.add(complexScalar.real()), im.add(complexScalar.imag()), jm, km);
    }
    if (scalar instanceof RealScalar)
      return of(re.add(scalar), im, jm, km);
    return null;
  }

  @Override
  public Scalar multiply(Scalar scalar) {
    if (scalar instanceof QuaternionScalar) {
      QuaternionScalar quaternionScalar = (QuaternionScalar) scalar;
      return of( //
          re.multiply(quaternionScalar.re) //
              .subtract(im.multiply(quaternionScalar.im)) //
              .subtract(jm.multiply(quaternionScalar.jm)) //
              .subtract(km.multiply(quaternionScalar.km)) //
          , //
          re.multiply(quaternionScalar.im) //
              .add(im.multiply(quaternionScalar.re)) //
              .add(jm.multiply(quaternionScalar.km)) //
              .subtract(km.multiply(quaternionScalar.jm)) //
          , //
          re.multiply(quaternionScalar.jm) //
              .subtract(im.multiply(quaternionScalar.km)) //
              .add(jm.multiply(quaternionScalar.re)) //
              .add(km.multiply(quaternionScalar.im)) //
          , //
          re.multiply(quaternionScalar.km) //
              .add(im.multiply(quaternionScalar.jm)) //
              .subtract(jm.multiply(quaternionScalar.im)) //
              .add(km.multiply(quaternionScalar.re)) //
      );
    }
    if (scalar instanceof RealScalar)
      return of( //
          re.multiply(scalar), //
          im.multiply(scalar), //
          jm.multiply(scalar), //
          km.multiply(scalar));
    throw TensorRuntimeException.of(scalar);
  }

  @Override
  public Scalar negate() {
    return of( //
        re.negate(), //
        im.negate(), //
        jm.negate(), //
        km.negate());
  }

  @Override
  public Scalar reciprocal() {
    return conjugate().divide(AbsSquared.FUNCTION.apply(this));
  }

  @Override
  public Scalar abs() {
    return Sqrt.FUNCTION.apply(AbsSquared.FUNCTION.apply(this));
  }

  @Override
  public Number number() {
    throw TensorRuntimeException.of(this);
  }

  @Override
  public Scalar zero() {
    return RealScalar.ZERO;
  }

  @Override // from ComplexEmbedding
  public Scalar conjugate() {
    return of(re, im.negate(), jm.negate(), km.negate());
  }

  @Override // from ComplexEmbedding
  public Scalar real() {
    return re;
  }

  @Override // from ComplexEmbedding
  public Scalar imag() {
    return im;
  }

  // API not finalized
  public Tensor vectorPart() {
    return Tensors.of(im, jm, km);
  }

  @Override
  public int hashCode() {
    return Objects.hash(re, im, jm, km);
  }

  @Override
  public boolean equals(Object object) {
    if (object instanceof QuaternionScalar) {
      QuaternionScalar quaternionScalar = (QuaternionScalar) object;
      return re.equals(quaternionScalar.re) && //
          im.equals(quaternionScalar.im) && //
          jm.equals(quaternionScalar.jm) && //
          km.equals(quaternionScalar.km);
    }
    return false;
  }

  @Override
  public String toString() {
    return String.format("%s'%s'%s'%s", //
        re.toString(), //
        im.toString(), //
        jm.toString(), //
        km.toString());
  }
}
