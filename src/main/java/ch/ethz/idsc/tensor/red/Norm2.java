// code by jph
package ch.ethz.idsc.tensor.red;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.ZeroScalar;
import ch.ethz.idsc.tensor.mat.SingularValueDecomposition;
import ch.ethz.idsc.tensor.sca.Sqrt;

class Norm2 extends RankAdapter<RealScalar> {
  @Override
  public RealScalar ofScalar(Scalar scalar) {
    return (RealScalar) scalar.abs();
  }

  @Override
  public RealScalar ofVector(Tensor vector) {
    // TODO use hypot if 2 doubles
    return (RealScalar) Sqrt.of(Norm._2Squared.of(vector));
  }

  @Override
  public RealScalar ofMatrix(Tensor matrix) {
    return SingularValueDecomposition.of(matrix) //
        .getW().flatten(0) //
        .map(RealScalar.class::cast) //
        .reduce(RealScalar::max) //
        .orElse(ZeroScalar.get());
  }
}
