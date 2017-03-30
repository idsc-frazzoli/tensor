// code by jph
package ch.ethz.idsc.tensor;

/** StringScalar represents unparsed strings
 * <br/>
 * for instance the first line of a csv file may contain column
 * headers which are imported as StringScalars */
public final class StringScalar extends AbstractScalar {
  /** @param string
   * @return new instance of {@link StringScalar} representing string */
  public static Scalar of(String string) {
    if (string == null)
      throw new IllegalArgumentException();
    return new StringScalar(string);
  }

  private final String string;

  private StringScalar(String string) {
    this.string = string;
  }

  @Override // from Scalar
  public Scalar invert() {
    throw TensorRuntimeException.of(this);
  }

  @Override // from Scalar
  public Scalar multiply(Scalar scalar) {
    throw TensorRuntimeException.of(this);
  }

  @Override // from Scalar
  protected Scalar plus(Scalar scalar) {
    throw TensorRuntimeException.of(this);
  }

  @Override // from Scalar
  public Scalar negate() {
    throw TensorRuntimeException.of(this);
  }

  @Override // from Scalar
  public int hashCode() {
    return string.hashCode();
  }

  @Override // from Scalar
  public boolean equals(Object object) {
    // null check not required
    if (object instanceof StringScalar) {
      StringScalar stringScalar = (StringScalar) object;
      return string.equals(stringScalar.string);
    }
    return false;
  }

  @Override // from Scalar
  public String toString() {
    return string;
  }
}
