// code by jph
package ch.ethz.idsc.tensor.mat;

import ch.ethz.idsc.tensor.Tensor;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/QRDecomposition.html">QRDecomposition</a> */
interface QRDecomposition {
  public static QRDecomposition of(Tensor A) {
    return new QRDecompositionImpl(A.unmodifiable());
  }

  Tensor getQ();

  Tensor getR();
}
