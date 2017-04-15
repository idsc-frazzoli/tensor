// code by jph
package ch.ethz.idsc.tensor;

import java.math.BigInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum Scalars {
  ;
  private static final Pattern PATTERN_INTEGER = Pattern.compile("-?\\d+");
  private static final Pattern PATTERN_RATIONAL = Pattern.compile("-?\\d+/\\d+");
  private static final Pattern PATTERN_DOUBLE = Pattern.compile(StaticHelper.fpRegex);
  private static final Pattern PATTERN_SEPARATOR = Pattern.compile("[^E][+-]");

  /** @param string
   * @return instance of Scalar for which toString().equals(string) */
  // TODO this does not work for gaussScalar
  public static Scalar fromString(String string) {
    try {
      // check complex scalar
      if (string.endsWith(ComplexScalar.IMAGINARY_SUFFIX)) {
        Matcher matcher = PATTERN_SEPARATOR.matcher(string);
        matcher.find();
        int sep = matcher.start() + 1;
        if (sep == 1) {
          // 0-3*I, 1-3*I, -3+1*I
          if (string.charAt(0) == '-') { // + should never be at the first place
            System.out.println("can be 1");
            matcher.find();
            sep = matcher.start() + 1;
          }
        }
        Scalar re = fromString(string.substring(0, sep));
        if (string.charAt(sep) == '+')
          ++sep;
        Scalar im = fromString(string.substring(sep, string.length() - 2));
        if (re instanceof StringScalar || im instanceof StringScalar)
          throw new RuntimeException();
        return ComplexScalar.of(re, im);
      }
      // check rational scalar
      if (PATTERN_RATIONAL.matcher(string).matches()) {
        int index = string.indexOf('/');
        return RationalScalar.of( //
            new BigInteger(string.substring(0, index)), //
            new BigInteger(string.substring(index + 1)));
      }
      // check integer
      if (PATTERN_INTEGER.matcher(string).matches())
        return RationalScalar.of(new BigInteger(string), BigInteger.ONE);
      // check decimal
      if (PATTERN_DOUBLE.matcher(string).matches())
        return DoubleScalar.of(Double.parseDouble(string));
    } catch (Exception exception) {
      // ---
    }
    // return as string
    return StringScalar.of(string);
  }

  /** @param s1
   * @param s2
   * @return
   * @see Double#compare(double, double)
   * @see Integer#compare(int, int) */
  public static int compare(Scalar s1, Scalar s2) {
    @SuppressWarnings("unchecked")
    Comparable<Scalar> comparable = (Comparable<Scalar>) s1;
    return comparable.compareTo(s2);
  }

  /** @param s1
   * @param s2
   * @return true if s1 < s2 */
  public static boolean lessThan(Scalar s1, Scalar s2) {
    return compare(s1, s2) == -1;
  }

  /** @param s1
   * @param s2
   * @return true if s1 <= s2 */
  public static boolean lessEquals(Scalar s1, Scalar s2) {
    return compare(s1, s2) <= 0;
  }
}
