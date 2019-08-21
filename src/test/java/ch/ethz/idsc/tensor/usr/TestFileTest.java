// code by jph
package ch.ethz.idsc.tensor.usr;

import java.io.File;

import ch.ethz.idsc.tensor.io.HomeDirectory;
import junit.framework.TestCase;

public class TestFileTest extends TestCase {
  public void testSimple() {
    File file = TestFile.withExtension("ethz");
    assertEquals(file, HomeDirectory.file("TestFileTest_testSimple.ethz"));
  }
}
