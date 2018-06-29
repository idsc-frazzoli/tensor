// code by jph
package ch.ethz.idsc.tensor.red;

import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Scalars;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import junit.framework.TestCase;

public class KurtosisTest extends TestCase {
  public void testMathematica() {
    Tensor tensor = Tensors.vector(10, 2, 3, 4, 1);
    Scalar result = Kurtosis.of(tensor);
    assertEquals(result, Scalars.fromString("697/250")); // confirmed in mathematica
  }
}
