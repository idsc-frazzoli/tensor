// code by jph
package ch.ethz.idsc.tensor.qty;

import java.io.Serializable;
import java.util.Set;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/KnownUnitQ.html">KnownUnitQ</a> */
public class KnownUnitQ implements Serializable {
  public static KnownUnitQ SI() {
    return BuiltIn.SI.knownUnitQ;
  }

  public static KnownUnitQ in(UnitSystem unitSystem) {
    return new KnownUnitQ(unitSystem.units());
  }

  // ---
  private final Set<String> set;

  private KnownUnitQ(Set<String> set) {
    this.set = set;
  }

  /** @param unit
   * @return */
  public boolean of(Unit unit) {
    return set.containsAll(unit.map().keySet());
  }
}
