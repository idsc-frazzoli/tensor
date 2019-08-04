// code by jph
package ch.ethz.idsc.tensor.num;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Objects;

import ch.ethz.idsc.tensor.AbstractScalar;
import ch.ethz.idsc.tensor.IntegerQ;
import ch.ethz.idsc.tensor.RationalScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.TensorRuntimeException;
import ch.ethz.idsc.tensor.sca.ExactScalarQInterface;
import ch.ethz.idsc.tensor.sca.PowerInterface;
import ch.ethz.idsc.tensor.sca.RoundingInterface;
import ch.ethz.idsc.tensor.sca.SignInterface;
import ch.ethz.idsc.tensor.sca.SqrtInterface;

/** scalars from finite field with prime number of elements and values
 * 0, 1, 2, ..., prime - 1 */
public class GaussScalar extends AbstractScalar implements //
    Comparable<Scalar>, ExactScalarQInterface, PowerInterface, RoundingInterface, //
    Serializable, SignInterface, SqrtInterface {
  /** @param value
   * @param prime number
   * @return value in finite field with prime number of elements
   * @throws Exception if given prime is not a prime number */
  public static GaussScalar of(BigInteger value, BigInteger prime) {
    return in(value, StaticHelper.assertIsProbablePrime(prime));
  }

  /** @param value
   * @param prime number
   * @return value in finite field with prime number of elements
   * @throws Exception if given prime is not a prime number */
  public static GaussScalar of(long value, long prime) {
    return of(BigInteger.valueOf(value), BigInteger.valueOf(prime));
  }

  // helper function
  private static GaussScalar in(BigInteger value, BigInteger prime) {
    return new GaussScalar(value.mod(prime), prime);
  }

  // ---
  private final BigInteger value;
  private final BigInteger prime;

  /** @param value non-negative
   * @param prime */
  private GaussScalar(BigInteger value, BigInteger prime) {
    this.value = value;
    this.prime = prime;
  }

  /***************************************************/
  @Override // from Scalar
  public GaussScalar abs() {
    return this;
  }

  @Override // from Scalar
  public GaussScalar reciprocal() {
    return new GaussScalar(value.modInverse(prime), prime);
  }

  @Override // from Scalar
  public GaussScalar negate() {
    return in(value.negate(), prime);
  }

  @Override // from Scalar
  public GaussScalar multiply(Scalar scalar) {
    if (scalar instanceof GaussScalar) {
      GaussScalar gaussScalar = (GaussScalar) scalar;
      return in(value.multiply(gaussScalar.value), prime);
    }
    throw TensorRuntimeException.of(this, scalar);
  }

  @Override // from Scalar
  public Number number() {
    return value;
  }

  @Override // from Scalar
  public Scalar zero() {
    return in(BigInteger.ZERO, prime);
  }

  /***************************************************/
  @Override // from AbstractScalar
  protected GaussScalar plus(Scalar scalar) {
    if (scalar instanceof GaussScalar) {
      GaussScalar gaussScalar = (GaussScalar) scalar;
      assertCommonBase(gaussScalar);
      return in(value.add(gaussScalar.value), prime);
    }
    throw TensorRuntimeException.of(this, scalar);
  }

  /***************************************************/
  @Override // from Comparable<Scalar>
  public int compareTo(Scalar scalar) {
    if (scalar instanceof GaussScalar) {
      GaussScalar gaussScalar = (GaussScalar) scalar;
      assertCommonBase(gaussScalar);
      return value.compareTo(gaussScalar.value);
    }
    throw TensorRuntimeException.of(this, scalar);
  }

  private void assertCommonBase(GaussScalar gaussScalar) {
    if (!prime.equals(gaussScalar.prime))
      throw TensorRuntimeException.of(this, gaussScalar);
  }

  @Override // from ExactScalarQInterface
  public boolean isExactScalar() {
    return true;
  }

  @Override // from PowerInterface
  public GaussScalar power(Scalar exponent) {
    if (IntegerQ.of(exponent)) {
      RationalScalar exp = (RationalScalar) exponent;
      return new GaussScalar(value.modPow(exp.numerator(), prime), prime);
    }
    throw TensorRuntimeException.of(this, exponent);
  }

  @Override // from RoundingInterface
  public Scalar ceiling() {
    return this;
  }

  @Override // from RoundingInterface
  public Scalar floor() {
    return this;
  }

  @Override // from RoundingInterface
  public Scalar round() {
    return this;
  }

  @Override // from SignInterface
  public int signInt() {
    return value.signum();
  }

  @Override // from SqrtInterface
  public GaussScalar sqrt() {
    // implementation is slow, could use memo function
    for (BigInteger index = BigInteger.ZERO; index.compareTo(prime) < 0; index = index.add(BigInteger.ONE))
      if (equals(in(index.multiply(index), prime)))
        return in(index, prime);
    throw TensorRuntimeException.of(this); // sqrt of this does not exist
  }

  /***************************************************/
  /** @return prime order of finite field */
  public BigInteger prime() {
    return prime;
  }

  /***************************************************/
  @Override // from AbstractScalar
  public int hashCode() {
    return Objects.hash(value, prime);
  }

  @Override // from AbstractScalar
  public boolean equals(Object object) {
    if (object instanceof GaussScalar) {
      GaussScalar gaussScalar = (GaussScalar) object;
      return value.equals(gaussScalar.value) //
          && prime.equals(gaussScalar.prime);
    }
    return false;
  }

  @Override // from AbstractScalar
  public String toString() {
    return "{\"value\": " + value + ", \"prime\": " + prime + "}";
  }
}
