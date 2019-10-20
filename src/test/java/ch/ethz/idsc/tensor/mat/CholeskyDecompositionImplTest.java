// code by jph
package ch.ethz.idsc.tensor.mat;

import java.lang.reflect.Modifier;

import junit.framework.TestCase;

public class CholeskyDecompositionImplTest extends TestCase {
  public void testPackageVisibility() {
    assertTrue(Modifier.isPublic(CholeskyDecomposition.class.getModifiers()));
    assertFalse(Modifier.isPublic(CholeskyDecompositionImpl.class.getModifiers()));
  }
}
