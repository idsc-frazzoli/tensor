// code by jph
package ch.ethz.idsc.tensor.alg;

import java.util.stream.Stream;

import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.TensorRuntimeException;

/** ArrayReshape is used in {@link Transpose}
 * 
 * <p>inspired by
 * <a href="https://reference.wolfram.com/language/ref/ArrayReshape.html">ArrayReshape</a> */
public enum ArrayReshape {
  ;
  /** compliant with Mathematica's ArrayReshape
   * <code>
   * ArrayReshape[{a, b, c, d, e, f}, {2, 3}] == {{a, b, c}, {d, e, f}}
   * ArrayReshape[{a, b, c, d, e, f}, {2, 3, 1}] == {{{a}, {b}, {c}}, {{d}, {e}, {f}}}
   * </code>
   * 
   * implementation requires
   * <code>stream.count() == prod(size)</code>
   * 
   * @param stream of {@link Scalar}s
   * @param size
   * @return tensor with {@link Scalar} entries from stream and {@link Dimensions} size
   * @throws Exception if the product of the elements in dimensions
   * does not equal the number of tensors in the given stream */
  public static Tensor of(Stream<? extends Tensor> stream, Integer... size) {
    Tensor transpose = Tensor.of(stream);
    int length = transpose.length();
    int numel = 1;
    for (int index = size.length - 1; 0 < index; --index) {
      numel *= size[index];
      transpose = Partition.of(transpose, size[index]);
    }
    numel *= size[0];
    if (length != numel)
      throw TensorRuntimeException.of(transpose);
    return transpose;
  }

  /** @param tensor
   * @param dimensions
   * @return
   * @throws Exception if the product of the elements in dimensions
   * does not equal the number of scalars in given tensor */
  public static Tensor of(Tensor tensor, Integer... dimensions) {
    return of(tensor.flatten(-1), dimensions);
  }
}
