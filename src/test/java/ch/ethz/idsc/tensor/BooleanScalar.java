// code by jph
package ch.ethz.idsc.tensor;

import java.io.Serializable;

import ch.ethz.idsc.tensor.sca.ExactScalarQInterface;
import ch.ethz.idsc.tensor.sca.SqrtInterface;

/** arithmetic of BooleanScalar is as for an element of the finite field F with cardinality |F|=2
 * multiplication is logical AND
 * addition is logical XOR */
/* package */ final class BooleanScalar extends AbstractScalar implements //
    Comparable<Scalar>, ExactScalarQInterface, Serializable, SqrtInterface {
  /** instance with value true, toString() == "true" */
  public static final Scalar TRUE = new BooleanScalar(true);
  /** instance with value false, toString() == "false" */
  public static final Scalar FALSE = new BooleanScalar(false);

  /** @param value
   * @return */
  public static Scalar of(boolean value) {
    return value ? TRUE : FALSE;
  }

  // ---
  private final boolean value;

  // only invoked to initialize the static members
  private BooleanScalar(boolean value) {
    this.value = value;
  }

  @Override
  public Scalar negate() {
    return this;
  }

  @Override
  public Scalar reciprocal() {
    if (!value)
      throw TensorRuntimeException.of(this);
    return this;
  }

  @Override
  public Scalar abs() {
    return this;
  }

  @Override
  public Number number() {
    // since Boolean is not an instance of Number, we return type Integer
    return value ? 1 : 0;
  }

  @Override
  public Scalar zero() {
    return FALSE;
  }

  @Override // from Scalar
  public Scalar multiply(Scalar scalar) {
    if (scalar instanceof BooleanScalar) {
      BooleanScalar booleanScalar = (BooleanScalar) scalar;
      return of(Boolean.logicalAnd(value, booleanScalar.value));
    }
    throw TensorRuntimeException.of(this, scalar);
  }

  @Override // from AbstractScalar
  protected Scalar plus(Scalar scalar) {
    if (scalar instanceof BooleanScalar) {
      BooleanScalar booleanScalar = (BooleanScalar) scalar;
      return of(Boolean.logicalXor(value, booleanScalar.value));
    }
    throw TensorRuntimeException.of(this, scalar);
  }

  @Override // from Comparable
  public int compareTo(Scalar scalar) {
    if (scalar instanceof BooleanScalar) {
      BooleanScalar booleanScalar = (BooleanScalar) scalar;
      return Boolean.compare(value, booleanScalar.value);
    }
    throw TensorRuntimeException.of(this, scalar);
  }

  @Override // from ExactScalarQInterface
  public boolean isExactScalar() {
    return true;
  }

  @Override // from SqrtInterface
  public Scalar sqrt() {
    return this;
  }

  @Override
  public int hashCode() {
    return Boolean.hashCode(value);
  }

  @Override
  public boolean equals(Object object) {
    if (object instanceof BooleanScalar) {
      BooleanScalar booleanScalar = (BooleanScalar) object;
      return value == booleanScalar.value;
    }
    return false;
  }

  @Override
  public String toString() {
    return Boolean.toString(value);
  }
}
