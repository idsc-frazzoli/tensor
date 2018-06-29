// code by jph
package ch.ethz.idsc.tensor;

/** implementation consistent with Mathematica
 * 
 * <p>Examples:
 * <pre>
 * IntegerQ.of(RationalScalar.of(7, 1)) == true
 * IntegerQ.of(RationalScalar.of(7, 2)) == false
 * IntegerQ.of(DoubleScalar.of(7)) == false
 * </pre>
 * 
 * <p>inspired by
 * <a href="https://reference.wolfram.com/language/ref/IntegerQ.html">IntegerQ</a>
 * 
 * @see ExactScalarQ */
public enum IntegerQ {
  ;
  /** @param tensor
   * @return true, if tensor is instance of {@link RationalScalar} with denominator == 1 */
  public static boolean of(Tensor tensor) {
    return tensor instanceof RationalScalar && ((RationalScalar) tensor).isInteger();
  }

  /** @param scalar
   * @return given scalar
   * @throws Exception if given scalar is not an integer in exact precision */
  public static Scalar require(Scalar scalar) {
    if (of(scalar))
      return scalar;
    throw TensorRuntimeException.of(scalar);
  }
}
