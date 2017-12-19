// code by jph
package ch.ethz.idsc.tensor;

import java.util.List;

import ch.ethz.idsc.tensor.alg.ListCorrelate;
import ch.ethz.idsc.tensor.opt.LinearInterpolation;

/** ViewTensor overrides the methods {@link #extract(int, int)} and {@link #block(List, List)}.
 * The implementation returns the content provided by these methods via reference.
 * Since this access exposes the content of this ViewTensor for modification from the outside
 * the functionality is used only in very special and controlled applications.
 * 
 * A ViewTensor is created using {@link Unprotect#referencesNonScalar(Tensor)}.
 * Within the tensor library, ViewTensor is used to speed up the computations in
 * {@link LinearInterpolation}, and {@link ListCorrelate}. */
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

  @Override // from TensorImpl
  public Tensor extract(int fromIndex, int toIndex) {
    return wrap(list.subList(fromIndex, toIndex));
  }

  @Override // from TensorImpl
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
