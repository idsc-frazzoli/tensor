// code by jph
package ch.ethz.idsc.tensor.qty;

import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Scalars;

/* package */ enum QuantityParser {
  ;
  static Scalar of(final String string) {
    final int index = string.indexOf(Quantity.UNIT_OPENING_BRACKET);
    if (0 < index) {
      final int last = string.indexOf(Quantity.UNIT_CLOSING_BRACKET);
      if (index < last && string.substring(last + 1).trim().isEmpty())
        return QuantityImpl.of( //
            Scalars.fromString(string.substring(0, index)), //
            Unit.of(string.substring(index + 1, last)));
      throw new RuntimeException(string);
    }
    return Scalars.fromString(string);
  }
}
