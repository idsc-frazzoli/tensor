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
public class Binomial {
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
    if (n < m)
      throw new RuntimeException(String.format("Binomial[%d,%d]", n, m));
    return _binomial(n).over(m);
  }

  /** @param n non-negative integer
   * @return binomial function that computes n choose k */
  public static Binomial of(Scalar n) {
    return of(Scalars.intValueExact(n));
  }

  /** @param n non-negative integer
   * @return binomial function that computes n choose k */
  public static Binomial of(int n) {
    if (n < 0)
      throw new RuntimeException(String.format("Binomial[%d]", n));
    return _binomial(n);
  }

  /***************************************************/
  static int MEMO_REUSE = 0;
  private static final int MEMO_SIZE = 100;
  private static final Map<Integer, Binomial> MEMO = new LinkedHashMap<Integer, Binomial>(MEMO_SIZE * 4 / 3, 0.75f, true) {
    @Override
    protected boolean removeEldestEntry(Map.Entry<Integer, Binomial> eldest) {
      return size() > MEMO_SIZE;
    }
  };

  // function does not require synchronized
  private static Binomial _binomial(int n) {
    Binomial binomial = MEMO.get(n);
    if (binomial == null) {
      binomial = new Binomial(n);
      MEMO.put(n, binomial);
    } else
      ++MEMO_REUSE;
    return binomial;
  }

  // ---
  private final int n;
  /* package for testing */ final Tensor row = Tensors.empty();

  private Binomial(int n) {
    this.n = n;
    row.append(RealScalar.ONE);
    int half = n / 2;
    for (int k = 1; k <= half; ++k)
      row.append(Last.of(row).multiply(RationalScalar.of(n - k + 1, k)));
  }

  /** @param k
   * @return n choose k */
  public Scalar over(int k) {
    return row.Get(Math.min(k, n - k));
  }
}
