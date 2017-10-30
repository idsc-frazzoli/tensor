// code by jph
package ch.ethz.idsc.tensor;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/* package */ enum TensorParser {
  ;
  private static final String OPENING_BRACKET_STRING = "" + Tensor.OPENING_BRACKET;
  private static final char COMMA = ',';

  /** @param string
   * @param function that parses a string to a scalar
   * @return */
  static Tensor of(final String string, Function<String, Scalar> function) {
    // could implement using stack?
    if (string.startsWith(OPENING_BRACKET_STRING)) {
      List<Tensor> list = new ArrayList<>();
      int level = 0;
      int beg = -1;
      for (int index = 0; index < string.length(); ++index) {
        final char chr = string.charAt(index);
        if (chr == Tensor.OPENING_BRACKET) {
          ++level;
          if (level == 1)
            beg = index + 1;
        }
        if (level == 1 && (chr == COMMA || chr == Tensor.CLOSING_BRACKET)) {
          String entry = string.substring(beg, index).trim(); // trim is required
          if (!entry.isEmpty())
            list.add(of(entry, function));
          beg = index + 1;
        }
        if (chr == Tensor.CLOSING_BRACKET)
          --level;
      }
      if (level != 0)
        return StringScalar.of(string);
      return Tensor.of(list.stream());
    }
    return function.apply(string);
  }
}
