// code by jph
package ch.ethz.idsc.tensor.qty;

/** auxiliary functions and operators for {@link Unit} */
public enum Units {
  ;
  /** @param unit
   * @return true if given unit is dimension-less */
  public static boolean isOne(Unit unit) {
    return Unit.ONE.equals(unit);
  }
}
