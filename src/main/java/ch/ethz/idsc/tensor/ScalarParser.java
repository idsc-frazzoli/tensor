// code by jph
package ch.ethz.idsc.tensor;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

enum ScalarParser {
  ;
  private static final char OPENING_BRACKET = '(';
  private static final char CLOSING_BRACKET = ')';
  private static final char ADD = '+';
  private static final char SUBTRACT = '-';
  private static final char MULTIPLY = '*';
  private static final char DIVIDE = '/';
  // ---
  private static final Pattern PATTERN_INTEGER = Pattern.compile("\\d+"); // optional sign is obsolete
  private static final Pattern PATTERN_DOUBLE = Pattern.compile(StaticHelper.fpRegex);

  static Scalar of(final String string) {
    final String expr = string.trim();
    final char[] chars = expr.toCharArray();
    final List<Integer> plusMinus = new ArrayList<>();
    Integer times = null;
    Integer divide = null;
    {
      int index = 0;
      int level = 0;
      for (char c : chars) {
        if (c == OPENING_BRACKET)
          ++level;
        if (level == 0) {
          if (c == ADD || c == SUBTRACT) {
            if (index == 0 || chars[index - 1] != 'E')
              plusMinus.add(index);
          } else //
          if (c == MULTIPLY)
            times = index;
          else //
          if (c == DIVIDE)
            divide = index;
        }
        if (c == CLOSING_BRACKET)
          --level;
        ++index;
      }
    }
    if (!plusMinus.isEmpty()) {
      int first = plusMinus.get(0);
      Scalar sum = first == 0 ? RealScalar.ZERO : of(expr.substring(0, first));
      for (int index = 0; index < plusMinus.size(); ++index) {
        int curr = plusMinus.get(index);
        final char c = chars[curr];
        int next = index + 1 < plusMinus.size() ? plusMinus.get(index + 1) : expr.length();
        if (c == ADD)
          sum = sum.add(of(expr.substring(curr + 1, next)));
        if (c == SUBTRACT)
          sum = sum.subtract(of(expr.substring(curr + 1, next)));
      }
      return sum;
    }
    if (Objects.nonNull(times))
      return of(expr.substring(0, times)).multiply(of(expr.substring(times + 1)));
    if (Objects.nonNull(divide))
      return of(expr.substring(0, divide)).divide(of(expr.substring(divide + 1)));
    if (expr.startsWith("(") && expr.endsWith(")"))
      return of(expr.substring(1, expr.length() - 1));
    if (expr.equals(ComplexScalar.I_SYMBOL))
      return ComplexScalar.I;
    if (PATTERN_INTEGER.matcher(expr).matches()) // check integer
      return RationalScalar.of(new BigInteger(expr), BigInteger.ONE);
    if (PATTERN_DOUBLE.matcher(expr).matches()) // check decimal
      return DoubleScalar.of(Double.parseDouble(expr));
    throw new RuntimeException(expr);
  }
}
