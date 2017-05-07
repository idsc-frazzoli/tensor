// code by jph
package ch.ethz.idsc.tensor.alg;

import java.util.List;

import ch.ethz.idsc.tensor.Tensor;

/** Careful: API _not_ consistent with Mathematica !
 * 
 * inspired by
 * <a href="https://reference.wolfram.com/language/ref/Extract.html">Extract</a> */
// EXPERIMENTAL
public enum Extract {
  ;
  /** @param tensor
   * @param list
   * @return */
  public static Tensor of(Tensor tensor, List<Integer> list) {
    return Tensor.of(list.stream().map(tensor::get));
  }

  // EXPERIMENTAL: this is Mathematica's own definition
  /* package */ static Tensor get(Tensor tensor, List<List<Integer>> list) {
    return Tensor.of(list.stream().map(tensor::get));
  }
}
