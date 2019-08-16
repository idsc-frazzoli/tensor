// code by jph
package ch.ethz.idsc.tensor.qty;

import java.io.Serializable;
import java.util.Set;

import ch.ethz.idsc.tensor.Scalar;

/** Predicate determines if unit is defined by a given unit system.
 * 
 * <p>A use case of {@link KnownUnitQ} is the validation of compatibility of user defined
 * scalar or tensor parameters with the unit system of an application.
 * 
 * <p>Use {@link QuantityUnit} to obtain {@link Unit} of a {@link Scalar}, and then
 * check containment of unit in a given {@link UnitSystem}.
 * 
 * <p>inspired by
 * <a href="https://reference.wolfram.com/language/ref/KnownUnitQ.html">KnownUnitQ</a> */
public class KnownUnitQ implements Serializable {
  private static final KnownUnitQ SI = in(UnitSystem.SI());

  /** @param unitSystem non-null
   * @return */
  public static KnownUnitQ in(UnitSystem unitSystem) {
    return new KnownUnitQ(unitSystem.units());
  }

  /** Examples:
   * <pre>
   * KnownUnitQ.SI().of(Unit.of("V*K*CD*kOhm^-2")) == true
   * KnownUnitQ.SI().of(Unit.of("CHF")) == false
   * </pre>
   * 
   * @return */
  public static KnownUnitQ SI() {
    return SI;
  }

  // ---
  private final Set<String> set;

  private KnownUnitQ(Set<String> set) {
    this.set = set;
  }

  /** @param unit
   * @return true if all atomic units of given unit are defined in unit system */
  public boolean of(Unit unit) {
    return set.containsAll(unit.map().keySet());
  }

  /** @param unit
   * @return unit
   * @throws Exception if given unit is not known to unit system */
  public Unit require(Unit unit) {
    if (of(unit))
      return unit;
    throw new IllegalArgumentException("" + unit);
  }
}
