// code by jph
package ch.ethz.idsc.tensor.sca.win;

import java.lang.reflect.Modifier;

import junit.framework.TestCase;

public class StaticHelperTest extends TestCase {
  public void testPackageVisibility() {
    assertFalse(Modifier.isPublic(StaticHelper.class.getModifiers()));
  }
}
