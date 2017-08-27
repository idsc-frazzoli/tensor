// code by jph
package ch.ethz.idsc.tensor.red;

import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;

interface NormInterface {
  Scalar vector(Tensor vector);

  Scalar matrix(Tensor matrix);
}
