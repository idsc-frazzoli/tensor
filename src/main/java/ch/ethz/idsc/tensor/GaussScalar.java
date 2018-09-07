// code by jph
package ch.ethz.idsc.tensor;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import ch.ethz.idsc.tensor.sca.ExactScalarQInterface;
import ch.ethz.idsc.tensor.sca.PowerInterface;
import ch.ethz.idsc.tensor.sca.SqrtInterface;

/** over finite field with prime number of elements denoted by
 * 0, 1, 2, ..., prime - 1 */
public class GaussScalar extends AbstractScalar implements //
    Comparable<Scalar>, ExactScalarQInterface, PowerInterface, Serializable, SqrtInterface {
  private static final Set<Long> PROBABLE_PRIMES = new HashSet<>();

  private static void assertIsProbablePrime(long prime) {
    if (!PROBABLE_PRIMES.contains(prime)) {
      if (!BigInteger.valueOf(prime).isProbablePrime(20))
        throw new IllegalArgumentException("not a prime number");
      PROBABLE_PRIMES.add(prime);
    }
  }

  /** @param value
   * @param prime
   * @return value in finite field with prime number of elements */
  public static Scalar of(long value, long prime) {
    assertIsProbablePrime(prime);
    return in(value, prime);
  }

  // helper function
  private static Scalar in(long value, long prime) {
    return new GaussScalar(((value % prime) + prime) % prime, prime);
  }

  // ---
  private final long value;
  private final long prime;

  private GaussScalar(long value, long prime) {
    this.value = value;
    this.prime = prime;
  }

  /***************************************************/
  @Override // from Scalar
  public Scalar abs() {
    return this;
  }

  @Override // from Scalar
  public Scalar reciprocal() {
    return in(new ExtendedGcd(value, prime).x, prime);
  }

  @Override // from Scalar
  public Scalar negate() {
    return in(-value, prime);
  }

  @Override // from Scalar
  public Scalar multiply(Scalar scalar) {
    if (scalar instanceof GaussScalar) {
      GaussScalar gaussScalar = (GaussScalar) scalar;
      return in(Math.multiplyExact(value, gaussScalar.value), prime);
    }
    throw TensorRuntimeException.of(this, scalar);
  }

  @Override // from Scalar
  public Number number() {
    return value;
  }

  @Override // from Scalar
  public Scalar zero() {
    return in(0, prime);
  }

  /***************************************************/
  @Override // from AbstractScalar
  protected Scalar plus(Scalar scalar) {
    if (scalar instanceof GaussScalar) {
      GaussScalar gaussScalar = (GaussScalar) scalar;
      if (prime != gaussScalar.prime)
        throw TensorRuntimeException.of(this, scalar);
      return in(Math.addExact(value, gaussScalar.value), prime);
    }
    throw TensorRuntimeException.of(this, scalar);
  }

  /***************************************************/
  @Override // from Comparable<Scalar>
  public int compareTo(Scalar scalar) {
    if (scalar instanceof GaussScalar) {
      GaussScalar gaussScalar = (GaussScalar) scalar;
      if (prime != gaussScalar.prime)
        throw TensorRuntimeException.of(this, scalar);
      return Long.compare(value, gaussScalar.value);
    }
    throw TensorRuntimeException.of(this, scalar);
  }

  @Override // from ExactScalarQInterface
  public boolean isExactScalar() {
    return true;
  }

  @Override // from PowerInterface
  public Scalar power(Scalar exponent) {
    if (Scalars.isZero(exponent))
      return in(1, prime);
    if (IntegerQ.of(exponent)) {
      RationalScalar rationalScalar = (RationalScalar) exponent;
      return Scalars.binaryPower(in(1, prime)).apply(this, rationalScalar.numerator());
    }
    throw TensorRuntimeException.of(this, exponent);
  }

  @Override // from SqrtInterface
  public Scalar sqrt() {
    // implementation is slow, could use memo function
    for (long index = 0; index < prime; ++index)
      if (equals(in(Math.multiplyExact(index, index), prime)))
        return in(index, prime);
    throw TensorRuntimeException.of(this); // sqrt of this does not exist
  }

  /***************************************************/
  /** @return prime order of finite field */
  public long prime() {
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
      return value == gaussScalar.value && prime == gaussScalar.prime;
    }
    return false;
  }

  @Override // from AbstractScalar
  public String toString() {
    return String.format("G:%d'%d", value, prime);
  }
}
