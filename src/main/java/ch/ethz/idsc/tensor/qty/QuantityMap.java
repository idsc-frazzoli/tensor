// code by jph
package ch.ethz.idsc.tensor.qty;

import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.sca.ScalarUnaryOperator;

// EXPERIMENTAL
public class QuantityMap implements ScalarUnaryOperator {
  /** @param unit
   * @return */
  public static QuantityMap of(Unit unit) {
    return new QuantityMap(unit);
  }

  // ---
  private final Unit unit;

  private QuantityMap(Unit unit) {
    this.unit = unit;
  }

  @Override
  public Scalar apply(Scalar scalar) {
    return Quantity.of(scalar, unit);
  }

  /** @param tensor
   * @return tensor with all scalars replaced with their sin */
  @SuppressWarnings("unchecked")
  public <T extends Tensor> T of(T tensor) {
    return (T) tensor.map(this);
  }
}
