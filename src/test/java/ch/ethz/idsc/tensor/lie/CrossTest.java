// code by jph
package ch.ethz.idsc.tensor.lie;

import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.alg.UnitVector;
import junit.framework.TestCase;

public class CrossTest extends TestCase {
  public void testUnits() {
    Tensor v1 = UnitVector.of(0, 3);
    Tensor v2 = UnitVector.of(1, 3);
    Tensor v3 = UnitVector.of(2, 3);
    assertEquals(Cross.of(v1, v2), v3);
    assertEquals(Cross.of(v2, v3), v1);
    assertEquals(Cross.of(v3, v1), v2);
  }

  public static void main(String[] args) {
    Tensor ad = LieAlgebras.so3();
    Tensor x = Tensors.vector(7, 2, -4);
    Tensor y = Tensors.vector(-3, 5, 2);
    System.out.println(ad);
    System.out.println(ad.dot(x).dot(y));
  }
}
