// code by jph
package ch.ethz.idsc.tensor.alg;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.ZeroScalar;
import ch.ethz.idsc.tensor.red.Norm;
import ch.ethz.idsc.tensor.sca.Chop;
import junit.framework.TestCase;

public class NormalizeTest extends TestCase {
  public void testVector1() {
    Tensor vector = Tensors.vector(3, 3, 3, 3);
    Tensor n = Normalize.of(vector);
    assertEquals(n.toString(), "{1/2, 1/2, 1/2, 1/2}");
  }

  public void testVector2() {
    Tensor vector = Tensors.vector(3, 2, 1);
    Tensor n = Normalize.of(vector);
    Scalar res = Chop.function.apply(Norm._2.of(n).subtract(RealScalar.ONE));
    assertEquals(res, ZeroScalar.get());
  }

  public void testEmpty() {
    try {
      Normalize.of(Tensors.empty());
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }

  public void testZeros() {
    try {
      Normalize.of(Array.zeros(10));
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }
}
