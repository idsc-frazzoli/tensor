// code by jph
package ch.ethz.idsc.tensor.qty;

import java.io.Serializable;
import java.math.MathContext;
import java.util.Objects;

import ch.ethz.idsc.tensor.AbstractScalar;
import ch.ethz.idsc.tensor.ExactScalarQ;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.TensorRuntimeException;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.red.Norm;
import ch.ethz.idsc.tensor.sca.AbsSquared;
import ch.ethz.idsc.tensor.sca.Chop;
import ch.ethz.idsc.tensor.sca.ChopInterface;
import ch.ethz.idsc.tensor.sca.ComplexEmbedding;
import ch.ethz.idsc.tensor.sca.ExactScalarQInterface;
import ch.ethz.idsc.tensor.sca.N;
import ch.ethz.idsc.tensor.sca.NInterface;
import ch.ethz.idsc.tensor.sca.Sqrt;

/* package */ class QuaternionImpl extends AbstractScalar implements Quaternion, //
    ChopInterface, ExactScalarQInterface, NInterface, Serializable {
  private static final Scalar TWO = RealScalar.of(2);
  // ---
  private final Scalar re;
  private final Scalar im;
  private final Scalar jm;
  private final Scalar km;

  QuaternionImpl(Scalar re, Scalar im, Scalar jm, Scalar km) {
    this.re = re;
    this.im = im;
    this.jm = jm;
    this.km = km;
  }

  @Override // from AbstractScalar
  protected Scalar plus(Scalar scalar) {
    if (scalar instanceof Quaternion) {
      Quaternion quaternion = (Quaternion) scalar;
      return Quaternion.of(re.add(quaternion.re()), im.add(quaternion.im()), jm.add(quaternion.jm()), km.add(quaternion.km()));
    }
    if (scalar instanceof ComplexEmbedding) {
      ComplexEmbedding complexEmbedding = (ComplexEmbedding) scalar;
      return Quaternion.of(re.add(complexEmbedding.real()), im.add(complexEmbedding.imag()), jm, km);
    }
    return scalar.add(this);
  }

  @Override
  public Scalar multiply(Scalar scalar) {
    if (scalar instanceof Quaternion) {
      Quaternion quaternion = (Quaternion) scalar;
      return Quaternion.of( //
          re.multiply(quaternion.re()) //
              .subtract(im.multiply(quaternion.im())) //
              .subtract(jm.multiply(quaternion.jm())) //
              .subtract(km.multiply(quaternion.km())) //
          , //
          re.multiply(quaternion.im()) //
              .add(im.multiply(quaternion.re())) //
              .add(jm.multiply(quaternion.km())) //
              .subtract(km.multiply(quaternion.jm())) //
          , //
          re.multiply(quaternion.jm()) //
              .subtract(im.multiply(quaternion.km())) //
              .add(jm.multiply(quaternion.re())) //
              .add(km.multiply(quaternion.im())) //
          , //
          re.multiply(quaternion.km()) //
              .add(im.multiply(quaternion.jm())) //
              .subtract(jm.multiply(quaternion.im())) //
              .add(km.multiply(quaternion.re())) //
      );
    }
    if (scalar instanceof RealScalar)
      return Quaternion.of(re.multiply(scalar), im.multiply(scalar), jm.multiply(scalar), km.multiply(scalar));
    if (scalar instanceof ComplexEmbedding) {
      ComplexEmbedding complexEmbedding = (ComplexEmbedding) scalar;
      return Quaternion.of( //
          re.multiply(complexEmbedding.real()) //
              .subtract(im.multiply(complexEmbedding.imag())), //
          im.multiply(complexEmbedding.real()) //
              .add(re.multiply(complexEmbedding.imag())), //
          jm.multiply(complexEmbedding.real()) //
              .add(km.multiply(complexEmbedding.imag())), //
          km.multiply(complexEmbedding.real()) //
              .subtract(jm.multiply(complexEmbedding.imag())) //
      );
    }
    throw TensorRuntimeException.of(scalar);
  }

  @Override
  public Scalar negate() {
    return new QuaternionImpl(re.negate(), im.negate(), jm.negate(), km.negate());
  }

  @Override
  public Scalar reciprocal() {
    return conjugate().divide(AbsSquared.FUNCTION.apply(this));
  }

  @Override
  public Scalar abs() {
    return Norm._2.ofVector(Tensors.of(re, im, jm, km));
  }

  @Override
  public Number number() {
    throw TensorRuntimeException.of(this);
  }

  @Override
  public Scalar zero() {
    return RealScalar.ZERO;
  }

  /***************************************************/
  @Override // from ChopInterface
  public Scalar chop(Chop chop) {
    return Quaternion.of(chop.apply(re), chop.apply(im), chop.apply(jm), chop.apply(km));
  }

  @Override // from ConjugateInterface
  public Scalar conjugate() {
    return new QuaternionImpl(re, im.negate(), jm.negate(), km.negate());
  }

  @Override // from ExactScalarQInterface
  public boolean isExactScalar() {
    return ExactScalarQ.of(re) && ExactScalarQ.of(im) //
        && ExactScalarQ.of(jm) && ExactScalarQ.of(km);
  }

  @Override // from NInterface
  public Scalar n() {
    return Quaternion.of( //
        re.number().doubleValue(), im.number().doubleValue(), //
        jm.number().doubleValue(), km.number().doubleValue());
  }

  @Override // from NInterface
  public Scalar n(MathContext mathContext) {
    N n = N.in(mathContext.getPrecision());
    return Quaternion.of(n.apply(re), n.apply(im), n.apply(jm), n.apply(km));
  }

  @Override // from SqrtInterface
  public Scalar sqrt() {
    Scalar nre = Sqrt.FUNCTION.apply(re.add(abs()).multiply(TWO));
    return Quaternion.of(nre.divide(TWO), im.divide(nre), jm.divide(nre), km.divide(nre));
  }

  /***************************************************/
  @Override // from Quaternion
  public Scalar re() {
    return re;
  }

  @Override // from Quaternion
  public Scalar im() {
    return im;
  }

  @Override // from Quaternion
  public Scalar jm() {
    return jm;
  }

  @Override // from Quaternion
  public Scalar km() {
    return km;
  }

  /***************************************************/
  @Override // from AbstractScalar
  public int hashCode() {
    return Objects.hash(re, im, jm, km);
  }

  @Override // from AbstractScalar
  public boolean equals(Object object) {
    if (object instanceof Quaternion) {
      Quaternion quaternion = (Quaternion) object;
      return re.equals(quaternion.re()) && im.equals(quaternion.im()) && //
          jm.equals(quaternion.jm()) && km.equals(quaternion.km());
    }
    return false;
  }

  @Override // from AbstractScalar
  public String toString() {
    return String.format("Q:%s'%s'%s'%s", re, im, jm, km);
  }
}
