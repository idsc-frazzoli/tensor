// code by jph
package ch.ethz.idsc.tensor.red;

import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.ZeroScalar;
import ch.ethz.idsc.tensor.mat.SingularValueDecomposition;

/* package */ class Norm2 extends RankAdapter<Scalar> {
  @Override
  public Scalar ofScalar(Scalar scalar) {
    return scalar.abs();
  }

  @Override
  public Scalar ofVector(Tensor vector) {
    return Hypot.ofVector(vector);
    // rejected implementation is
    // return (Scalar) Sqrt.of(Norm._2Squared.of(vector));
    // which evaluates incorrectly: Norm_2[ {1e-300, 1e-300} ] == 0
  }

  @Override
  public Scalar ofMatrix(Tensor matrix) {
    return SingularValueDecomposition.of(matrix) //
        .getW().flatten(0) // values are non-negative
        .map(Scalar.class::cast) //
        .reduce(Max::of) //
        .orElse(ZeroScalar.get());
  }
}
