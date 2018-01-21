// code by jph
package ch.ethz.idsc.tensor;

import java.io.Serializable;

import ch.ethz.idsc.tensor.mat.IdentityMatrix;
import ch.ethz.idsc.tensor.mat.Inverse;
import ch.ethz.idsc.tensor.red.Norm;
import ch.ethz.idsc.tensor.sca.ComplexEmbedding;
import ch.ethz.idsc.tensor.sca.Conjugate;

// alternative implementation of a Quaternion that uses 2 complex scalars instead of 4 reals
/* package */ final class McScalar extends AbstractScalar implements ComplexEmbedding, Serializable {
  public static Scalar of(Scalar re, Scalar im) {
    if (Scalars.isZero(im))
      return re;
    return _of(Tensors.matrix(new Scalar[][] { //
        { re /*                              */, im }, //
        { Conjugate.FUNCTION.apply(im).negate(), Conjugate.FUNCTION.apply(re) } }));
  }

  private static Scalar _of(Tensor skew) {
    if (skew.Get(0, 1).equals(RealScalar.ZERO))
      return skew.Get(0, 0);
    return new McScalar(skew);
  }

  private final Tensor skew;

  private McScalar(Tensor skew) {
    this.skew = skew;
  }

  /** @return real part */
  @Override
  public Scalar real() {
    return skew.Get(0, 0);
  }

  /** @return imaginary part */
  @Override
  public Scalar imag() {
    return skew.Get(0, 1);
  }

  @Override // from ComplexEmbedding
  public Scalar conjugate() {
    return of(real(), imag().negate());
  }

  @Override
  public Scalar abs() {
    return Norm._2.ofMatrix(skew);
  }

  @Override
  public Number number() {
    throw TensorRuntimeException.of(this);
  }

  @Override
  public Scalar zero() {
    return RealScalar.ZERO;
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
  public Scalar reciprocal() {
    return _of(Inverse.of(skew));
  }

  @Override
  public int hashCode() {
    return skew.hashCode();
  }

  @Override
  public boolean equals(Object object) {
    if (object instanceof McScalar) {
      McScalar complexScalar = (McScalar) object;
      return skew.equals(complexScalar.skew);
    }
    return real().equals(object) && imag().equals(imag().zero());
  }

  @Override
  public String toString() {
    return skew.toString();
  }
}
