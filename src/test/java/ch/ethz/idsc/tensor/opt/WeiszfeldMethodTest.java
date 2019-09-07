// code by jph
package ch.ethz.idsc.tensor.opt;

import ch.ethz.idsc.tensor.RealScalar;
import junit.framework.TestCase;

public class WeiszfeldMethodTest extends TestCase {
  public void testNegativeFail() {
    try {
      new WeiszfeldMethod(RealScalar.of(-0.1));
      fail();
    } catch (Exception exception) {
      // ---
    }
  }

  public void testNullFail() {
    try {
      new WeiszfeldMethod(null);
      fail();
    } catch (Exception exception) {
      // ---
    }
  }
}
