// code by jph
package ch.ethz.idsc.tensor;

import java.math.BigInteger;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import ch.ethz.idsc.tensor.alg.BinaryExponentiation;
import ch.ethz.idsc.tensor.sca.PowerInterface;
import ch.ethz.idsc.tensor.sca.SqrtInterface;

/** over finite field with prime number of elements denoted by
 * 0, 1, 2, ..., prime - 1 */
public class GaussScalar extends AbstractScalar implements //
    Comparable<Scalar>, //
    PowerInterface, //
    SqrtInterface //
{
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

  @Override // from Scalar
  public Scalar negate() {
    return of(-value, prime);
  }

  @Override // from Scalar
  public Scalar invert() {
    return of(new ExtendedGcd(value, prime).x, prime);
  }

  @Override // from Scalar
  public Number number() {
    return value;
  }

  @Override // from Scalar
  public Scalar abs() {
    return this;
  }

  @Override // from Scalar
  public Scalar multiply(Scalar scalar) {
    if (scalar instanceof GaussScalar) {
      GaussScalar gaussScalar = (GaussScalar) scalar;
      return of(value * gaussScalar.value, prime);
    }
    assertInstanceOfZeroScalar(scalar);
    return scalar.multiply(this);
  }

  @Override // from AbstractScalar
  protected Scalar plus(Scalar scalar) {
    if (scalar instanceof GaussScalar) {
      GaussScalar gaussScalar = (GaussScalar) scalar;
      return of(value + gaussScalar.value, prime);
    }
    assertInstanceOfZeroScalar(scalar);
    return scalar.add(this);
  }

  @Override // from SqrtInterface
  public Scalar sqrt() {
    // TODO implementation is slow
    for (long index = 1; index < prime; ++index) {
      GaussScalar candidate = (GaussScalar) of(index, prime);
      GaussScalar square = (GaussScalar) candidate.multiply(candidate);
      if (value == square.value)
        return candidate;
    }
    throw TensorRuntimeException.of(this);
  }

  @Override // from PowerInterface
  public Scalar power(Scalar exponent) {
    if (exponent instanceof ZeroScalar)
      return of(1, prime);
    if (exponent instanceof RationalScalar) {
      RationalScalar ratio = (RationalScalar) exponent;
      if (ratio.isInteger()) {
        long exp = ratio.numerator().longValueExact();
        return 0 <= exp ? //
            BinaryExponentiation.positive(Scalars.binaryExponentiation(this, of(1, prime)), exp) : //
            BinaryExponentiation.positive(Scalars.binaryExponentiation(invert(), of(1, prime)), -exp);
      }
    }
    throw TensorRuntimeException.of(exponent);
  }

  @Override // from Comparable<Scalar>
  public int compareTo(Scalar scalar) {
    if (scalar instanceof ZeroScalar)
      return Long.compare(value, 0);
    if (scalar instanceof GaussScalar) {
      GaussScalar gaussScalar = (GaussScalar) scalar;
      if (prime != gaussScalar.prime)
        throw TensorRuntimeException.of(this);
      return Long.compare(value, gaussScalar.value);
    }
    throw TensorRuntimeException.of(this);
  }

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
    return object == null ? false : object.equals(this);
  }

  @Override // from AbstractScalar
  public String toString() {
    // TODO check for unicode symbol tripple ===
    return String.format("%d'%d", value, prime);
  }
}
