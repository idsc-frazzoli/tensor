// code by jph
package ch.ethz.idsc.tensor.opt;

import junit.framework.TestCase;

public class TensorUnaryOperatorTest extends TestCase {
  public void testFunctionalInterface() {
    assertNotNull(TensorUnaryOperator.class.getAnnotation(FunctionalInterface.class));
  }
}
