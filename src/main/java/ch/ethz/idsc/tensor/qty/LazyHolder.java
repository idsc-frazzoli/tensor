// code by jph
package ch.ethz.idsc.tensor.qty;

import ch.ethz.idsc.tensor.io.ResourceData;

/** Initialization On Demand Holder Idiom */
/* package */ enum LazyHolder {
  SI;
  /** Cached field instance. */
  final UnitSystem unitSystem = SimpleUnitSystem.from(ResourceData.properties("/unit/si.properties"));
}