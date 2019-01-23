// code by jph
package ch.ethz.idsc.tensor.opt;

import junit.framework.TestCase;

public class ScalarTensorFunctionTest extends TestCase {
  public void testFunctionalInterface() {
    assertNotNull(ScalarTensorFunction.class.getAnnotation(FunctionalInterface.class));
  }
}
