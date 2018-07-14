// code by jph
package ch.ethz.idsc.tensor;

import java.util.Objects;
import java.util.function.Function;

import ch.ethz.idsc.tensor.io.StringScalar;

/* package */ class TensorParser {
  private static final String OPENING_BRACKET_STRING = "" + Tensor.OPENING_BRACKET;
  private static final char COMMA = ',';
  // ---
  public static final TensorParser DEFAULT = new TensorParser(Scalars::fromString);
  // ---
  private final Function<String, Scalar> function; // not Serializable

  /** @param function that parses a string to a scalar
   * @throws Exception if given function is null */
  public TensorParser(Function<String, Scalar> function) {
    this.function = Objects.requireNonNull(function);
  }

  public Tensor parse(String string) {
    // could implement using stack?
    if (string.startsWith(OPENING_BRACKET_STRING)) { // first character is "{"
      Tensor tensor = Tensors.empty();
      int level = 0; // track nesting with "{" and "}"
      int beg = -1;
      for (int index = 0; index < string.length(); ++index) {
        final char chr = string.charAt(index);
        if (chr == Tensor.OPENING_BRACKET) {
          ++level;
          if (level == 1)
            beg = index + 1;
        }
        boolean isComma = chr == COMMA;
        boolean closing = chr == Tensor.CLOSING_BRACKET;
        if (level == 1 && (isComma || closing)) {
          String entry = string.substring(beg, index).trim(); // trim is required
          if (!entry.isEmpty() || !closing || 0 < tensor.length())
            tensor.append(parse(entry));
          beg = index + 1;
        }
        if (closing)
          --level;
      }
      if (level != 0)
        return StringScalar.of(string);
      return tensor;
    }
    return function.apply(string);
  }
}
