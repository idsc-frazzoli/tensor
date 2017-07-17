// code by jph
package ch.ethz.idsc.tensor.io.ext;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import ch.ethz.idsc.tensor.Scalars;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;

class WavefrontImpl implements Wavefront {
  private final Tensor vertices = Tensors.empty();
  private final Tensor normals = Tensors.empty();
  private final List<WavefrontObject> objects = new ArrayList<>();

  @Override
  public Tensor vertices() {
    return vertices.unmodifiable();
  }

  @Override
  public Tensor normals() {
    return normals.unmodifiable();
  }

  @Override
  public List<WavefrontObject> objects() {
    return objects;
  }

  void append_v(String string) {
    vertices.append(three(string));
  }

  void append_vn(String string) {
    normals.append(three(string));
  }

  public void append_o(String string) {
    objects.add(new WavefrontObjectImpl(string));
  }

  void append_f(String substring) {
    ((WavefrontObjectImpl) object()).append_f(substring);
  }

  private WavefrontObject object() {
    return objects.get(objects.size() - 1);
  }

  private static Tensor three(String string) {
    StringTokenizer stringTokenizer = new StringTokenizer(string);
    return Tensors.of( //
        Scalars.fromString(stringTokenizer.nextToken()), //
        Scalars.fromString(stringTokenizer.nextToken()), //
        Scalars.fromString(stringTokenizer.nextToken()));
  }
}
