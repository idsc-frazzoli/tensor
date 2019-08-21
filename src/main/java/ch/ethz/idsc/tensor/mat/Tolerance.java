// code by jph
package ch.ethz.idsc.tensor.mat;

import ch.ethz.idsc.tensor.sca.Chop;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/Tolerance.html">Tolerance</a> */
/* package */ enum Tolerance {
  ;
  /** default threshold below which to consider:
   * a singular value as zero,
   * values equal to determine symmetry,
   * etc. */
  public static final Chop CHOP = Chop._12;
}
