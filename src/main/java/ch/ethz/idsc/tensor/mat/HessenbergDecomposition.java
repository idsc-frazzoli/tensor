// code by jph
package ch.ethz.idsc.tensor.mat;

import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.TensorRuntimeException;

public enum HessenbergDecomposition {
  ;
  public static Tensor of(Tensor matrix) {
    throw TensorRuntimeException.of(matrix);
  }
}
