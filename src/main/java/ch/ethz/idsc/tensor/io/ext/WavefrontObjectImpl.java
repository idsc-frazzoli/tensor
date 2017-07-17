// code by jph
package ch.ethz.idsc.tensor.io.ext;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;

class WavefrontObjectImpl implements WavefrontObject {
  private final String string;
  private final Tensor faces = Tensors.empty();
  private final Tensor normals = Tensors.empty();

  public WavefrontObjectImpl(String string) {
    this.string = string;
  }

  @Override
  public String name() {
    return string;
  }

  @Override
  public Tensor faces() {
    return faces.unmodifiable();
  }

  @Override
  public Tensor normals() {
    return normals.unmodifiable();
  }

  void append_f(String line) {
    String[] nodes = line.split(" +");
    Tensor iv = Tensors.empty();
    Tensor in = Tensors.empty();
    for (int index = 0; index < nodes.length; ++index) {
      String[] node = nodes[index].split("/");
      iv.append(RealScalar.of(Integer.parseInt(node[0]) - 1));
      in.append(RealScalar.of(Integer.parseInt(node[2]) - 1));
    }
    faces.append(iv);
    normals.append(in);
  }
}
