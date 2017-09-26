// code by jph
package ch.ethz.idsc.tensor.qty;

import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import junit.framework.TestCase;

public class QuantityMapTest extends TestCase {
  public void testSimple() {
    Tensor vector = Tensors.vector(1, 2, 3);
    Tensor nuvec = vector.map(QuantityMap.of(Unit.of("m*kg^2")));
    assertEquals(nuvec, //
        Tensors.fromString("{1[kg^2*m], 2[kg^2*m], 3[kg^2*m]}", Quantity::fromString));
  }
}
