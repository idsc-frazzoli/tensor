// code by jph
package ch.ethz.idsc.tensor.red;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.sca.Power;

/** Each norm is defined at least for scalars, vectors, and matrices.
 * The return value is of type {@link RealScalar}.
 * 
 * <p>As in Mathematica, the norm of empty expressions is undefined:
 * Norm[{{}}] -> undefined
 * 
 * <p>inspired by
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
  private final RankAdapter<Scalar> rankAdapter;

  private Norm(RankAdapter<Scalar> rankAdapter) {
    this.rankAdapter = rankAdapter;
  }

  /** entry point to compute the norm of given tensor
   * 
   * @param tensor is scalar, vector, or matrix
   * @return norm of given tensor */
  public Scalar of(Tensor tensor) {
    return rankAdapter.of(tensor);
  }

  /** @param vector
   * @param p
   * @return p-norm of vector */
  public static Scalar ofVector(Tensor vector, Number p) {
    return ofVector(vector, RealScalar.of(p));
  }

  /** @param vector
   * @param p
   * @return p-norm of vector */
  public static Scalar ofVector(Tensor vector, Scalar p) {
    return Power.of( //
        (Scalar) Total.of(vector.map(Scalar::abs).map(Power.function(p))), //
        p.invert());
  }
}
