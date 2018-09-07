// code by jph
package ch.ethz.idsc.tensor.qty;

import ch.ethz.idsc.tensor.Tensor;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/QuantityArray.html">QuantityArray</a> */
public enum QuantityTensor {
  ;
  /** @param tensor
   * @param unit
   * @return tensor with all scalars of given tensor equipped with given unit
   * @throws Exception if any entry of given tensor is an instance of {@link Quantity} */
  public static Tensor of(Tensor tensor, Unit unit) {
    return tensor.map(scalar -> Quantity.of(scalar, unit));
  }

  /** Example:
   * <pre>
   * QuantityTensor.of(Tensors.vector(2, 3, -1), "m*s^-1")
   * == {2[m*s^-1], 3[m*s^-1], -1[m*s^-1]}
   * </pre>
   * 
   * @param tensor
   * @param unit
   * @return */
  public static Tensor of(Tensor tensor, String unit) {
    return of(tensor, Unit.of(unit));
  }
}
