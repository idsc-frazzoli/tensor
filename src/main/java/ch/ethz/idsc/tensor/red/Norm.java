// code by jph
package ch.ethz.idsc.tensor.red;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Tensor;

/** Each norm is defined at least for scalars, vectors, and matrices.
 * The return value is of type {@link RealScalar}.
 * 
 * inspired by
 * <a href="https://reference.wolfram.com/language/ref/Norm.html">Norm</a> */
public enum Norm {
  /** 1-norm, for vectors |a_1| + ... + |a_n| also known as ManhattanDistance */
  _1(new Norm1()), //
  /** 2-norm, uses SVD for matrices */
  _2(new Norm2()), //
  _2Squared(new Norm2Squared()), //
  Frobenius(new NormFrobenius()), //
  /** infinity-norm, for vectors max_i |a_i| */
  Infinity(new NormInfinity()), //
  ;
  // ---
  private final RankAdapter<RealScalar> rankAdapter;

  private Norm(RankAdapter<RealScalar> rankAdapter) {
    this.rankAdapter = rankAdapter;
  }

  /** entry point to compute the norm of given tensor
   * 
   * @param tensor is scalar, vector, or matrix
   * @return norm of given tensor */
  public RealScalar of(Tensor tensor) {
    return rankAdapter.of(tensor);
  }
}
