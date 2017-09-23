// code by jph
package ch.ethz.idsc.tensor.qty;

import ch.ethz.idsc.tensor.sca.ScalarUnaryOperator;

/** UnitSystem is an operator that maps a given {@link Quantity} to a
 * {@link Quantity} that makes use only of standard units defined by
 * the unit system.
 * 
 * <p>Example when using the built-in SI definitions:
 * <pre>
 * UnitSystem.SI().apply(Quantity.of(1, "V")) == 1[A^-1*kg*m^2*s^-3]
 * UnitSystem.SI().apply(Quantity.of(125, "mi")) == 201168[m]
 * </pre>
 * 
 * <p>inspired by
 * <a href="https://reference.wolfram.com/language/ref/UnitSystem.html">UnitSystem</a> */
public interface UnitSystem extends ScalarUnaryOperator {
  /** @return international system of units, metric system */
  static UnitSystem SI() {
    return SimpleUnitSystem.SI();
  }
}
