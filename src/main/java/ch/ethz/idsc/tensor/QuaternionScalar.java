// code by jph
package ch.ethz.idsc.tensor;

// TODO work in progress
class QuaternionScalar extends AbstractScalar {
  public static Scalar of(Scalar re, Scalar im, Scalar jm, Scalar km) {
    // if (re instanceof ZeroScalar)
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

  @Override
  protected Scalar plus(Scalar scalar) {
    if (scalar instanceof QuaternionScalar) {
      QuaternionScalar quaternionScalar = (QuaternionScalar) scalar;
      return of( //
          re.add(quaternionScalar.re), //
          im.add(quaternionScalar.im), //
          jm.add(quaternionScalar.jm), //
          km.add(quaternionScalar.km));
    }
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
    return null;
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
  public Scalar invert() {
    return null;
  }

  @Override
  public Scalar abs() {
    return null;
  }

  @Override
  public Number number() {
    throw TensorRuntimeException.of(this);
  }

  @Override
  public int hashCode() {
    return 0;
  }

  @Override
  public boolean equals(Object object) {
    return false;
  }

  @Override
  public String toString() {
    return null;
  }
}
