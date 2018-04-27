// code by jph
package ch.ethz.idsc.tensor.alg;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.opt.TensorUnaryOperator;

/** Example:
 * <pre>
 * Mathematica::PadLeft[{1, 2, 3}, 6] == {0, 0, 0, 1, 2, 3}
 * Tensor::PadLeft.zeros(6).apply({1, 2, 3}) == {0, 0, 0, 1, 2, 3}
 * </pre>
 * 
 * <p>inspired by
 * <a href="https://reference.wolfram.com/language/ref/PadLeft.html">PadLeft</a> */
public class PadLeft implements TensorUnaryOperator {
  /** @param element
   * @param dimensions non-empty
   * @return */
  public static TensorUnaryOperator with(Tensor element, List<Integer> dimensions) {
    return new PadLeft(element, dimensions);
  }

  /** @param element
   * @param dimensions non-empty
   * @return */
  public static TensorUnaryOperator with(Tensor element, Integer... dimensions) {
    return with(element, Arrays.asList(dimensions));
  }

  /** @param dimensions non-empty
   * @return */
  public static TensorUnaryOperator zeros(List<Integer> dimensions) {
    return new PadLeft(RealScalar.ZERO, dimensions);
  }

  /** @param dimensions non-empty
   * @return */
  public static TensorUnaryOperator zeros(Integer... dimensions) {
    return zeros(Arrays.asList(dimensions));
  }

  // ---
  private final Tensor element;
  private final List<Integer> dimensions;

  private PadLeft(Tensor element, List<Integer> dimensions) {
    this.element = element;
    this.dimensions = dimensions;
  }

  @Override // from TensorUnaryOperator
  public Tensor apply(Tensor tensor) {
    final int dim0 = dimensions.get(0);
    if (1 < dimensions.size()) { // recur
      TensorUnaryOperator tensorUnaryOperator = with(element, dimensions.subList(1, dimensions.size()));
      if (dim0 <= tensor.length())
        return Tensor.of(tensor.stream().skip(tensor.length() - dim0).map(tensorUnaryOperator));
      List<Integer> copy = new ArrayList<>(dimensions);
      copy.set(0, dim0 - tensor.length());
      return Join.of( //
          Array.of(index -> element, copy), //
          Tensor.of(tensor.stream().map(tensorUnaryOperator)));
    }
    return dim0 <= tensor.length() //
        ? tensor.extract(tensor.length() - dim0, tensor.length())
        : Join.of(Array.of(index -> element, dim0 - tensor.length()), tensor);
  }
}
