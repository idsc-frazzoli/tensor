// code by jph
package ch.ethz.idsc.tensor.red;

import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.ZeroScalar;
import ch.ethz.idsc.tensor.mat.SingularValueDecomposition;
import ch.ethz.idsc.tensor.sca.Sqrt;

class Norm2 extends RankAdapter<Scalar> {
  @Override
  public Scalar ofScalar(Scalar scalar) {
    return scalar.abs();
  }

  @Override
  public Scalar ofVector(Tensor vector) {
    return (Scalar) Sqrt.of(Norm._2Squared.of(vector));
  }

  @Override
  public Scalar ofMatrix(Tensor matrix) {
    return SingularValueDecomposition.of(matrix) //
        .getW().flatten(0) //
        .map(Scalar.class::cast) //
        .reduce(Max::of) //
        .orElse(ZeroScalar.get());
  }
}
