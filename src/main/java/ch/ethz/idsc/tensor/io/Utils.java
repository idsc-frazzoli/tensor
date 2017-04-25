// code by jph
package ch.ethz.idsc.tensor.io;

import ch.ethz.idsc.tensor.Tensor;

/* package */ enum Utils {
  ;
  private static final String OPENING_BRACKET_STRING = "" + Tensor.OPENING_BRACKET;
  private static final String CLOSING_BRACKET_STRING = "" + Tensor.CLOSING_BRACKET;

  /** @param string
   * @return string with opening and closing bracket removed, if brackets are present */
  // function only used in CsvFormat
  static String removeEnclosingBracketsIfPresent(final String string) {
    if (string.startsWith(OPENING_BRACKET_STRING) && string.endsWith(CLOSING_BRACKET_STRING))
      return string.substring(1, string.length() - 1);
    return string;
  }

  /** @param string
   * @return '{' + string + '}' */
  // function only used in CsvFormat
  static String encloseWithBrackets(final String string) {
    StringBuilder stringBuilder = new StringBuilder(1 + string.length() + 1);
    stringBuilder.append(Tensor.OPENING_BRACKET);
    stringBuilder.append(string);
    stringBuilder.append(Tensor.CLOSING_BRACKET);
    return stringBuilder.toString();
  }
}
