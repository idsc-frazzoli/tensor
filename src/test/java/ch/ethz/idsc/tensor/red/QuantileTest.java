// code by jph
package ch.ethz.idsc.tensor.red;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import junit.framework.TestCase;

public class QuantileTest extends TestCase {
  public void testMultiple() {
    Tensor vector = Tensors.vectorInt(0, 1, 2, 3, 4);
    Tensor q = Quantile.of(vector, Tensors.fromString("[0,1/5,2/5,3/5,4/5,1]"));
    Tensor r = Tensors.fromString("[0, 0, 1, 2, 3, 4]");
    assertEquals(q, r);
  }

  public void testScalar() {
    Tensor vector = Tensors.vectorInt(0, 1, 2, 3, 4);
    Tensor q = Quantile.of(vector, RealScalar.of(.71233));
    Tensor r = Tensors.fromString("[0, 0, 1, 2, 3, 4]");
    // System.out.println(q);
    assertEquals(q, RealScalar.of(3));
  }
}
