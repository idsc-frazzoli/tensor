// code by jph
package ch.ethz.idsc.tensor;

import junit.framework.TestCase;

public class TensorImplTest extends TestCase {
  public void testString() {
    String demo = "[1,2,3]";
    StringBuilder stringBuilder = new StringBuilder(demo.length());
    stringBuilder.append('{');
    stringBuilder.append(demo, 1, demo.length() - 1);
    stringBuilder.append('}');
    assertEquals(stringBuilder.toString(), "{1,2,3}");
  }
}
