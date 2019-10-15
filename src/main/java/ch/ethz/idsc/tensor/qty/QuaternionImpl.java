// code by jph
// https://en.wikipedia.org/wiki/Quaternion
package ch.ethz.idsc.tensor.qty;

import java.io.Serializable;
import java.math.MathContext;
import java.util.Objects;

import ch.ethz.idsc.tensor.AbstractScalar;
import ch.ethz.idsc.tensor.ExactScalarQ;
import ch.ethz.idsc.tensor.ExactScalarQInterface;
import ch.ethz.idsc.tensor.ExactTensorQ;
import ch.ethz.idsc.tensor.RationalScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Scalars;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.TensorRuntimeException;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.lie.Cross;
import ch.ethz.idsc.tensor.red.Norm;
import ch.ethz.idsc.tensor.sca.ArcCos;
import ch.ethz.idsc.tensor.sca.Chop;
import ch.ethz.idsc.tensor.sca.ChopInterface;
import ch.ethz.idsc.tensor.sca.ComplexEmbedding;
import ch.ethz.idsc.tensor.sca.Cos;
import ch.ethz.idsc.tensor.sca.Exp;
import ch.ethz.idsc.tensor.sca.Log;
import ch.ethz.idsc.tensor.sca.N;
import ch.ethz.idsc.tensor.sca.NInterface;
import ch.ethz.idsc.tensor.sca.Power;
import ch.ethz.idsc.tensor.sca.Sin;
import ch.ethz.idsc.tensor.sca.Sqrt;

/* package */ class QuaternionImpl extends AbstractScalar implements Quaternion, //
    ChopInterface, ExactScalarQInterface, NInterface, Serializable {
  private final Scalar w;
  private final Tensor xyz;

  /* package */ QuaternionImpl(Scalar w, Tensor xyz) {
    this.w = w;
    this.xyz = xyz;
  }

  @Override // from AbstractScalar
  protected Quaternion plus(Scalar scalar) {
    if (scalar instanceof Quaternion) {
      Quaternion quaternion = (Quaternion) scalar;
      return new QuaternionImpl(w.add(quaternion.w()), xyz.add(quaternion.xyz()));
    }
    throw TensorRuntimeException.of(this, scalar);
  }

  @Override // from Quaternion
  public Quaternion multiply(Scalar scalar) {
    if (scalar instanceof Quaternion) {
      Quaternion quaternion = (Quaternion) scalar;
      return new QuaternionImpl( //
          w.multiply(quaternion.w()).subtract(xyz.dot(quaternion.xyz())), //
          xyz.multiply(quaternion.w()).add(quaternion.xyz().multiply(w())).add(Cross.of(xyz, quaternion.xyz())));
    }
    if (scalar instanceof RealScalar)
      return new QuaternionImpl(w.multiply(scalar), xyz.multiply(scalar));
    if (scalar instanceof ComplexEmbedding) {
      ComplexEmbedding complexEmbedding = (ComplexEmbedding) scalar;
      Scalar imag = complexEmbedding.imag();
      return multiply(new QuaternionImpl( //
          complexEmbedding.real(), //
          Tensors.of(imag, imag.zero(), imag.zero())));
    }
    throw TensorRuntimeException.of(scalar);
  }

  @Override // from Quaternion
  public Quaternion negate() {
    return new QuaternionImpl(w.negate(), xyz.negate());
  }

  @Override // from Quaternion
  public Quaternion divide(Scalar scalar) {
    return multiply(scalar.reciprocal());
  }

  @Override // from Quaternion
  public Quaternion reciprocal() {
    Quaternion conjugate = conjugate();
    return conjugate.divide(multiply(conjugate).w());
  }

  @Override // from Scalar
  public Scalar abs() {
    return Norm._2.ofVector(xyz.copy().append(w));
  }

  @Override // from Scalar
  public Number number() {
    throw TensorRuntimeException.of(this);
  }

  @Override // from Scalar
  public Scalar zero() {
    return Quaternion.ZERO;
  }

  /***************************************************/
  @Override // from ChopInterface
  public Quaternion chop(Chop chop) {
    return new QuaternionImpl(chop.apply(w), xyz.map(chop));
  }

  @Override // from ConjugateInterface
  public Quaternion conjugate() {
    return new QuaternionImpl(w, xyz.negate());
  }

  @Override // from ExpInterface
  public Quaternion exp() {
    Scalar vn = Norm._2.ofVector(xyz);
    return new QuaternionImpl( //
        Cos.FUNCTION.apply(vn), //
        xyz.multiply(Sin.FUNCTION.apply(vn).divide(vn))) //
            .multiply(Exp.FUNCTION.apply(w));
  }

  @Override // from ExactScalarQInterface
  public boolean isExactScalar() {
    return ExactScalarQ.of(w) //
        && ExactTensorQ.of(xyz);
  }

  @Override // from LogInterface
  public Quaternion log() {
    Scalar abs = abs();
    Scalar vn = Norm._2.ofVector(xyz);
    return new QuaternionImpl( //
        Log.FUNCTION.apply(abs), //
        xyz.multiply(ArcCos.FUNCTION.apply(w.divide(abs))).divide(vn));
  }

  @Override // from NInterface
  public Scalar n() {
    return new QuaternionImpl(N.DOUBLE.apply(w), xyz.map(N.DOUBLE));
  }

  @Override // from NInterface
  public Scalar n(MathContext mathContext) {
    N n = N.in(mathContext.getPrecision());
    return new QuaternionImpl(n.apply(w), xyz.map(n));
  }

  @Override // from PowerInterface
  public Quaternion power(Scalar exponent) {
    Scalar abs = abs();
    Scalar et = exponent.multiply(ArcCos.FUNCTION.apply(w.divide(abs)));
    Scalar qa = Power.of(abs, exponent);
    Scalar vn = Norm._2.ofVector(xyz);
    return new QuaternionImpl( //
        Cos.FUNCTION.apply(et).multiply(qa), //
        Scalars.isZero(vn) //
            ? xyz.map(Scalar::zero)
            : xyz.multiply(Sin.FUNCTION.apply(et).multiply(qa).divide(vn)));
  }

  @Override // from SqrtInterface
  public Quaternion sqrt() {
    Scalar w_abs = w.add(abs());
    Scalar nre = Sqrt.FUNCTION.apply(w_abs.add(w_abs));
    return new QuaternionImpl(nre.multiply(RationalScalar.HALF), xyz.divide(nre));
  }

  /***************************************************/
  @Override // from Quaternion
  public Scalar w() {
    return w;
  }

  @Override // from Quaternion
  public Tensor xyz() {
    return xyz.unmodifiable();
  }

  /***************************************************/
  @Override // from AbstractScalar
  public int hashCode() {
    return Objects.hash(w, xyz);
  }

  @Override // from AbstractScalar
  public boolean equals(Object object) {
    if (object instanceof Quaternion) {
      Quaternion quaternion = (Quaternion) object;
      return w.equals(quaternion.w()) //
          && xyz.equals(quaternion.xyz());
    }
    return false;
  }

  @Override // from AbstractScalar
  public String toString() {
    return "{\"w\": " + w + ", \"xyz\": " + xyz + "}";
  }
}
