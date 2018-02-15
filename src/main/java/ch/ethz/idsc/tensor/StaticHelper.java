// code by jph
// https://docs.oracle.com/javase/7/docs/api/java/lang/Double.html#valueOf(java.lang.String)
package ch.ethz.idsc.tensor;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import ch.ethz.idsc.tensor.sca.ArcTan;
import ch.ethz.idsc.tensor.sca.ComplexEmbedding;
import ch.ethz.idsc.tensor.sca.Sign;

/* package */ enum StaticHelper {
  ;
  static final Collector<CharSequence, ?, String> EMBRACE = //
      Collectors.joining(", ", "" + Tensor.OPENING_BRACKET, "" + Tensor.CLOSING_BRACKET);
  // ---
  /** code from java.lang.Double */
  private static final String Digits = "(\\p{Digit}+)";
  private static final String HexDigits = "(\\p{XDigit}+)";
  // an exponent is 'e' or 'E' followed by an optionally
  // signed decimal integer.
  private static final String Exp = "[eE][+-]?" + Digits;
  // optional leading and trailing whitespace and sign is obsolete
  static final String fpRegex = ("(" + //
      "NaN|" + // "NaN" string
      "Infinity|" + // "Infinity" string
      // A decimal floating-point string representing a finite positive
      // number without a leading sign has at most five basic pieces:
      // Digits . Digits ExponentPart FloatTypeSuffix
      //
      // Since this method allows integer-only strings as input
      // in addition to strings of floating-point literals, the
      // two sub-patterns below are simplifications of the grammar
      // productions from section 3.10.2 of
      // The Java Language Specification.
      // Digits ._opt Digits_opt ExponentPart_opt FloatTypeSuffix_opt
      "(((" + Digits + "(\\.)?(" + Digits + "?)(" + Exp + ")?)|" +
      // . Digits ExponentPart_opt FloatTypeSuffix_opt
      "(\\.(" + Digits + ")(" + Exp + ")?)|" +
      // Hexadecimal strings
      "((" +
      // 0[xX] HexDigits ._opt BinaryExponent FloatTypeSuffix_opt
      "(0[xX]" + HexDigits + "(\\.)?)|" +
      // 0[xX] HexDigits_opt . HexDigits BinaryExponent FloatTypeSuffix_opt
      "(0[xX]" + HexDigits + "?(\\.)" + HexDigits + ")" + ")[pP][+-]?" + Digits + "))" + "[fFdD]?))" //
  );

  // throws an exception if value is Infinity
  static BigInteger floor(BigDecimal bd) {
    BigInteger bi = bd.toBigInteger();
    if (0 < new BigDecimal(bi).compareTo(bd)) {
      bd = bd.subtract(BigDecimal.ONE);
      bi = bd.toBigInteger();
    }
    return bi;
  }

  // throws an exception if value is Infinity
  static BigInteger ceiling(BigDecimal bd) {
    BigInteger bi = bd.toBigInteger();
    if (new BigDecimal(bi).compareTo(bd) < 0) {
      bd = bd.add(BigDecimal.ONE);
      bi = bd.toBigInteger();
    }
    return bi;
  }

  private static final Scalar PI_HALF = DoubleScalar.of(Math.PI / 2);

  /** @param x complex scalar
   * @param y complex scalar
   * @return Mathematica::ArcTan[x, y] */
  static Scalar arcTan(Scalar x, Scalar y) {
    if (Scalars.isZero(x)) { // prevent division by zero
      ComplexEmbedding complexEmbedding = (ComplexEmbedding) y;
      return Sign.FUNCTION.apply(complexEmbedding.real()).multiply(PI_HALF);
    }
    return ArcTan.FUNCTION.apply(y.divide(x));
  }
}
