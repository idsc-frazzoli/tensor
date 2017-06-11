// code by jph
package ch.ethz.idsc.tensor.alg;

import java.util.LinkedHashMap;
import java.util.Map;

import ch.ethz.idsc.tensor.RationalScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Scalars;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;

/** binomial coefficient implemented for integer input
 * 
 * <p>inspired by
 * <a href="https://reference.wolfram.com/language/ref/Binomial.html">Binomial</a> */
public enum Binomial {
  ;
  // ---
  /** Mathematica::Binomial[n, m]
   * 
   * @param n scalar that satisfies IntegerQ
   * @param m scalar that satisfies IntegerQ, and m <= n
   * @return binomial coefficient defined by n and m */
  public static Scalar of(Scalar n, Scalar m) {
    return of(Scalars.intValueExact(n), Scalars.intValueExact(m));
  }

  /** Mathematica::Binomial[n, m]
   * 
   * @param n
   * @param m <= n
   * @return binomial coefficient defined by n and m */
  public static Scalar of(int n, int m) {
    if (n < m || n < 0)
      throw new RuntimeException(String.format("Binomial[%d,%d]", n, m));
    return row(n).Get(Math.min(m, n - m));
  }

  /***************************************************/
  private static final int MEMO_SIZE = 40;
  private static final Map<Integer, Tensor> MEMO = new LinkedHashMap<Integer, Tensor>(MEMO_SIZE * 4 / 3, 0.75f, true) {
    @Override
    protected boolean removeEldestEntry(Map.Entry<Integer, Tensor> eldest) {
      return size() > MEMO_SIZE;
    }
  };

  /* package for testing */ static Tensor row(int n) {
    if (!MEMO.containsKey(n))
      MEMO.put(n, _row(n));
    return MEMO.get(n);
  }

  private static Tensor _row(int n) {
    Tensor row = Tensors.empty();
    row.append(RealScalar.ONE);
    int half = n / 2;
    for (int k = 1; k <= half; ++k)
      row.append(Last.of(row).multiply(RationalScalar.of(n - k + 1, k)));
    return row;
  }
}
