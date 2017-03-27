// code by jph
package ch.ethz.idsc.tensor.red;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Tensor;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/Norm.html">Norm</a> */
public enum Norm {
  _1(new Norm1()), //
  _2(new Norm2()), //
  _2Squared(new Norm2Squared()), //
  Frobenius(new NormFrobenius()), //
  Infinity(new NormInfinity()), //
  ;
  // ---
  private final RankAdapter<RealScalar> rankAdapter;

  private Norm(RankAdapter<RealScalar> rankAdapter) {
    this.rankAdapter = rankAdapter;
  }

  public RealScalar of(Tensor tensor) {
    return rankAdapter.of(tensor);
  }
}
