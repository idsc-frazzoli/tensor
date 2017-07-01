// code by jph
package ch.ethz.idsc.tensor.red;

import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.alg.Transpose;
import ch.ethz.idsc.tensor.mat.SingularValueDecomposition;
import ch.ethz.idsc.tensor.sca.Sqrt;

/* package */ class Norm2 extends RankAdapter<Scalar> {
  @Override
  public Scalar ofScalar(Scalar scalar) {
    return scalar.abs();
  }

  @Override
  public Scalar ofVector(Tensor vector) {
    // if (0 < vector.length()) {
    // Scalar first = vector.Get(0).abs();
    // if (first instanceof Comparable)
    // return Hypot.ofVector(vector);
    // }
    try {
      return Hypot.ofVector(vector);
    } catch (Exception e) {
      // ---
    }
    // Hypot prevents the incorrect evaluation: Norm_2[ {1e-300, 1e-300} ] == 0
    return Sqrt.of(Norm._2SQUARED.of(vector));
  }

  @Override
  public Scalar ofMatrix(Tensor matrix) {
    if (matrix.length() < matrix.get(0).length())
      matrix = Transpose.of(matrix);
    return SingularValueDecomposition.of(matrix) //
        .values().flatten(0) // values are non-negative
        .map(Scalar.class::cast) //
        .reduce(Max::of) //
        .get();
  }
}
