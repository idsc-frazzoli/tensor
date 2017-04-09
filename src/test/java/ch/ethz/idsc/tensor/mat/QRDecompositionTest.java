// code by jph
package ch.ethz.idsc.tensor.mat;

import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import junit.framework.TestCase;

public class QRDecompositionTest extends TestCase {
  public void testExampleP32() {
    Tensor A = Tensors.matrixInt(new int[][] { { -1, -1, 1 }, { 1, 3, 3 }, { -1, -1, 5 }, { 1, 3, 7 } });
    QRDecomposition qr = QRDecomposition.of(A);
  }
}
