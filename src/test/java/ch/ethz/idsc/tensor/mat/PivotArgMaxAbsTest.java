// code by jph
package ch.ethz.idsc.tensor.mat;

import java.lang.reflect.Modifier;

import junit.framework.TestCase;

public class PivotArgMaxAbsTest extends TestCase {
  public void testPackageVisibility() {
    assertFalse(Modifier.isPublic(PivotArgMaxAbs.class.getModifiers()));
  }
}
