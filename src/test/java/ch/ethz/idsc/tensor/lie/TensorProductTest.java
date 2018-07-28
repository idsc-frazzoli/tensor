// code by jph
package ch.ethz.idsc.tensor.lie;

import java.util.Arrays;

import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.alg.Dimensions;
import ch.ethz.idsc.tensor.qty.Quantity;
import junit.framework.TestCase;

public class TensorProductTest extends TestCase {
  public void testVectors() {
    Tensor tensor = TensorProduct.of(Tensors.vector(1, 2, 3), Tensors.vector(-1, 2));
    assertEquals(Dimensions.of(tensor), Arrays.asList(3, 2));
  }

  public void testScalar() {
    Tensor s = TensorProduct.of(Quantity.of(3, "m*s"), Quantity.of(4, "s"));
    assertEquals(s, Quantity.of(12, "s^2*m"));
  }

  public void testFour1() {
    Tensor tensor = TensorProduct.of(Tensors.vector(1, 2), LieAlgebras.sl2());
    assertEquals(Dimensions.of(tensor), Arrays.asList(2, 3, 3, 3));
  }

  public void testFour2() {
    Tensor tensor = TensorProduct.of(LieAlgebras.sl2(), Tensors.vector(1, 2));
    assertEquals(Dimensions.of(tensor), Arrays.asList(3, 3, 3, 2));
  }
}
