// code by jph
package ch.ethz.idsc.tensor.qty;

import java.io.Serializable;
import java.util.Objects;
import java.util.function.Predicate;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/CompatibleUnitQ.html">CompatibleUnitQ</a> */
public class CompatibleUnitQ implements Serializable {
  private static final CompatibleUnitQ SI = in(BuiltIn.SI.unitSystem);

  /** @param unitSystem non-null
   * @return */
  public static CompatibleUnitQ in(UnitSystem unitSystem) {
    return new CompatibleUnitQ(Objects.requireNonNull(unitSystem));
  }

  /** Examples:
   * <pre>
   * CompatibleUnitQ.SI().with(Unit.of("PS^2")).test(Quantity.of(2, "W^2")) == true
   * CompatibleUnitQ.SI().with(Unit.of("m*s^-1")).test(Quantity.of(2, "m*s")) == false
   * </pre>
   * 
   * @return */
  public static CompatibleUnitQ SI() {
    return SI;
  }

  // ---
  private final UnitSystem unitSystem;

  private CompatibleUnitQ(UnitSystem unitSystem) {
    this.unitSystem = unitSystem;
  }

  /** @param unit
   * @return */
  public Predicate<Scalar> with(Unit unit) {
    Scalar base = unitSystem.apply(QuantityImpl.of(RealScalar.ONE, unit));
    return scalar -> !(unitSystem.apply(scalar).divide(base) instanceof Quantity);
  }
}
