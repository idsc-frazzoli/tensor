// code by jph
package ch.ethz.idsc.tensor.red;

import java.util.Optional;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Scalars;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.TensorRuntimeException;
import ch.ethz.idsc.tensor.Unprotect;
import ch.ethz.idsc.tensor.alg.TensorRank;
import ch.ethz.idsc.tensor.alg.Transpose;
import ch.ethz.idsc.tensor.mat.SingularValueDecomposition;
import ch.ethz.idsc.tensor.sca.Abs;
import ch.ethz.idsc.tensor.sca.Power;
import ch.ethz.idsc.tensor.sca.Sqrt;

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
  _1() {
    @Override
    public Scalar vector(Tensor vector) {
      return vector.flatten(0) //
          .map(Scalar.class::cast) //
          .map(Scalar::abs) //
          .reduce(Scalar::add).get();
    }

    @Override
    public Scalar matrix(Tensor matrix) {
      return Total.of(Abs.of(matrix)).flatten(0) //
          .map(Scalar.class::cast) //
          .reduce(Max::of).get();
    }
  }, //
  /** 2-norm, uses SVD for matrices */
  _2() {
    @Override
    public Scalar vector(Tensor vector) {
      try {
        // Hypot prevents the incorrect evaluation: Norm_2[ {1e-300, 1e-300} ] == 0
        return Hypot.ofVector(vector);
      } catch (Exception exception) {
        // <- when vector is empty, or contains NaN
      }
      return Sqrt.FUNCTION.apply(Norm2Squared.vector(vector));
    }

    @Override
    public Scalar matrix(Tensor matrix) {
      if (matrix.length() < Unprotect.dimension1(matrix))
        matrix = Transpose.of(matrix);
      return SingularValueDecomposition.of(matrix) //
          .values().flatten(0) // values are non-negative
          .map(Scalar.class::cast) //
          .reduce(Max::of).get();
    }
  }, //
  // /** infinity-norm, for vectors max_i |a_i| */
  INFINITY() {
    @Override
    public Scalar vector(Tensor vector) {
      return vector.flatten(0) //
          .map(Scalar.class::cast) //
          .map(Scalar::abs) //
          .reduce(Max::of).get();
    }

    @Override
    public Scalar matrix(Tensor matrix) {
      return vector(Tensor.of(matrix.flatten(0).map(_1::vector)));
    }
  }, //
  ;
  // ---
  /** entry point to compute the norm of given tensor
   * 
   * @param tensor is scalar, vector, or matrix
   * @return norm of given tensor */
  public Scalar of(Tensor tensor) {
    Optional<Integer> rank = TensorRank.ofArray(tensor);
    if (rank.isPresent())
      switch (rank.get()) {
      case 1:
        return vector(tensor);
      case 2:
        return matrix(tensor);
      default:
      }
    throw TensorRuntimeException.of(tensor);
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
    if (Scalars.lessThan(p, RealScalar.ONE))
      throw TensorRuntimeException.of(p);
    return Power.of( //
        (Scalar) Total.of(vector.map(Scalar::abs).map(Power.function(p))), //
        p.reciprocal());
  }
}
