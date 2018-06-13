// code by jph
package ch.ethz.idsc.tensor.qty;

import java.util.Objects;
import java.util.Properties;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.TensorRuntimeException;
import ch.ethz.idsc.tensor.sca.ScalarUnaryOperator;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/QuantityMagnitude.html">QuantityMagnitude</a> */
public class QuantityMagnitude {
  private static final QuantityMagnitude EMPTY = new QuantityMagnitude(SimpleUnitSystem.from(new Properties()));

  /** @return instance of QuantityMagnitude that uses the built-in SI convention */
  public static QuantityMagnitude SI() {
    return BuiltIn.SI.quantityMagnitude;
  }

  /** @param unit
   * @return operator that extracts the value from a Quantity of given unit */
  public static ScalarUnaryOperator singleton(Unit unit) {
    return EMPTY.in(unit);
  }

  /** @param string
   * @return operator that extracts the value from a Quantity of unit specified by given string */
  public static ScalarUnaryOperator singleton(String string) {
    return singleton(Unit.of(string));
  }

  // ---
  private final UnitSystem unitSystem;

  /** creates instance for quantity conversion and magnitude extraction
   * that is backed by given unitSystem
   * 
   * @param unitSystem
   * @throws Exception if given {@link UnitSystem} is null */
  public QuantityMagnitude(UnitSystem unitSystem) {
    this.unitSystem = Objects.requireNonNull(unitSystem);
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

  /** @param string
   * @return
   * @see #in(Unit) */
  public ScalarUnaryOperator in(String string) {
    return in(Unit.of(string));
  }
}
