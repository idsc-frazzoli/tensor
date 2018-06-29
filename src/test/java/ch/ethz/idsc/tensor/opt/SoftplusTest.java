// code by jph
package ch.ethz.idsc.tensor.opt;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import junit.framework.TestCase;

public class SoftplusTest extends TestCase {
  public void testZero() {
    Scalar s = Softplus.FUNCTION.apply(RealScalar.ZERO);
    assertEquals(s, RealScalar.of(0.6931471805599453));
  }

  public void testOuter() {
    assertEquals(Softplus.FUNCTION.apply(RealScalar.of(+1000000)), RealScalar.of(1000000));
    assertEquals(Softplus.FUNCTION.apply(RealScalar.of(-1000000)), RealScalar.of(0));
  }

  public void testTensor() {
    Scalar s0 = Softplus.FUNCTION.apply(RealScalar.ZERO);
    Scalar s1 = Softplus.FUNCTION.apply(RealScalar.ONE);
    Tensor tensor = Softplus.of(Tensors.vector(0, 1));
    assertEquals(tensor, Tensors.of(s0, s1));
  }
}
