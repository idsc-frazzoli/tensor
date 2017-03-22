// code by jph
package ch.ethz.idsc.tensor;

import java.math.BigInteger;

// test
class IntegerScalar extends RealScalar {
  public static RealScalar of(BigInteger value) {
    return value.equals(BigInteger.ZERO) ? ZeroScalar.get() : new IntegerScalar(value);
  }

  private final BigInteger value;

  private IntegerScalar(BigInteger value) {
    this.value = value;
  }

  @Override
  public int compareTo(RealScalar o) {
    // TODO Auto-generated method stub
    return 0;
  }

  @Override
  protected boolean isPositive() {
    return 0 < value.signum();
  }

  @Override
  public RealScalar negate() {
    return of(value.negate());
  }

  @Override
  public Number number() {
    return value; // TODO
  }

  @Override
  protected Scalar plus(Scalar scalar) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Scalar multiply(Scalar scalar) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Scalar invert() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public int hashCode() {
    return value.hashCode();
  }

  @Override
  public boolean equals(Object object) {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public String toString() {
    return value.toString();
  }
}
