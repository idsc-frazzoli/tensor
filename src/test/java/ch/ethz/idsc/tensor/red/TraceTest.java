// code by jph
package ch.ethz.idsc.tensor.red;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.mat.IdentityMatrix;
import junit.framework.TestCase;

public class TraceTest extends TestCase {
  public void testSimple() {
    Tensor eye = IdentityMatrix.of(10);
    Tensor red = Trace.of(eye, 0, 1);
    assertEquals(red, RealScalar.of(10));
  }
}
