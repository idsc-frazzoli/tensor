// code by jph
package ch.ethz.idsc.tensor.pdf;

import java.util.LinkedHashMap;
import java.util.Map;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.alg.Last;
import ch.ethz.idsc.tensor.sca.Exp;

/** consistent with Mathematica::PoissonDistribution
 * 
 * <p>inspired by
 * <a href="https://reference.wolfram.com/language/ref/PoissonDistribution.html">PoissonDistribution</a> */
public class PoissonDistribution implements DiscreteDistribution {
  /** Example:
   * PDF[PoissonDistribution[Lambda], 2] == 1/6 Exp[-Lambda] Lambda^3
   * 
   * @param lambda
   * @return */
  public static DiscreteDistribution of(Scalar lambda) {
    if (!MEMO.containsKey(lambda))
      MEMO.put(lambda, new PoissonDistribution(lambda));
    return MEMO.get(lambda);
  }

  /***************************************************/
  private static final int PRECOMPUTE_LENGTH = 16;
  private static final int MEMO_SIZE = 40;
  /* package for testing */ static final Map<Scalar, PoissonDistribution> MEMO = //
      new LinkedHashMap<Scalar, PoissonDistribution>(MEMO_SIZE * 4 / 3, 0.75f, true) {
        @Override
        protected boolean removeEldestEntry(Map.Entry<Scalar, PoissonDistribution> eldest) {
          return size() > MEMO_SIZE;
        }
      };
  /***************************************************/
  private final Scalar lambda;
  private final Tensor values = Tensors.empty();

  private PoissonDistribution(Scalar lambda) {
    this.lambda = lambda;
    values.append(Exp.of(lambda.negate()));
    p_equals(PRECOMPUTE_LENGTH - 1);
  }

  @Override // from DiscreteDistribution
  public int lowerBound() {
    return 0;
  }

  @Override // from DiscreteDistribution
  public Scalar p_equals(int n) {
    if (n < 0)
      return RealScalar.ZERO;
    while (values.length() <= n) {
      Scalar factor = lambda.divide(RealScalar.of(values.length()));
      values.append(Last.of(values).multiply(factor));
    }
    return values.Get(n);
  }
}
