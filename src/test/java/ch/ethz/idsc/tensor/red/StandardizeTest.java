// code by jph
package ch.ethz.idsc.tensor.red;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.sca.Chop;
import junit.framework.TestCase;

public class StandardizeTest extends TestCase {
  public void testSimple() {
    Tensor res = Standardize.of(Tensors.vector(6.5, 3.8, 6.6, 5.7, 6.0, 6.4, 5.3));
    assertTrue(Chop.isZeros(Mean.of(res)));
    assertTrue(Chop.isZeros(Variance.ofVector(res).subtract(RealScalar.ONE)));
  }
}
