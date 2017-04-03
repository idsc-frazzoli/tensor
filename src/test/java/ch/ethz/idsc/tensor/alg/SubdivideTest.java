// code by jph
package ch.ethz.idsc.tensor.alg;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import junit.framework.TestCase;

public class SubdivideTest extends TestCase {
  public void testSubdivide() {
    Tensor t = Subdivide.of(RealScalar.of(10), RealScalar.of(15), 5);
    Tensor r = Tensors.fromString("[10, 11, 12, 13, 14, 15]");
    assertEquals(t, r);
    assertEquals(t.toString(), r.toString());
  }

  public void testSubdivideRev() {
    Tensor t = Subdivide.of(RealScalar.of(-1), RealScalar.of(-4), 3);
    Tensor r = Tensors.fromString("[-1, -2, -3, -4]");
    assertEquals(t, r);
    assertEquals(t.toString(), r.toString());
  }
}
