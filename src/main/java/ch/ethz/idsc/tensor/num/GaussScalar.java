// code by jph
package ch.ethz.idsc.tensor.num;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import ch.ethz.idsc.tensor.AbstractScalar;
import ch.ethz.idsc.tensor.IntegerQ;
import ch.ethz.idsc.tensor.RationalScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Scalars;
import ch.ethz.idsc.tensor.TensorRuntimeException;
import ch.ethz.idsc.tensor.sca.ExactScalarQInterface;
import ch.ethz.idsc.tensor.sca.PowerInterface;
import ch.ethz.idsc.tensor.sca.SqrtInterface;

/** over finite field with prime number of elements denoted by
 * 0, 1, 2, ..., prime - 1 */
public class GaussScalar extends AbstractScalar implements //
    Comparable<Scalar>, ExactScalarQInterface, PowerInterface, Serializable, SqrtInterface {
  private static final Set<BigInteger> PROBABLE_PRIMES = new HashSet<>();

  private static BigInteger assertIsProbablePrime(BigInteger prime) {
    if (!PROBABLE_PRIMES.contains(prime)) {
      if (!prime.isProbablePrime(20))
        throw new IllegalArgumentException("not a prime number " + prime);
      PROBABLE_PRIMES.add(prime);
    }
    return prime;
  }

  /** @param value
   * @param prime
   * @return value in finite field with prime number of elements */
  public static GaussScalar of(long value, long prime) {
    BigInteger _prime = BigInteger.valueOf(prime);
    return in(BigInteger.valueOf(value), assertIsProbablePrime(_prime));
  }

  // helper function
  private static GaussScalar in(BigInteger value, BigInteger prime) {
    return new GaussScalar(value.mod(prime), prime);
  }

  // ---
  private final BigInteger value;
  private final BigInteger prime;

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
    return in(value.modInverse(prime), prime);
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
    if (Scalars.isZero(exponent))
      return in(BigInteger.ONE, prime);
    if (IntegerQ.of(exponent)) {
      RationalScalar rationalScalar = (RationalScalar) exponent;
      return Scalars.binaryPower(in(BigInteger.ONE, prime)).apply(this, rationalScalar.numerator());
    }
    throw TensorRuntimeException.of(this, exponent);
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
    return "G:" + value + "'" + prime;
  }
}