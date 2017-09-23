// code by jph
package ch.ethz.idsc.tensor.qty;

import java.util.Objects;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.TensorRuntimeException;
import ch.ethz.idsc.tensor.sca.ScalarUnaryOperator;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/QuantityMagnitude.html">QuantityMagnitude</a> */
public class QuantityMagnitude {
  private static final QuantityMagnitude SI = new QuantityMagnitude(UnitSystem.SI());

  /** @return instance of QuantityMagnitude that uses the built-in SI convention */
  public static final QuantityMagnitude SI() {
    return SI;
  }

  private final UnitSystem unitSystem;

  public QuantityMagnitude(UnitSystem unitSystem) {
    if (Objects.isNull(unitSystem))
      throw new NullPointerException();
    this.unitSystem = unitSystem;
  }

  /** Example:
   * <pre>
   * QuantityMagnitude.SI().in(Unit.of("K*m^2")).apply(Quantity.of(2, "K*km^2"))
   * == RealScalar.of(2_000_000)
   * <pre>
   * 
   * @param unit
   * @return operator that maps a quantity to the equivalent scalar of given unit */
  public ScalarUnaryOperator in(Unit unit) {
    final Scalar base = unitSystem.apply(Quantity.of(RealScalar.ONE, unit));
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
