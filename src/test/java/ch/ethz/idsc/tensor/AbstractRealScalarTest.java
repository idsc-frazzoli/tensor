// code by jph
package ch.ethz.idsc.tensor;

import junit.framework.TestCase;

public class AbstractRealScalarTest extends TestCase {
  public void testRange() {
    assertEquals(Math.log(AbstractRealScalar.LOG_HI), Math.log1p(AbstractRealScalar.LOG_HI - 1));
    assertEquals(Math.log(AbstractRealScalar.LOG_LO), Math.log1p(AbstractRealScalar.LOG_LO - 1));
  }
}
