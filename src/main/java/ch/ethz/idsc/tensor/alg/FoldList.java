// code by jph
package ch.ethz.idsc.tensor.alg;

import java.util.function.BinaryOperator;

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
   * @param tensor
   * @return */
  public static Tensor of(BinaryOperator<Tensor> binaryOperator, Tensor tensor) {
    Tensor result = Tensors.empty();
    if (0 < tensor.length()) {
      Tensor entry = tensor.get(0);
      result.append(entry);
      for (int index = 1; index < tensor.length(); ++index) {
        entry = binaryOperator.apply(entry, tensor.get(index));
        result.append(entry);
      }
    }
    return result;
  }
}
