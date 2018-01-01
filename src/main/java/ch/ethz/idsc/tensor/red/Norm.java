// code by jph
package ch.ethz.idsc.tensor.red;

import java.util.Optional;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.TensorRuntimeException;
import ch.ethz.idsc.tensor.alg.TensorRank;

/** The enumeration defines the 1-, 2-, and Infinity-norm for vectors, and matrices.
 * The return value is of type {@link RealScalar}.
 * 
 * <p>As in Mathematica, the norm of empty expressions is undefined:
 * Norm[{}] -> undefined
 * Norm[{{}}] -> undefined
 * 
 * <p>While Mathematica also defines the norm for Scalars, for instance Norm[-3 + 4 I] == 5,
 * the tensor library insists that Scalar::abs is used for that purpose.
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

  @Override // from VectorNormInterface
  public Scalar ofVector(Tensor vector) {
    return normInterface.ofVector(vector);
  }

  /** @param v1 vector
   * @param v2 vector
   * @return norm of vector difference || v1 - v2 || */
  public Scalar between(Tensor v1, Tensor v2) {
    return ofVector(v1.subtract(v2));
  }

  @Override // from NormInterface
  public Scalar ofMatrix(Tensor matrix) {
    return normInterface.ofMatrix(matrix);
  }

  /** Hint: Whenever the application layer is aware of the rank of the given tensor,
   * we recommend to invoke the norm computation directly via
   * <ul>
   * <li>Norm.X::ofVector
   * <li>Norm.X::ofMatrix
   * </ul>
   * 
   * <p>universal entry point to compute the norm of a tensor of rank 1 or 2
   * 
   * @param tensor is a vector or matrix
   * @return norm of given tensor */
  public Scalar of(Tensor tensor) {
    Optional<Integer> rank = TensorRank.ofArray(tensor);
    if (rank.isPresent())
      switch (rank.get()) {
      // Norm::of(Scalar) is not supported to prevent mistakes.
      // For scalars use Scalar::abs instead
      case 1:
        return normInterface.ofVector(tensor);
      case 2:
        return normInterface.ofMatrix(tensor);
      default:
      }
    throw TensorRuntimeException.of(tensor);
  }
}
