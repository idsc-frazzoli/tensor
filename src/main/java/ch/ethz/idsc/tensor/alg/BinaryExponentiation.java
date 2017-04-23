// code by jph
package ch.ethz.idsc.tensor.alg;

import java.util.BitSet;

/** https://en.wikipedia.org/wiki/Exponentiation_by_squaring 
 * 
 * interface use by MatrixPower and GaussScalar */
// EXPERIMENTAL
public interface BinaryExponentiation<T> {
  T zeroth();

  T square(T object);

  T raise(T object);

  public static <T> T positive(BinaryExponentiation<T> fastPower, long exponent) {
    T tensor = fastPower.zeroth();
    // TODO check reference code if use of bitset can be circumvented
    BitSet bitSet = BitSet.valueOf(new long[] { exponent });
    for (int bitIndex = log2(exponent); 0 <= bitIndex; --bitIndex) {
      tensor = fastPower.square(tensor);
      if (bitSet.get(bitIndex))
        tensor = fastPower.raise(tensor);
    }
    return tensor;
  }

  /** @param exponent
   * @return -1 in case exponent == 0, else floor(log2(exponent) */
  static int log2(long exponent) {
    return 63 - Long.numberOfLeadingZeros(exponent);
  }
}
