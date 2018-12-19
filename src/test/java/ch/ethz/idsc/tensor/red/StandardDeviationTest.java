// code by jph
package ch.ethz.idsc.tensor.red;

import ch.ethz.idsc.tensor.RationalScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.sca.Sqrt;
import junit.framework.TestCase;

public class StandardDeviationTest extends TestCase {
  public void testSimple() {
    Scalar scalar = StandardDeviation.ofVector(Tensors.vector(1, 2, 6, 3, -2, 3, 10));
    assertEquals(scalar, Sqrt.of(RationalScalar.of(102, 7)));
  }
}
