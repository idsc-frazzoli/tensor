// code by jph
package ch.ethz.idsc.tensor;

/** implementation consistent with Mathematica
 * 
 * NumberQ[ 13 / 17 ] == true
 * NumberQ[ 3.1415 ] == true
 * NumberQ[ 3.1415 + 1/2*I ] == true
 * NumberQ[ Infinity ] == false
 * NumberQ[ Indeterminate ] == false
 * 
 * Tensor::isScalar returns true in all 5 cases and therefore not equivalent to NumberQ
 * 
 * NumberQ[ { ... } ] == false
 * 
 * NumberQ does not indicate whether {@link Scalar#number()} returns a Number.
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
    return MachineNumberQ.of(tensor) || ExactNumberQ.of(tensor);
  }
}
