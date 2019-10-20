// code by jph
package ch.ethz.idsc.tensor.lie;

import java.lang.reflect.Modifier;

import ch.ethz.idsc.tensor.Tensors;
import junit.framework.TestCase;

public class QRDecompositionImplTest extends TestCase {
  public void testEmpty() {
    try {
      QRDecomposition.of(Tensors.empty());
      fail();
    } catch (Exception exception) {
      // ---
    }
  }

  public void testFail() {
    try {
      QRDecomposition.of(Tensors.fromString("{{1, 2}, {3, 4, 5}}"));
      fail();
    } catch (Exception exception) {
      // ---
    }
    try {
      QRDecomposition.of(LieAlgebras.sl2());
      fail();
    } catch (Exception exception) {
      // ---
    }
  }

  public void testPackageVisibility() {
    assertTrue(Modifier.isPublic(QRDecomposition.class.getModifiers()));
    assertFalse(Modifier.isPublic(QRDecompositionImpl.class.getModifiers()));
  }
}
