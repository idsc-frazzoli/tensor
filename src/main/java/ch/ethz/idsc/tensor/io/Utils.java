// code by jph
package ch.ethz.idsc.tensor.io;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

enum Utils {
  ;
  /** @param string
   * @return string with opening and closing bracket removed, if brackets are present */
  static String removeEnclosingBrackets(final String string) {
    if (string.startsWith("[") && string.endsWith("]"))
      return string.substring(1, string.length() - 1);
    return string;
  }

  static String spaces(int level) {
    return IntStream.range(0, level).boxed().map(i -> " ").collect(Collectors.joining());
  }
}
