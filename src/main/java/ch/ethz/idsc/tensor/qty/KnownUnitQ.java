// code by jph
package ch.ethz.idsc.tensor.qty;

import java.io.Serializable;
import java.util.Set;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/KnownUnitQ.html">KnownUnitQ</a> */
public class KnownUnitQ implements Serializable {
  private static final KnownUnitQ SI = in(BuiltIn.SI.unitSystem);

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
}
