// code by jph
package ch.ethz.idsc.tensor.mat;

import ch.ethz.idsc.tensor.Tensors;
import junit.framework.TestCase;

public class EigensystemTest extends TestCase {
  public void testNonSymmetricFail() {
    try {
      Eigensystem.ofSymmetric(Tensors.fromString("{{1,2},{3,4}}"));
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }
}
