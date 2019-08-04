// code by jph
package ch.ethz.idsc.tensor.qty;

import java.util.Comparator;

import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.io.ResourceData;

/** singleton instance of built-in SI unit system */
/* package */ enum BuiltIn {
  SI;
  // ---
  final UnitSystem unitSystem = SimpleUnitSystem.from(ResourceData.properties("/unit/si.properties"));
  final UnitConvert unitConvert = new UnitConvert(unitSystem);
  final QuantityMagnitude quantityMagnitude = new QuantityMagnitude(unitSystem);
  final CompatibleUnitQ compatibleUnitQ = CompatibleUnitQ.in(unitSystem);
  final KnownUnitQ knownUnitQ = KnownUnitQ.in(unitSystem);
  final Comparator<Scalar> comparator = QuantityComparator.of(unitSystem);
}
