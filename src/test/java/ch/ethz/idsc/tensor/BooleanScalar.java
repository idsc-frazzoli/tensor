// code by jph
package ch.ethz.idsc.tensor;

/* package */ final class BooleanScalar extends AbstractScalar implements Comparable<Scalar> {
  public static final Scalar TRUE = new BooleanScalar(true);
  public static final Scalar FALSE = new BooleanScalar(false);

  /** @param value
   * @return */
  public static Scalar of(boolean value) {
    return value ? TRUE : FALSE;
  }

  // ---
  private final boolean value;

  private BooleanScalar(boolean value) {
    this.value = value;
  }

  @Override
  public Scalar negate() {
    return this; // or throw ?
  }

  @Override
  public Scalar reciprocal() {
    return this; // or throw ?
  }

  @Override
  public Scalar abs() {
    return this; // or throw ?
  }

  @Override
  public Number number() {
    return value ? 1 : 0;
  }

  @Override
  public Scalar zero() {
    return of(false);
  }

  @Override
  public Scalar multiply(Scalar scalar) {
    if (scalar instanceof BooleanScalar) {
      BooleanScalar booleanScalar = (BooleanScalar) scalar;
      return of(Boolean.logicalAnd(value, booleanScalar.value));
    }
    throw TensorRuntimeException.of(this, scalar);
  }

  @Override
  protected Scalar plus(Scalar scalar) {
    if (scalar instanceof BooleanScalar) {
      BooleanScalar booleanScalar = (BooleanScalar) scalar;
      return of(Boolean.logicalXor(value, booleanScalar.value));
    }
    throw TensorRuntimeException.of(this, scalar);
  }

  @Override
  public int compareTo(Scalar scalar) {
    if (scalar instanceof BooleanScalar) {
      BooleanScalar booleanScalar = (BooleanScalar) scalar;
      return Boolean.compare(value, booleanScalar.value);
    }
    throw TensorRuntimeException.of(this, scalar);
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
