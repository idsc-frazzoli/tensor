// code by jph
package ch.ethz.idsc.tensor.qty;

import ch.ethz.idsc.tensor.Tensor;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/QuantityArray.html">QuantityArray</a> */
public enum QuantityTensor {
  ;
  /** @param unit
   * @return */
  public static Tensor of(Tensor tensor, Unit unit) {
    return tensor.map(scalar -> Quantity.of(scalar, unit));
  }

  /** Example:
   * QuantityTensor.of(Tensors.vector(2, 3, -1), "m*s^-1")
   * 
   * @param unit
   * @return */
  public static Tensor of(Tensor tensor, String unit) {
    return of(tensor, Unit.of(unit));
  }
}
