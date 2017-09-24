// code by jph
package ch.ethz.idsc.tensor.qty;

import ch.ethz.idsc.tensor.io.ResourceData;

/* package */ enum BuiltIn {
  SI;
  // ---
  final UnitSystem unitSystem = SimpleUnitSystem.from(ResourceData.properties("/unit/si.properties"));
  final UnitConvert unitConvert = new UnitConvert(unitSystem);
  final QuantityMagnitude quantityMagnitude = new QuantityMagnitude(unitSystem);
}
