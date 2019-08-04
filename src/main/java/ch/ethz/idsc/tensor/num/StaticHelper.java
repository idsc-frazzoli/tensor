// code by jph
package ch.ethz.idsc.tensor.num;

import java.math.BigInteger;
import java.util.HashSet;
import java.util.Set;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;

/* package */ enum StaticHelper {
  ;
  private static final Set<BigInteger> PROBABLE_PRIMES = new HashSet<>();

  public static BigInteger assertIsProbablePrime(BigInteger prime) {
    if (!PROBABLE_PRIMES.contains(prime)) {
      if (!prime.isProbablePrime(20))
        throw new IllegalArgumentException("not a prime number " + prime);
      PROBABLE_PRIMES.add(prime);
    }
    return prime;
  }

  public static Scalar normalForm(Scalar scalar) {
    if (scalar instanceof RealScalar)
      return scalar.abs();
    return scalar;
  }
}
