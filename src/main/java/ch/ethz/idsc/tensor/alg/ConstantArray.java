// code by jph
package ch.ethz.idsc.tensor.alg;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Unprotect;

/** MATLAB::repmat
 * 
 * <p>inspired by
 * <a href="https://reference.wolfram.com/language/ref/ConstantArray.html">ConstantArray</a> */
public enum ConstantArray {
  ;
  /** @param entry non-null
   * @param dimensions
   * @return unmodifiable tensor with given dimensions and entries as given entry */
  public static Tensor of(Tensor entry, List<Integer> dimensions) {
    Tensor tensor = entry.copy();
    for (int index = dimensions.size() - 1; 0 <= index; --index)
      tensor = Unprotect.using(Collections.nCopies(dimensions.get(index), tensor));
    return tensor.unmodifiable();
  }

  /** @param entry non-null
   * @param dimensions
   * @return unmodifiable tensor with given dimensions and entries as given entry */
  public static Tensor of(Tensor entry, Integer... dimensions) {
    return of(entry, Arrays.asList(dimensions));
  }
}
