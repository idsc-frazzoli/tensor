// code by jph
package ch.ethz.idsc.tensor;

import java.math.BigInteger;
import java.util.regex.Pattern;

public class Scalars {
  private static final Pattern PATTERN_INTEGER = Pattern.compile("-?\\d+");
  private static final Pattern PATTERN_RATIONAL = Pattern.compile("-?\\d+/\\d+");
  private static final Pattern PATTERN_DOUBLE = Pattern.compile(StaticHelper.fpRegex);

  /** @param string
   * @return instance of Scalar for which toString().equals(string) */
  // TODO this does not work for gaussScalar
  // TODO if the real and imag part of complex cannot be parsed to scalar -> return stringScalar
  public static Scalar fromString(String string) {
    try {
      // check complex scalar
      if (string.endsWith(ComplexScalar.IMAGINARY)) {
        int sep;
        sep = string.indexOf('+', 1);
        if (0 < sep)
          return ComplexScalar.of( //
              fromString(string.substring(0, sep)), //
              fromString(string.substring(sep + 1, string.length() - 2)) //
          );
        // TODO this check is generally not sufficient -2E-10-2*I
        // ... have to check if E is before -
        sep = string.indexOf('-', 1);
        if (0 < sep)
          return ComplexScalar.of( //
              fromString(string.substring(0, sep)), //
              fromString(string.substring(sep + 1, string.length() - 2)).negate() //
          );
        return ComplexScalar.of( //
            ZeroScalar.get(), //
            fromString(string.substring(0, string.length() - 2)) //
        );
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
}
