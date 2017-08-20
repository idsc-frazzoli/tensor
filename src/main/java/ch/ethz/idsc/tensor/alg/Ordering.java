// code by jph
package ch.ethz.idsc.tensor.alg;

import java.util.stream.IntStream;
import java.util.stream.Stream;

import ch.ethz.idsc.tensor.Scalars;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.mat.Eigensystem;

/** an application of Ordering is to arrange the eigenvalues in {@link Eigensystem}
 * in descending order.
 * 
 * <p>inspired by
 * <a href="https://reference.wolfram.com/language/ref/Ordering.html">Ordering</a> */
public enum Ordering {
  INCREASING(tensor -> IntStream.range(0, tensor.length()) //
      .boxed().sorted((i, j) -> Scalars.compare(tensor.Get(i), tensor.Get(j)))), //
  DECREASING(tensor -> IntStream.range(0, tensor.length()) //
      .boxed().sorted((i, j) -> Scalars.compare(tensor.Get(j), tensor.Get(i)))), //
  ;
  // ---
  private final OrderingInterface orderingInterface;

  private Ordering(OrderingInterface orderingInterface) {
    this.orderingInterface = orderingInterface;
  }

  /** @param vector
   * @return array of indices i[:] so that vector[i[0]], vector[i[1]], ... is ordered */
  public int[] of(Tensor vector) {
    return orderingInterface.stream(vector).mapToInt(Integer::intValue).toArray();
  }
}

/* private */ interface OrderingInterface {
  /** @param tensor
   * @return stream of indices so that tensor[i0], tensor[i1], ... is ascending */
  Stream<Integer> stream(Tensor tensor);
}
