// code by jph
package ch.ethz.idsc.tensor;

import ch.ethz.idsc.tensor.qty.Quantity;
import ch.ethz.idsc.tensor.qty.Unit;

/* package */ enum QuantityParser {
  ;
  /** Example:
   * "9.81[m*s^-2]" -> Quantity.of(9.81, "m*s^-2")
   * 
   * @param string
   * @return */
  static Scalar of(final String string) {
    final int index = string.indexOf(Quantity.UNIT_OPENING_BRACKET);
    if (0 < index) {
      final int last = string.indexOf(Quantity.UNIT_CLOSING_BRACKET);
      if (index < last && string.substring(last + 1).trim().isEmpty())
        return Quantity.of( //
            Scalars.fromString(string.substring(0, index)), //
            Unit.of(string.substring(index + 1, last)));
      throw new RuntimeException(string);
    }
    return ScalarParser.of(string);
  }
}
