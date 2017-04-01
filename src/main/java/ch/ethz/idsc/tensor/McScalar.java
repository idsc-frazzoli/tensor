// code by jph
package ch.ethz.idsc.tensor;

import ch.ethz.idsc.tensor.mat.IdentityMatrix;
import ch.ethz.idsc.tensor.mat.Inverse;
import ch.ethz.idsc.tensor.red.Norm;

// experimental towards a Quaternion scalar
class McScalar extends BasicScalar {
  public static Scalar of(Scalar re, Scalar im) {
    if (im.equals(ZeroScalar.get()))
      return re;
    return _of(Tensors.matrix(new Scalar[][] { //
        { re /*                */, im }, //
        { im.conjugate().negate(), re.conjugate() } }));
  }

  private static Scalar _of(Tensor skew) {
    if (skew.Get(0, 1).equals(ZeroScalar.get()))
      return skew.Get(0, 0);
    return new McScalar(skew);
  }

  private final Tensor skew;

  private McScalar(Tensor skew) {
    this.skew = skew;
  }

  /** @return real part */
  public Scalar real() {
    return skew.Get(0, 0);
  }

  /** @return imaginary part */
  public Scalar imag() {
    return skew.Get(0, 1);
  }

  @Override
  public Scalar abs() {
    return Norm._2.of(skew);
  }

  @Override
  public Scalar conjugate() {
    return of(real(), imag().negate());
  }

  @Override
  public Scalar multiply(Scalar scalar) {
    if (scalar instanceof McScalar) {
      McScalar cmp = (McScalar) scalar;
      return _of(skew.dot(cmp.skew)); //
    }
    return _of(skew.multiply(scalar));
  }

  @Override
  protected Scalar plus(Scalar scalar) {
    if (scalar instanceof McScalar) {
      McScalar complexScalar = (McScalar) scalar;
      return _of(skew.add(complexScalar.skew));
    }
    return _of(skew.add(IdentityMatrix.of(2).multiply(scalar)));
  }

  @Override
  public Scalar negate() {
    return _of(skew.negate());
  }

  @Override
  public Scalar invert() {
    return _of(Inverse.of(skew));
  }

  @Override
  public int hashCode() {
    return skew.hashCode();
  }

  @Override
  public boolean equals(Object object) {
    if (object == null)
      return false;
    if (object instanceof McScalar) {
      McScalar complexScalar = (McScalar) object;
      return skew.equals(complexScalar.skew);
    }
    return real().equals(object) && imag().equals(ZeroScalar.get());
  }

  @Override
  public String toString() {
    return skew.toString();
  }
}
