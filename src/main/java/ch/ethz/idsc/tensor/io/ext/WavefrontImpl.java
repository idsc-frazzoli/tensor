// code by jph
package ch.ethz.idsc.tensor.io.ext;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;

/* package */ class WavefrontImpl implements Wavefront, Serializable {
  private final Tensor vertices = Tensors.empty();
  private final Tensor normals = Tensors.empty();
  private final List<WavefrontObject> objects = new ArrayList<>();

  public WavefrontImpl(Stream<String> stream) {
    stream.forEach(this::parse);
  }

  private void parse(String string) {
    if (string.startsWith("v "))
      vertices.append(StaticHelper.three(string.substring(2)));
    else //
    if (string.startsWith("vn "))
      normals.append(StaticHelper.three(string.substring(3)));
    else //
    if (string.startsWith("f "))
      ((WavefrontObjectImpl) object()).append_f(string.substring(2));
    else //
    if (string.startsWith("o "))
      objects.add(new WavefrontObjectImpl(string.substring(2)));
  }

  @Override // from Wavefront
  public Tensor vertices() {
    return vertices.unmodifiable();
  }

  @Override // from Wavefront
  public Tensor normals() {
    return normals.unmodifiable();
  }

  @Override // from Wavefront
  public List<WavefrontObject> objects() {
    return objects;
  }

  private WavefrontObject object() {
    if (objects.isEmpty())
      throw new RuntimeException();
    return objects.get(objects.size() - 1);
  }
}
