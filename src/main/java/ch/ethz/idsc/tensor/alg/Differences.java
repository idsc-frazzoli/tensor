// code by jph
package ch.ethz.idsc.tensor.alg;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.Unprotect;

/** simplified version of Mathematica::Differences
 * 
 * <p>The implementation is consistent for the special cases
 * <pre>
 * Differences[ {} ] == {}
 * Differences[ {single} ] == {}
 * </pre>
 * 
 * <p>inspired by
 * <a href="https://reference.wolfram.com/language/ref/Differences.html">Differences</a> */
public enum Differences {
  ;
  /** <pre>
   * Differences[{a, b, c, d, e}] == {b - a, c - b, d - c, e - d}
   * </pre>
   * 
   * @param tensor
   * @return the successive differences of elements in tensor */
  public static Tensor of(Tensor tensor) {
    int length = tensor.length();
    if (length <= 1)
      return Tensors.empty();
    List<Tensor> list = new ArrayList<>(length - 1);
    Iterator<Tensor> iterator = tensor.iterator();
    Tensor prev = iterator.next();
    for (int index = 1; index < length; ++index) {
      Tensor next = iterator.next();
      list.add(next.subtract(prev));
      prev = next;
    }
    return Unprotect.using(list);
  }
}
