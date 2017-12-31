// code by jph
package ch.ethz.idsc.tensor;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

/** the content of the UnmodifiableTensor is read-only.
 * An attempt to modify the content results in an exception.
 * 
 * <p>UnmodifiableTensor wraps the original content list as a
 * {@link Collections#unmodifiableList(List)}.
 * 
 * <p>All methods in the default implementation {@link TensorImpl} that
 * 1) give references of sub-tensors, or that
 * 2) may result in the modification of the content
 * are overloaded.
 * 
 * <p>UnmodifiableTensor does not duplicate memory.
 * 
 * <p>The copy {@link UnmodifiableTensor#copy()} is modifiable. */
/* package */ class UnmodifiableTensor extends TensorImpl {
  UnmodifiableTensor(List<Tensor> list) {
    super(Collections.unmodifiableList(list));
  }

  @Override // from TensorImpl
  public Tensor unmodifiable() {
    return this;
  }

  @Override // from TensorImpl
  void _set(Tensor tensor, List<Integer> index) {
    throw new UnsupportedOperationException("unmodifiable");
  }

  @Override // from TensorImpl
  <T extends Tensor> void _set(Function<T, ? extends Tensor> function, List<Integer> index) {
    throw new UnsupportedOperationException("unmodifiable");
  }

  @Override // from TensorImpl
  public Stream<Tensor> stream() {
    return list.stream().map(Tensor::unmodifiable);
  }

  @Override // from TensorImpl
  public Iterator<Tensor> iterator() {
    return new Iterator<Tensor>() {
      int index = 0;

      @Override
      public boolean hasNext() {
        return index < list.size();
      }

      @Override
      public Tensor next() {
        return list.get(index++).unmodifiable();
      }
    };
  }
}
