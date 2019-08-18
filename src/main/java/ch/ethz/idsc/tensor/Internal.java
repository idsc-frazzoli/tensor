// code by jph
package ch.ethz.idsc.tensor;

/** inspired by Mathematica::Internal` */
public enum Internal {
  ;
  /** @param value non-negative
   * @return value
   * @throws Exception if given value is negative */
  public static int requirePositiveOrZero(int value) {
    if (value < 0)
      throw new IllegalArgumentException(Integer.toString(value));
    return value;
  }

  /** @param value strictly positive
   * @return value
   * @throws Exception if given value is negative or zero */
  public static int requirePositive(int value) {
    if (value <= 0)
      throw new IllegalArgumentException(Integer.toString(value));
    return value;
  }
}
