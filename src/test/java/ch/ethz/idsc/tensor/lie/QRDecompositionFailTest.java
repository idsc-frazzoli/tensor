// code by jph
package ch.ethz.idsc.tensor.lie;

import ch.ethz.idsc.tensor.Tensors;
import junit.framework.TestCase;

public class QRDecompositionFailTest extends TestCase {
  public void testEmpty() {
    try {
      QRDecomposition.of(Tensors.empty());
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }

  public void testFail() {
    try {
      QRDecomposition.of(Tensors.fromString("{{1,2},{3,4,5}}"));
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
    try {
      QRDecomposition.of(LieAlgebras.sl2());
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }
}
