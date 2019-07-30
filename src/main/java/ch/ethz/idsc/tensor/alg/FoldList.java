// code by jph
package ch.ethz.idsc.tensor.alg;

import java.util.function.BinaryOperator;

import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.ScalarQ;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/FoldList.html">FoldList</a> */
public enum FoldList {
  ;
  /** <pre>
   * FoldList[f, {a, b, c, ...}] gives {a, f[a, b], f[f[a, b], c], ...}
   * </pre>
   * 
   * @param binaryOperator
   * @param tensor must not be a {@link Scalar}
   * @return see description above */
  public static Tensor of(BinaryOperator<Tensor> binaryOperator, Tensor tensor) {
    int length = tensor.length();
    Tensor result = Tensors.reserve(length);
    if (0 < length) {
      Tensor entry = tensor.get(0);
      result.append(entry);
      for (int index = 1; index < length; ++index)
        result.append(entry = binaryOperator.apply(entry, tensor.get(index)));
    }
    return result;
  }

  /** <pre>
   * FoldList[f, x, {a, b, ...}] gives {x, f[x, a], f[f[x, a], b], ...}
   * </pre>
   * 
   * @param binaryOperator
   * @param x
   * @param tensor
   * @return */
  public static Tensor of(BinaryOperator<Tensor> binaryOperator, Tensor x, Tensor tensor) {
    ScalarQ.thenThrow(tensor);
    int length = tensor.length();
    Tensor result = Tensors.reserve(length + 1).append(x);
    for (int index = 0; index < length; ++index)
      result.append(x = binaryOperator.apply(x, tensor.get(index)));
    return result;
  }
}
