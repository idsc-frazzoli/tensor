// code by jph
package ch.ethz.idsc.tensor.red;

import java.util.Objects;
import java.util.function.BinaryOperator;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.TensorRuntimeException;

/** Entrywise applies a BinaryOperator<Scalar> across multiple tensors.
 * The tensors are required to have the same dimensions/structure.
 * 
 * <p>Entrywise reproduces existing functionality:
 * <pre>
 * Entrywise.with(Scalar::add).of(a, b, c) == a.add(b).add(c)
 * Entrywise.with(Scalar::multiply).of(a, b, c) == a.pmul(b).pmul(c)
 * </pre>
 * 
 * <p>Typical examples that are pointwise maximum, or pointwise minimum. */
public class Entrywise implements BinaryOperator<Tensor> {
  private static final Entrywise MAX = Entrywise.with(Max::of);
  private static final Entrywise MIN = Entrywise.with(Min::of);

  /** @param binaryOperator
   * @return */
  public static Entrywise with(BinaryOperator<Scalar> binaryOperator) {
    return new Entrywise(binaryOperator);
  }

  /** @return entrywise maximum operator */
  public static Entrywise max() {
    return MAX;
  }

  /** @return entrywise minimum operator */
  public static Entrywise min() {
    return MIN;
  }
  // ---

  private final BinaryOperator<Scalar> binaryOperator;

  private Entrywise(BinaryOperator<Scalar> binaryOperator) {
    this.binaryOperator = Objects.requireNonNull(binaryOperator);
  }

  @Override // from BinaryOperator
  public Tensor apply(Tensor a, Tensor b) {
    if (a instanceof Scalar)
      return binaryOperator.apply(a.Get(), b.Get());
    // ---
    if (a.length() != b.length())
      throw TensorRuntimeException.of(a, b);
    return Tensor.of(IntStream.range(0, a.length()) //
        .mapToObj(index -> apply(a.get(index), b.get(index))));
  }

  public Tensor of(Tensor... tensors) {
    return 1 == tensors.length //
        ? tensors[0].copy()
        : Stream.of(tensors).reduce(this).get();
  }
}
