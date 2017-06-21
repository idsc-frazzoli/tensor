// code by jph
package ch.ethz.idsc.tensor.pdf;

import ch.ethz.idsc.tensor.RationalScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Scalars;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.TensorRuntimeException;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.alg.Reverse;
import ch.ethz.idsc.tensor.red.Total;
import ch.ethz.idsc.tensor.sca.Chop;
import ch.ethz.idsc.tensor.sca.Power;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/BinomialDistribution.html">BinomialDistribution</a> */
public class BinomialDistribution extends AbstractDiscreteDistribution implements VarianceInterface {
  /** Example:
   * PDF[BinomialDistribution[10, 1/3], 1] == 5120/59049
   * 
   * @param n non-negative
   * @param p in the interval [0, 1]
   * @return
   * @throws Exception */
  public static Distribution of(int n, Scalar p) {
    if (n < 0)
      throw new RuntimeException("n=" + n);
    if (Scalars.lessThan(p, RealScalar.ZERO) || Scalars.lessThan(RealScalar.ONE, p))
      throw TensorRuntimeException.of(p);
    // ---
    boolean revert = Scalars.lessThan(RationalScalar.of(1, 2), p);
    Scalar q = revert ? RealScalar.ONE.subtract(p) : p;
    Tensor table = Tensors.empty();
    Scalar prev = Power.of(RealScalar.ONE.subtract(q), n);
    table.append(prev);
    final Scalar pratio = q.divide(RealScalar.ONE.subtract(q));
    for (int k = 1; k <= n; ++k) {
      // ((1 - k + n) p) / (k - k p) == ((1 - k + n)/k) * (p/(1 - p))
      Scalar ratio = RationalScalar.of(n - k + 1, k).multiply(pratio);
      prev = prev.multiply(ratio);
      table.append(prev);
    }
    table = revert ? Reverse.of(table) : table;
    Scalar sum = Total.of(table).Get();
    if (Chop.isZeros(sum.subtract(RealScalar.ONE)))
      return new BinomialDistribution(n, p, table);
    // ---
    return new BinomialRandomVariate(n, p);
  }

  /** @param n non-negative integer
   * @param p in the interval [0, 1]
   * @return */
  public static Distribution of(Scalar n, Scalar p) {
    return of(Scalars.intValueExact(n), p);
  }

  // ---
  private final int n;
  private final Scalar p;
  private final Tensor table;

  private BinomialDistribution(int n, Scalar p, Tensor table) {
    this.n = n;
    this.p = p;
    this.table = table;
  }

  @Override // from MeanInterface
  public Scalar mean() {
    return RealScalar.of(n).multiply(p);
  }

  @Override // from VarianceInterface
  public Scalar variance() {
    return RealScalar.of(n).multiply(p).multiply(RealScalar.ONE.subtract(p));
  }

  @Override // from DiscreteDistribution
  public int lowerBound() {
    return 0;
  }

  @Override // from AbstractDiscreteDistribution
  protected Scalar protected_p_equals(int k) {
    if (n < k)
      return RealScalar.ZERO;
    return table.Get(k);
  }
}
