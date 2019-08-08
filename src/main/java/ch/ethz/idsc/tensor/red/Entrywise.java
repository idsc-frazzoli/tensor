// code by jph
package ch.ethz.idsc.tensor.red;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.function.BinaryOperator;

import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.TensorRuntimeException;
import ch.ethz.idsc.tensor.Unprotect;

/** Entrywise applies a BinaryOperator<Scalar> across multiple tensors.
 * The tensors are required to have the same dimensions/structure.
 * 
 * <p>Example:
 * <pre>
 * Tensor box = {{0, 7}, {0, 8}, {1, 5}, {2, 7}};
 * box.stream().reduce(Entrywise.max()).get() == {2, 8}
 * box.stream().reduce(Entrywise.min()).get() == {0, 5}
 * </pre>
 * 
 * <p>Entrywise reproduces existing functionality:
 * <pre>
 * Entrywise.with(Scalar::add).of(a, b, c) == a.add(b).add(c)
 * Entrywise.with(Scalar::multiply).of(a, b, c) == a.pmul(b).pmul(c)
 * </pre> */
public class Entrywise implements BinaryOperator<Tensor>, Serializable {
  /** @param binaryOperator non-null
   * @return
   * @throws Exception if given binaryOperator is null */
  public static Entrywise with(BinaryOperator<Scalar> binaryOperator) {
    return new Entrywise(Objects.requireNonNull(binaryOperator));
  }

  private static final Entrywise MIN = with(Min::of);
  private static final Entrywise MAX = with(Max::of);

  /** @return entrywise minimum operator */
  public static Entrywise min() {
    return MIN;
  }

  /** @return entrywise maximum operator */
  public static Entrywise max() {
    return MAX;
  }

  // ---
  private final BinaryOperator<Scalar> binaryOperator;

  private Entrywise(BinaryOperator<Scalar> binaryOperator) {
    this.binaryOperator = binaryOperator;
  }

  @Override // from BinaryOperator
  public Tensor apply(Tensor a, Tensor b) {
    if (a instanceof Scalar)
      return binaryOperator.apply(a.Get(), b.Get());
    // ---
    if (a.length() != b.length())
      throw TensorRuntimeException.of(a, b);
    Iterator<Tensor> ia = a.iterator();
    Iterator<Tensor> ib = b.iterator();
    List<Tensor> list = new ArrayList<>(a.length());
    while (ia.hasNext())
      list.add(apply(ia.next(), ib.next()));
    return Unprotect.using(list);
  }

  /** Example:
   * <pre>
   * Entrywise.with(Min::of).of({{1, 2, 3}, {5, 0, 4}}) == {1, 0, 3}
   * Entrywise.with(Max::of).of({{1, 2, 3}, {5, 0, 4}}) == {5, 2, 4}
   * </pre>
   * 
   * @param tensor
   * @return
   * @throws Exception */
  public Tensor of(Tensor tensor) {
    return tensor.stream().reduce(this).get();
  }
}
