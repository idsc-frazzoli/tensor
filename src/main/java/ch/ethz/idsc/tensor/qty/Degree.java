// code by jph
package ch.ethz.idsc.tensor.qty;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/Degree.html">Degree</a> */
public enum Degree {
  ;
  private static final Unit DEGREE = Unit.of("deg");

  /** the equivalent conversion formula in Mathematica is
   * QuantityMagnitude[Quantity[1, "Degrees"], "Radians"]
   * 
   * @param degree
   * @return radian == degree / 180 * PI */
  public static Scalar of(Number degree) {
    return of(RealScalar.of(degree));
  }

  /** @param degree
   * @return radian == degree / 180 * PI */
  public static Scalar of(Scalar degree) {
    return UnitSystem.SI().apply(Quantity.of(degree, DEGREE));
  }
}
