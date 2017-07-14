// code by jph
package ch.ethz.idsc.tensor.io;

import java.util.StringTokenizer;

import ch.ethz.idsc.tensor.Scalars;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;

class WavefrontObjImpl implements WavefrontObj {
  private final Tensor vertices = Tensors.empty();
  private final Tensor normals = Tensors.empty();

  @Override
  public Tensor vertices() {
    return vertices;
  }

  @Override
  public Tensor normals() {
    return normals;
  }

  void append_v(String string) {
    vertices.append(three(string));
  }

  void append_vn(String string) {
    normals.append(three(string));
  }

  private static Tensor three(String string) {
    StringTokenizer stringTokenizer = new StringTokenizer(string);
    return Tensors.of( //
        Scalars.fromString(stringTokenizer.nextToken()), //
        Scalars.fromString(stringTokenizer.nextToken()), //
        Scalars.fromString(stringTokenizer.nextToken()));
  }
}
