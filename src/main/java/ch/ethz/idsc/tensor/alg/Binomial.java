// code by jph
package ch.ethz.idsc.tensor.alg;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

import ch.ethz.idsc.tensor.IntegerQ;
import ch.ethz.idsc.tensor.RationalScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Scalars;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.sca.Gamma;

/** binomial coefficient implemented for integer input
 * <pre>
 * Gamma[n+1] / ( Gamma[m+1] Gamma[n-m+1] )
 * </pre>
 * 
 * <p>inspired by
 * <a href="https://reference.wolfram.com/language/ref/Binomial.html">Binomial</a> */
public class Binomial {
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
    return binomial(n);
  }

  /** <code>Mathematica::Binomial[n, m]</code>
   * 
   * @param n
   * @param m, and m <= n
   * @return binomial coefficient defined by n and m */
  public static Scalar of(Scalar n, Scalar m) {
    if (IntegerQ.of(n) && IntegerQ.of(m))
      return of(Scalars.intValueExact(n), Scalars.intValueExact(m));
    Scalar np1 = n.add(RealScalar.ONE);
    return Gamma.FUNCTION.apply(np1).divide( //
        Gamma.FUNCTION.apply(m.add(RealScalar.ONE)).multiply(Gamma.FUNCTION.apply(np1.subtract(m))));
  }

  /** <code>Mathematica::Binomial[n, m]</code>
   * 
   * @param n
   * @param m <= n
   * @return binomial coefficient defined by n and m */
  public static Scalar of(int n, int m) {
    if (n < m) {
      if (0 <= n)
        return RealScalar.ZERO;
      // LONGTERM this case is defined in Mathematica
      throw new RuntimeException(String.format("Binomial[%d,%d]", n, m));
    }
    return binomial(n).over(m);
  }

  /***************************************************/
  /* package for testing */ static int MEMO_REUSE = 0;
  private static final int MEMO_SIZE = 100;
  private static final Map<Integer, Binomial> MEMO = new LinkedHashMap<Integer, Binomial>(MEMO_SIZE * 4 / 3, 0.75f, true) {
    @Override
    protected boolean removeEldestEntry(Map.Entry<Integer, Binomial> eldest) {
      return size() > MEMO_SIZE;
    }
  };

  // function does not require synchronized
  private static Binomial binomial(int n) {
    Binomial binomial = MEMO.get(n);
    if (Objects.isNull(binomial)) {
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
    return 0 <= k ? row.Get(Math.min(k, n - k)) : RealScalar.ZERO;
  }
}
