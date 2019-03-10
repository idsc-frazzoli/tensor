// code by jph
package ch.ethz.idsc.tensor;

import junit.framework.TestCase;

public class ExactTensorQTest extends TestCase {
  public void testAll() {
    assertFalse(ExactTensorQ.of(Tensors.vector(1, 1, 1.)));
    assertTrue(ExactTensorQ.of(Tensors.vector(1, 2, 3)));
  }

  public void testRequireAll() {
    ExactTensorQ.require(Tensors.fromString("{{9/8,3/2[s]},1/2+3/4*I}"));
    try {
      ExactTensorQ.require(Tensors.vector(1, 2, 3, .7));
      fail();
    } catch (Exception exception) {
      // ---
    }
  }
}
