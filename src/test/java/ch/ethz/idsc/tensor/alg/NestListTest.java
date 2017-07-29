// code by jph
package ch.ethz.idsc.tensor.alg;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.sca.Cos;
import junit.framework.TestCase;

public class NestListTest extends TestCase {
  public void testSimple() {
    Tensor list = NestList.of(Cos::of, RealScalar.ONE, 4);
    assertEquals(list.length(), 5);
  }
}
