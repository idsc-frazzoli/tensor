// code by jph
package ch.ethz.idsc.tensor.sca;

import ch.ethz.idsc.tensor.ExactScalarQ;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import junit.framework.TestCase;

public class InvertUnlessZeroTest extends TestCase {
  public void testVector() {
    Tensor tensor = InvertUnlessZero.of(Tensors.vector(1, 0, 2));
    assertEquals(tensor, Tensors.vector(1, 0, 0.5));
    assertTrue(ExactScalarQ.all(tensor));
  }
}
