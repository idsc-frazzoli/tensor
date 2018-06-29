// code by jph
package ch.ethz.idsc.tensor.mat;

import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import junit.framework.TestCase;

public class ConjugateTransposeTest extends TestCase {
  public void testExample1() {
    Tensor m1 = Tensors.fromString("{{1,5+I},{2,3}}");
    Tensor m2 = Tensors.fromString("{{1,2},{5-I,3}}");
    assertEquals(ConjugateTranspose.of(m1), m2);
  }

  public void testExample2() {
    Tensor m1 = Tensors.fromString("{{1+2*I,5+I},{2,3}}");
    Tensor m2 = Tensors.fromString("{{1-2*I,2},{5-I,3}}");
    assertEquals(ConjugateTranspose.of(m1), m2);
  }
}
