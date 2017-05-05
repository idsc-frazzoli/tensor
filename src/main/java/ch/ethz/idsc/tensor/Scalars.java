// code by jph
package ch.ethz.idsc.tensor;

import ch.ethz.idsc.tensor.alg.BinaryPower;

public enum Scalars {
  ;
  /** parses string to scalar
   * 
   * Example:
   * "(3+2)*I/(-1+4)+8-I" -> ComplexScalar.of(8, 2/3) == "8+2/3*I"
   * 
   * @param string
   * @return scalar */
  // TODO this does not work for gaussScalar
  public static Scalar fromString(String string) {
    try {
      return ScalarParser.of(string);
    } catch (Exception exception) {
      // ---
    }
    return StringScalar.of(string); // return as string
  }

  /** @param scalar
   * @return true if input scalar is not an instance of {@link StringScalar} */
  public static boolean isNonStringScalar(Scalar scalar) {
    return !(scalar instanceof StringScalar);
  }

  /** @param scalar
   * @return true if input scalar is an instance of {@link StringScalar} */
  public static boolean isStringScalar(Scalar scalar) {
    return scalar instanceof StringScalar;
  }

  /** @param s1
   * @param s2
   * @return canonic/native comparison of input scalars
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
    return compare(s1, s2) < 0;
  }

  /** @param s1
   * @param s2
   * @return true if s1 <= s2 */
  public static boolean lessEquals(Scalar s1, Scalar s2) {
    return compare(s1, s2) <= 0;
  }

  public static BinaryPower<Scalar> binaryPower(Scalar one) {
    return new BinaryPower<Scalar>() {
      @Override
      public Scalar zeroth() {
        return one;
      }

      @Override
      public Scalar invert(Scalar scalar) {
        return scalar.invert();
      }

      @Override
      public Scalar multiply(Scalar s1, Scalar s2) {
        return s1.multiply(s2);
      }
    };
  }
}
