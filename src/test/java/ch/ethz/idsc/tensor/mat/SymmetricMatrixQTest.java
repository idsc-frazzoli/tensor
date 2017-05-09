// code by jph
package ch.ethz.idsc.tensor.mat;

import junit.framework.TestCase;

public class SymmetricMatrixQTest extends TestCase {
  public void testHilbert() {
    assertTrue(SymmetricMatrixQ.of(HilbertMatrix.of(10)));
  }
}
