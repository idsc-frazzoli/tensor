// code by jph
package ch.ethz.idsc.tensor.io.ext;

import java.io.Serializable;

import ch.ethz.idsc.tensor.RationalScalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;

/* package */ class WavefrontObjectImpl implements WavefrontObject, Serializable {
  private final String string;
  private final Tensor faces = Tensors.empty();
  private final Tensor normals = Tensors.empty();

  public WavefrontObjectImpl(String string) {
    this.string = string;
  }

  @Override // from WavefrontObject
  public String name() {
    return string;
  }

  @Override // from WavefrontObject
  public Tensor faces() {
    return faces.unmodifiable();
  }

  @Override // from WavefrontObject
  public Tensor normals() {
    return normals.unmodifiable();
  }

  void append_f(String line) {
    String[] nodes = line.split(" +");
    Tensor iv = Tensors.empty();
    Tensor in = Tensors.empty();
    for (int index = 0; index < nodes.length; ++index) {
      String[] node = nodes[index].split("/");
      iv.append(RationalScalar.of(Integer.parseInt(node[0]) - 1, 1));
      in.append(RationalScalar.of(Integer.parseInt(node[2]) - 1, 1));
    }
    faces.append(iv);
    normals.append(in);
  }
}
