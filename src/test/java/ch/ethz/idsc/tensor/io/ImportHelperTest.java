// code by jph
package ch.ethz.idsc.tensor.io;

import junit.framework.TestCase;

public class ImportHelperTest extends TestCase {
  public void testSwitch() {
    Extension extension = null;
    try {
      extension = Extension.valueOf("asd");
    } catch (Exception exception) {
      // ---
    }
    try {
      switch (extension) {
      default:
      }
    } catch (NullPointerException exception) {
      // ---
    }
  }
}
