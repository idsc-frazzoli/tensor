// code by jph
package ch.ethz.idsc.tensor.opt;

import java.io.IOException;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.io.Serialization;
import junit.framework.TestCase;

public class ScalarTensorFunctionTest extends TestCase {
  public void testFunctionalInterface() {
    assertNotNull(ScalarTensorFunction.class.getAnnotation(FunctionalInterface.class));
  }

  public void testSerializable() throws ClassNotFoundException, IOException {
    ScalarTensorFunction tensorScalarFunction = s -> Tensors.of(s, s, s);
    ScalarTensorFunction copy = Serialization.copy(tensorScalarFunction);
    assertEquals(copy.apply(RealScalar.ONE), Tensors.vector(1, 1, 1));
  }
}
