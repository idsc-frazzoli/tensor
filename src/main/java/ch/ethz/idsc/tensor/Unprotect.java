// code by jph
package ch.ethz.idsc.tensor;

/** The use of Unprotect in the application later is not recommended.
 * 
 * <p>inspired by
 * <a href="https://reference.wolfram.com/language/ref/Unprotect.html">Unprotect</a> */
public enum Unprotect {
  ;
  // undocumented
  public static int length0(Tensor tensor) {
    return ((TensorImpl) tensor).length0();
  }
}
