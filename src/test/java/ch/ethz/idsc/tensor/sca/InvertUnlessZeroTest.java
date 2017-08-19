// code by jph
package ch.ethz.idsc.tensor.sca;

import ch.ethz.idsc.tensor.Tensors;
import junit.framework.TestCase;

public class InvertUnlessZeroTest extends TestCase {
  public void testSimple() {
    assertEquals(InvertUnlessZero.of(Tensors.vector(1, 0, 2)), Tensors.vector(1, 0, 0.5));
  }
}
