// code by jph
package ch.ethz.idsc.tensor.sca;

import java.util.function.Function;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.ZeroScalar;
import junit.framework.TestCase;

public class ChopTest extends TestCase {
  public void testChop() {
    Tensor v = Tensors.vectorDouble(1e-10, 1e-14, 1e-16);
    Tensor c = v.map(Chop.function);
    assertFalse(c.get(0).equals(ZeroScalar.get()));
    assertTrue(c.get(1).equals(ZeroScalar.get()));
    assertTrue(c.get(2).equals(ZeroScalar.get()));
  }

  public void testExclusive() {
    assertFalse(Chop.isZeros(RealScalar.of(Chop.THRESHOLD)));
  }

  public void testChopOrInvert() {
    Function<Scalar, Scalar> func = Chop.orInvert(.5);
    assertEquals(func.apply(RealScalar.of(-.5)), RealScalar.of(-2));
    assertEquals(func.apply(RealScalar.of(-.4)), RealScalar.of(0));
  }
}
