// code by jph
package ch.ethz.idsc.tensor;

import ch.ethz.idsc.tensor.qty.Quantity;

/** implementation consistent with Mathematica
 * 
 * <p>Examples:
 * <pre>
 * NumberQ[ 13 / 17 ] == true
 * NumberQ[ 3.1415 ] == true
 * NumberQ[ 3.1415 + 1/2*I ] == true
 * 
 * NumberQ[ Infinity ] == false
 * NumberQ[ Indeterminate ] == false
 * NumberQ.of(Quantity.of(3, "m")) == false
 * </pre>
 * 
 * <p>{@link ScalarQ} returns true in all cases and is therefore not equivalent to NumberQ.
 * 
 * <pre>
 * NumberQ[ { ... } ] == false
 * </pre>
 * 
 * <p>NumberQ does not indicate whether {@link Scalar#number()} returns a Number.
 * 
 * <p>inspired by
 * <a href="https://reference.wolfram.com/language/ref/NumberQ.html">NumberQ</a> */
public enum NumberQ {
  ;
  /** see description above
   * 
   * @param tensor
   * @return */
  public static boolean of(Tensor tensor) {
    if (tensor instanceof ComplexScalar) {
      ComplexScalar complexScalar = (ComplexScalar) tensor;
      return of(complexScalar.real()) && of(complexScalar.imag());
    }
    if (tensor instanceof Quantity)
      return false;
    return MachineNumberQ.of(tensor) || ExactScalarQ.of(tensor);
  }
}
