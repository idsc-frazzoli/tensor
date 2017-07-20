// code by jph
package ch.ethz.idsc.tensor.mat;

import java.util.stream.IntStream;

import ch.ethz.idsc.tensor.MachineNumberQ;
import ch.ethz.idsc.tensor.Scalars;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.alg.Join;
import ch.ethz.idsc.tensor.alg.Transpose;

/** <p>Quote from Wikipedia:
 * For matrices whose entries are floating-point numbers, the problem of computing the kernel
 * makes sense only for matrices such that the number of rows is equal to their rank:
 * because of the rounding errors, a floating-point matrix has almost always a full rank,
 * even when it is an approximation of a matrix of a much smaller rank. Even for a full-rank
 * matrix, it is possible to compute its kernel only if it is well conditioned, i.e. it has a
 * low condition number.
 * 
 * <p>for matrices in exact precision use {@link NullSpace#usingRowReduce(Tensor)}
 * 
 * for matrices in numeric precision use {@link NullSpace#usingSvd(Tensor)}
 * 
 * {@link NullSpace#of(Tensor)} automatically switches between these two cases.
 * 
 * <p>inspired by
 * <a href="https://reference.wolfram.com/language/ref/NullSpace.html">NullSpace</a> */
public enum NullSpace {
  ;
  /** if matrix has any entry in machine precision, i.e. {@link MachineNumberQ} returns true,
   * the nullspace is computed using {@link SingularValueDecomposition}.
   * In that case the vectors in the return value are normalized.
   * 
   * If all entries of the given matrix are in exact precision,
   * the nullspace is computed using {@link RowReduce}.
   * In that case the vectors in the return value are <em>not</em> normalized.
   * 
   * Function is consistent with Mathematica.
   * 
   * @param matrix
   * @return vectors that span the nullspace */
  public static Tensor of(Tensor matrix) {
    return StaticHelper.anyMachineNumberQ(matrix) ? usingSvd(matrix) : usingRowReduce(matrix);
  }

  /** @param matrix with exact precision entries
   * @return tensor of vectors that span the kernel of given matrix */
  public static Tensor usingRowReduce(Tensor matrix) {
    return usingRowReduce(matrix, IdentityMatrix.of(matrix.get(0).length()));
  }

  /** @param matrix with exact precision entries
   * @param identity contains the unit vector for the scalar type of each column
   * @return tensor of vectors that span the kernel of given matrix */
  public static Tensor usingRowReduce(Tensor matrix, Tensor identity) {
    final int n = matrix.length();
    final int m = matrix.get(0).length();
    Tensor lhs = RowReduce.of(Join.of(1, Transpose.of(matrix), identity));
    int j = 0;
    int c0 = 0;
    while (c0 < n)
      if (Scalars.nonZero(lhs.Get(j, c0++))) // <- careful: c0 is modified
        ++j;
    return Tensor.of(lhs.extract(j, m).flatten(0).map(row -> row.extract(n, n + m)));
  }

  /** @param matrix
   * @return (cols - rank()) x cols matrix */
  public static Tensor usingSvd(Tensor matrix) {
    return of(SingularValueDecomposition.of(matrix));
  }

  /** @param svd
   * @return */
  public static Tensor of(SingularValueDecomposition svd) {
    double w_threshold = svd.getThreshold();
    Tensor vt = Transpose.of(svd.getV());
    return Tensor.of(IntStream.range(0, svd.values().length()).boxed() //
        .filter(index -> svd.values().Get(index).abs().number().doubleValue() < w_threshold) //
        .map(vt::get));
  }
}
