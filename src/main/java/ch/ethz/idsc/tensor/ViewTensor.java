// code by jph
package ch.ethz.idsc.tensor;

import java.util.List;

// EXPERIMENTAL
/* package */ class ViewTensor extends TensorImpl {
  static ViewTensor wrap(Tensor tensor) {
    TensorImpl impl = (TensorImpl) tensor;
    return wrap(impl.list);
  }

  private static ViewTensor wrap(List<Tensor> list) {
    return new ViewTensor(list);
  }

  private ViewTensor(List<Tensor> list) {
    super(list);
  }

  @Override
  public Tensor extract(int fromIndex, int toIndex) {
    return wrap(list.subList(fromIndex, toIndex));
  }

  @Override
  Tensor _block(List<Integer> fromIndex, List<Integer> dimensions) {
    int toIndex = fromIndex.get(0) + dimensions.get(0);
    List<Tensor> subList = list.subList(fromIndex.get(0), toIndex);
    if (fromIndex.size() == 1)
      return wrap(subList);
    int size = fromIndex.size();
    return Tensor.of(subList.stream() //
        .map(tensor -> wrap(tensor)._block(fromIndex.subList(1, size), dimensions.subList(1, size))));
  }
}
