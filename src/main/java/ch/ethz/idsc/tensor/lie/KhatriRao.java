// code by jph
package ch.ethz.idsc.tensor.lie;

import ch.ethz.idsc.tensor.Tensor;

/** Khatri-Rao matrix product
 * 
 * https://en.wikipedia.org/wiki/Kronecker_product#Khatri%E2%80%93Rao_product */
public enum KhatriRao {
  ;
  /** @param a
   * @param b
   * @return Khatri-Rao product */
  public static Tensor of(Tensor a, Tensor b) {
    return Tensor.of(a.stream().flatMap(row -> b.stream().map(row::pmul)));
  }
}
