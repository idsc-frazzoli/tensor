// code by jph
package ch.ethz.idsc.tensor.sca;

public enum Clips {
  ;
  private static final Clip UNIT = Clip.function(0, 1);
  private static final Clip ABSOLUTE_ONE = Clip.function(-1, 1);

  /** @return function that clips scalars to the unit interval [0, 1] */
  public static Clip unit() {
    return UNIT;
  }

  /** @return function that clips scalars to the interval [-1, 1] */
  public static Clip absoluteOne() {
    return ABSOLUTE_ONE;
  }
}
