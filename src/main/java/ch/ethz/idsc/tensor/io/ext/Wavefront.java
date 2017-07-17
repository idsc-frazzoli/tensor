// code by jph
package ch.ethz.idsc.tensor.io.ext;

import java.io.Serializable;
import java.util.List;

import ch.ethz.idsc.tensor.Tensor;

public interface Wavefront extends Serializable {
  /** @return |V| x 3 matrix of vertices */
  Tensor vertices();

  /** @return |N| x 3 matrix of normals */
  Tensor normals();

  /** @return list of WavefrontObj */
  List<WavefrontObject> objects();
}
