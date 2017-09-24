// code by jph
package ch.ethz.idsc.tensor;

import java.util.Objects;

/** StringScalar represents a string.
 * 
 * <p>No mathematical operations are supported.
 * 
 * <p>For instance, when importing a csv file, the first line may contain
 * column header names which are imported as {@link StringScalar}s. */
public final class StringScalar extends AbstractScalar implements Comparable<Scalar> {
  /** @param string
   * @return new instance of {@link StringScalar} representing string */
  public static Scalar of(String string) {
    if (Objects.isNull(string))
      throw new IllegalArgumentException();
    return new StringScalar(string);
  }

  private final String string;

  private StringScalar(String string) {
    this.string = string;
  }

  /***************************************************/
  @Override // from Scalar
  public Scalar abs() {
    throw TensorRuntimeException.of(this);
  }

  @Override // from Scalar
  public Scalar reciprocal() {
    throw TensorRuntimeException.of(this);
  }

  @Override // from Scalar
  public Scalar multiply(Scalar scalar) {
    throw TensorRuntimeException.of(this, scalar);
  }

  @Override // from Scalar
  public Scalar negate() {
    throw TensorRuntimeException.of(this);
  }

  @Override // from Scalar
  public Number number() {
    throw TensorRuntimeException.of(this);
  }

  @Override
  public Scalar zero() {
    throw TensorRuntimeException.of(this);
  }

  /***************************************************/
  @Override // from AbstractScalar
  protected Scalar plus(Scalar scalar) {
    throw TensorRuntimeException.of(this, scalar);
  }

  @Override // from Comparable<Scalar>
  public int compareTo(Scalar scalar) {
    if (scalar instanceof StringScalar) {
      StringScalar stringScalar = (StringScalar) scalar;
      return string.compareTo(stringScalar.string);
    }
    throw TensorRuntimeException.of(this, scalar);
  }

  /***************************************************/
  @Override // from AbstractScalar
  public int hashCode() {
    return string.hashCode();
  }

  @Override // from AbstractScalar
  public boolean equals(Object object) {
    if (object instanceof StringScalar) {
      StringScalar stringScalar = (StringScalar) object;
      return string.equals(stringScalar.string);
    }
    return false;
  }

  @Override // from AbstractScalar
  public String toString() {
    return string;
  }
}
