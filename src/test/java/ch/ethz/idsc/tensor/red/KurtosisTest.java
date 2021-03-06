// code by jph
package ch.ethz.idsc.tensor.red;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Scalars;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.mat.HilbertMatrix;
import junit.framework.TestCase;

public class KurtosisTest extends TestCase {
  public void testMathematica() {
    Tensor tensor = Tensors.vector(10, 2, 3, 4, 1);
    Scalar result = Kurtosis.of(tensor);
    assertEquals(result, Scalars.fromString("697/250")); // confirmed in mathematica
  }

  public void testFailScalar() {
    try {
      Kurtosis.of(RealScalar.ONE);
      fail();
    } catch (Exception exception) {
      // ---
    }
  }

  public void testFailMatrix() {
    try {
      Kurtosis.of(HilbertMatrix.of(3));
      fail();
    } catch (Exception exception) {
      // ---
    }
  }
}
