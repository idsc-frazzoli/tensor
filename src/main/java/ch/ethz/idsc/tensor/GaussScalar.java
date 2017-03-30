// code by jph
package ch.ethz.idsc.tensor;

import java.math.BigInteger;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/** over finite field with prime number of elements denoted by
 * 0, 1, 2, ..., prime - 1 */
// class may be a misnomer
public class GaussScalar extends AbstractScalar implements ExactPrecision {
  private static final Set<Long> primes = new HashSet<>();

  private static void assertIsProbablePrime(long prime) {
    if (!primes.contains(prime)) {
      if (!BigInteger.valueOf(prime).isProbablePrime(20))
        throw new IllegalArgumentException("not a prime number");
      primes.add(prime);
    }
  }

  private static void assertInstanceOfZeroScalar(Scalar scalar) {
    if (!(scalar instanceof ZeroScalar))
      throw new IllegalArgumentException();
  }

  public static Scalar of(long value, long prime) {
    assertIsProbablePrime(prime);
    long _value = ((value % prime) + prime) % prime;
    return _value == 0 ? ZeroScalar.get() : new GaussScalar(_value, prime);
  }

  private final long value;
  private final long prime;

  private GaussScalar(long value, long prime) {
    this.value = value;
    this.prime = prime;
  }

  /** @return value in the range 0, 1, ..., getPrime()-1 */
  public long getValue() {
    return value;
  }

  /** @return prime order of finite field */
  public long getPrime() {
    return prime;
  }

  @Override
  public Scalar multiply(Scalar scalar) {
    if (scalar instanceof GaussScalar) {
      GaussScalar gaussScalar = (GaussScalar) scalar;
      return of(value * gaussScalar.value, prime);
    }
    assertInstanceOfZeroScalar(scalar);
    return scalar.multiply(this);
  }

  @Override
  protected Scalar plus(Scalar scalar) {
    if (scalar instanceof GaussScalar) {
      GaussScalar gaussScalar = (GaussScalar) scalar;
      return of(value + gaussScalar.value, prime);
    }
    assertInstanceOfZeroScalar(scalar);
    return scalar.add(this);
  }

  @Override
  public Scalar negate() {
    return of(-value, prime);
  }

  @Override
  public Scalar invert() {
    return of(new ExtendedGcd(value, prime).x, prime);
  }

  @Override
  public Number number() {
    return value;
  }

  @Override
  public int hashCode() {
    return Objects.hash(value, prime);
  }

  @Override
  public boolean equals(Object object) {
    if (object instanceof GaussScalar) {
      GaussScalar gaussScalar = (GaussScalar) object;
      return value == gaussScalar.value && prime == gaussScalar.prime;
    }
    return object == null ? false : object.equals(this);
  }

  @Override
  public String toString() {
    return String.format("(%d'%d)", value, prime);
  }
}
