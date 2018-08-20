// code by jph
package ch.ethz.idsc.tensor.alg;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import junit.framework.TestCase;

public class DeleteDuplicatesTest extends TestCase {
  public void testVector() {
    Tensor unique = DeleteDuplicates.of(Tensors.vector(7, 3, 3, 7, 1, 2, 3, 2, 3, 1));
    assertEquals(unique, Tensors.vector(7, 3, 1, 2));
  }

  public void testReferences() {
    Tensor tensor = Tensors.fromString("{{1,2},{1,2}}");
    Tensor unique = DeleteDuplicates.of(tensor);
    unique.set(RealScalar.ZERO, 0, 0);
    assertEquals(tensor, Tensors.fromString("{{1, 2}, {1, 2}}"));
  }

  public void testScalar() {
    try {
      DeleteDuplicates.of(RealScalar.ONE);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }
}
