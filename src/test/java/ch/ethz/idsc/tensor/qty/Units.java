// code by jph
package ch.ethz.idsc.tensor.qty;

enum Units {
  ;
  /** Example in Mathematica: Quantity[1.2, "Radians"] */
  public static final Unit RADIANS = Unit.of("[rad]");
  /** Example in Mathematica: Sin[360 Degree] == 0 */
  public static final Unit DEGREE = Unit.of("[deg]");
}
