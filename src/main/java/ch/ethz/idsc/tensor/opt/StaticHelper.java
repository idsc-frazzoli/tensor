// code by jph
package ch.ethz.idsc.tensor.opt;

/* package */ enum StaticHelper {
  ;
  /** @param value positive or zero
   * @return value
   * @throws Exception if given value is negative */
  static int requirePositiveOrZero(int value) {
    if (value < 0)
      throw new IllegalArgumentException(Integer.toString(value));
    return value;
  }
}
