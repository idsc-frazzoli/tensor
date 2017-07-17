// code by jph
package ch.ethz.idsc.tensor.io.ext;

import java.io.Serializable;

import ch.ethz.idsc.tensor.Tensor;

public interface WavefrontObject extends Serializable {
  /** @return name of object */
  String name();

  /** @return vectors with indices to vertices for each polygon */
  Tensor faces();

  /** @return vectors with indices to normals for each polygon */
  Tensor normals();
}
