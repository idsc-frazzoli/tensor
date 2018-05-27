// code by jph
package ch.ethz.idsc.tensor.usr;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import junit.framework.TestCase;

public class MultinomialTest extends TestCase {
  public void testDerivative() {
    Tensor coeffs = Tensors.vector(-3, 4, -5, 8, 1);
    Tensor result = Multinomial.derivative(coeffs);
    assertEquals(result, Tensors.vector(4, -5 * 2, 8 * 3, 1 * 4));
  }

  public void testDerivativeEmpty() {
    assertEquals(Multinomial.derivative(Tensors.vector()), Tensors.vector());
    assertEquals(Multinomial.derivative(Tensors.vector(3)), Tensors.empty());
  }

  public void testDerivativeScalarFail() {
    try {
      Multinomial.derivative(RealScalar.ONE);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }
}
