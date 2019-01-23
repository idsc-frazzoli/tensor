// code by jph
package ch.ethz.idsc.tensor.qty;

import java.util.Objects;
import java.util.function.Predicate;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/CompatibleUnitQ.html">CompatibleUnitQ</a> */
public class CompatibleUnitQ {
  public static CompatibleUnitQ SI() {
    return BuiltIn.SI.compatibleUnitQ;
  }

  public static CompatibleUnitQ in(UnitSystem unitSystem) {
    return new CompatibleUnitQ(Objects.requireNonNull(unitSystem));
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
