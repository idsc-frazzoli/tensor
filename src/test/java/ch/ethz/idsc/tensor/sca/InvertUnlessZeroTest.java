// code by jph
package ch.ethz.idsc.tensor.sca;

import ch.ethz.idsc.tensor.ExactScalarQ;
import ch.ethz.idsc.tensor.RationalScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.qty.Quantity;
import junit.framework.TestCase;

public class InvertUnlessZeroTest extends TestCase {
  public void testVector() {
    Tensor tensor = InvertUnlessZero.of(Tensors.vector(1, 0, 2));
    assertEquals(tensor, Tensors.vector(1, 0, 0.5));
    assertTrue(ExactScalarQ.all(tensor));
  }

  public void testQuantity() {
    Scalar scalar = Quantity.of(-2, "m");
    Scalar invert = InvertUnlessZero.FUNCTION.apply(scalar);
    assertTrue(ExactScalarQ.of(invert));
    assertEquals(invert, Quantity.of(RationalScalar.HALF, "m^-1").negate());
  }

  public void testQuantityFail() {
    Scalar scalar = Quantity.of(0, "m");
    Scalar invert = InvertUnlessZero.FUNCTION.apply(scalar);
    assertTrue(ExactScalarQ.of(invert));
    assertEquals(invert, Quantity.of(RealScalar.ZERO, "m^-1"));
  }
}
