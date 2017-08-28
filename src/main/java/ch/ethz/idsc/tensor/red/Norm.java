// code by jph
package ch.ethz.idsc.tensor.red;

import java.util.Optional;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.TensorRuntimeException;
import ch.ethz.idsc.tensor.alg.TensorRank;

/** Each norm is defined at least for scalars, vectors, and matrices.
 * The return value is of type {@link RealScalar}.
 * 
 * <p>As in Mathematica, the norm of empty expressions is undefined:
 * Norm[{}] -> undefined
 * Norm[{{}}] -> undefined
 * 
 * @see Frobenius#NORM
 * 
 * <p>inspired by
 * <a href="https://reference.wolfram.com/language/ref/Norm.html">Norm</a> */
public enum Norm implements NormInterface {
  /** 1-norm, for vectors |a_1| + ... + |a_n| also known as ManhattanDistance */
  _1(Norm1.INSTANCE), //
  /** 2-norm, uses SVD for matrices */
  _2(Norm2.INSTANCE), //
  /** infinity-norm, for vectors max_i |a_i| */
  INFINITY(NormInfinity.INSTANCE), //
  ;
  private final NormInterface normInterface;

  private Norm(NormInterface normInterface) {
    this.normInterface = normInterface;
  }

  @Override
  public Scalar ofVector(Tensor vector) {
    return normInterface.ofVector(vector);
  }

  @Override
  public Scalar ofMatrix(Tensor matrix) {
    return normInterface.ofMatrix(matrix);
  }

  /** entry point to compute the norm of given tensor
   * 
   * @param tensor is scalar, vector, or matrix
   * @return norm of given tensor */
  public Scalar of(Tensor tensor) {
    Optional<Integer> rank = TensorRank.ofArray(tensor);
    if (rank.isPresent())
      switch (rank.get()) {
      case 1:
        return ofVector(tensor);
      case 2:
        return ofMatrix(tensor);
      default:
      }
    throw TensorRuntimeException.of(tensor);
  }
}
