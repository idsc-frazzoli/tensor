// code by jph
package ch.ethz.idsc.tensor.alg;

import java.util.stream.Stream;

import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/ArrayReshape.html">ArrayReshape</a> */
public enum ArrayReshape {
  ;
  /** Mathematica's ArrayReshape[{a, b, c, d, e, f}, {2, 3}] == {{a, b, c}, {d, e, f}}
   * 
   * @param stream of {@link Scalar}s
   * @param size
   * @return tensor with {@link Scalar} entries from stream and {@link Dimensions} size */
  public static Tensor of(Stream<? extends Tensor> stream, int... size) { // TODO make Integer...
    // TODO check if count != prod size
    Tensor transpose = Tensor.of(stream);
    for (int index = size.length - 1; 0 < index; --index)
      transpose = Partition.of(transpose, size[index]);
    return transpose;
  }

  public static Tensor of(Tensor tensor, int... size) {
    return of(tensor.flatten(-1), size);
  }
}
