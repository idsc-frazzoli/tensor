// code by jph
package ch.ethz.idsc.tensor.qty;

import java.util.Objects;

import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.TensorRuntimeException;
import ch.ethz.idsc.tensor.sca.ScalarUnaryOperator;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/QuantityMagnitude.html">QuantityMagnitude</a> */
public class QuantityMagnitude {
  private static final QuantityMagnitude SI = new QuantityMagnitude(UnitSystem.SI());

  public static final QuantityMagnitude SI() {
    return SI;
  }

  private final UnitSystem unitSystem;

  public QuantityMagnitude(UnitSystem unitSystem) {
    if (Objects.isNull(unitSystem))
      throw new NullPointerException();
    this.unitSystem = unitSystem;
  }

  public ScalarUnaryOperator in(Unit unit) {
    final Scalar base = unitSystem.apply(Quantity.of(1, unit.toString()));
    return new ScalarUnaryOperator() {
      @Override
      public Scalar apply(Scalar scalar) {
        Scalar result = unitSystem.apply(scalar).divide(base);
        if (result instanceof Quantity)
          throw TensorRuntimeException.of(result);
        return result;
      }
    };
  }
}
