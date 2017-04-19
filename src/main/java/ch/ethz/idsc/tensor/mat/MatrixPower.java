// code by jph
package ch.ethz.idsc.tensor.mat;

import java.util.BitSet;

import ch.ethz.idsc.tensor.Tensor;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/MatrixPower.html">MatrixPower</a> */
public enum MatrixPower {
  ;
  /** @param m square matrix
   * @param exponent
   * @return m ^ exponent */
  public static Tensor of(Tensor m, long exponent) {
    return 0 <= exponent ? positive(m, exponent) : positive(Inverse.of(m), -exponent);
  }

  // helper function
  private static Tensor positive(Tensor matrix, long exponent) {
    Tensor tensor = IdentityMatrix.of(matrix.length());
    BitSet bitSet = BitSet.valueOf(new long[] { exponent });
    for (int bitIndex = log2(exponent); 0 <= bitIndex; --bitIndex) {
      tensor = tensor.dot(tensor);
      if (bitSet.get(bitIndex))
        tensor = tensor.dot(matrix);
    }
    return tensor;
  }

  /** @param exponent
   * @return -1 in case exponent == 0, else floor(log2(exponent) */
  private static int log2(long exponent) {
    return 63 - Long.numberOfLeadingZeros(exponent);
  }
}
