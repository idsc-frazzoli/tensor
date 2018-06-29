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
 * For instance, {@link ComplexScalar#number()} throws an exception.
 * 
 * <p>inspired by
 * <a href="https://reference.wolfram.com/language/ref/NumberQ.html">NumberQ</a>
 * 
 * @see ExactScalarQ
 * @see IntegerQ */
public enum NumberQ {
  ;
  /** @param tensor
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

  /** @param scalar
   * @return given scalar
   * @throws Exception if given scalar does not satisfy {@link #of(Tensor)} */
  public static Scalar require(Scalar scalar) {
    if (of(scalar))
      return scalar;
    throw TensorRuntimeException.of(scalar);
  }

  /** @param tensor
   * @return true if all scalar entries in given tensor satisfy the predicate {@link NumberQ#of(Tensor)} */
  public static boolean all(Tensor tensor) {
    return tensor.flatten(-1).allMatch(NumberQ::of);
  }
}
