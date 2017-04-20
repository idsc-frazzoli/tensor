// code by jph
package ch.ethz.idsc.tensor;

public enum Scalars {
  ;
  /** @param string
   * @return instance of Scalar for which toString().equals(string) */
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
    return compare(s1, s2) == -1;
  }

  /** @param s1
   * @param s2
   * @return true if s1 <= s2 */
  public static boolean lessEquals(Scalar s1, Scalar s2) {
    return compare(s1, s2) <= 0;
  }
}
