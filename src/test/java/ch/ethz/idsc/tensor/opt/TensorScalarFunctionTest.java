// code by jph
package ch.ethz.idsc.tensor.opt;

import java.io.IOException;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.io.Serialization;
import junit.framework.TestCase;

public class TensorScalarFunctionTest extends TestCase {
  public void testFunctionalInterface() {
    assertNotNull(TensorScalarFunction.class.getAnnotation(FunctionalInterface.class));
  }

  public void testSerializable() throws ClassNotFoundException, IOException {
    TensorScalarFunction tensorScalarFunction = t -> t.Get(0);
    TensorScalarFunction copy = Serialization.copy(tensorScalarFunction);
    assertEquals(copy.apply(Tensors.vector(1, 2, 3)), RealScalar.ONE);
  }
}
