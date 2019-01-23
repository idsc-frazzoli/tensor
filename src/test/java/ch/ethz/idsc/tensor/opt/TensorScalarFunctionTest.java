// code by jph
package ch.ethz.idsc.tensor.opt;

import junit.framework.TestCase;

public class TensorScalarFunctionTest extends TestCase {
  public void testFunctionalInterface() {
    assertNotNull(TensorScalarFunction.class.getAnnotation(FunctionalInterface.class));
  }
}
