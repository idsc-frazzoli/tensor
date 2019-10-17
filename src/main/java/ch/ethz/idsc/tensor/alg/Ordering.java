// code by jph
package ch.ethz.idsc.tensor.alg;

import java.util.stream.IntStream;
import java.util.stream.Stream;

import ch.ethz.idsc.tensor.ScalarQ;
import ch.ethz.idsc.tensor.Scalars;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.lie.Signature;
import ch.ethz.idsc.tensor.mat.Eigensystem;

/** an application of Ordering is to arrange the eigenvalues in {@link Eigensystem}
 * in descending order.
 * 
 * <p>inspired by
 * <a href="https://reference.wolfram.com/language/ref/Ordering.html">Ordering</a>
 * 
 * @see Signature */
public enum Ordering {
  INCREASING(vector -> IntStream.range(0, vector.length()) //
      .boxed().sorted((i, j) -> Scalars.compare(vector.Get(i), vector.Get(j)))), //
  DECREASING(vector -> IntStream.range(0, vector.length()) //
      .boxed().sorted((i, j) -> Scalars.compare(vector.Get(j), vector.Get(i)))), //
  ;
  // ---
  private static interface OrderingInterface {
    /** @param vector
     * @return stream of indices i[:] so that vector[i[0]], vector[i[1]], ... is ordered */
    Stream<Integer> stream(Tensor vector);
  }

  // ---
  private final OrderingInterface orderingInterface;

  private Ordering(OrderingInterface orderingInterface) {
    this.orderingInterface = orderingInterface;
  }

  /** @param vector
   * @return stream of indices i[:] so that vector[i[0]], vector[i[1]], ... is ordered
   * @throws Exception if given vector cannot be sorted */
  public Stream<Integer> stream(Tensor vector) {
    ScalarQ.thenThrow(vector);
    return orderingInterface.stream(vector);
  }

  /** @param vector
   * @return array of indices i[:] so that vector[i[0]], vector[i[1]], ... is ordered
   * @throws Exception if given vector cannot be sorted */
  public Integer[] of(Tensor vector) {
    return stream(vector).toArray(Integer[]::new);
  }
}
