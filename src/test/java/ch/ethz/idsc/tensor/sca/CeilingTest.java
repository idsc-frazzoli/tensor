// code by jph
package ch.ethz.idsc.tensor.sca;

import ch.ethz.idsc.tensor.DoubleScalar;
import ch.ethz.idsc.tensor.RationalScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Scalars;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.ZeroScalar;
import junit.framework.TestCase;

public class CeilingTest extends TestCase {
  public void testCeiling() {
    assertEquals(Ceiling.of(ZeroScalar.get()), ZeroScalar.get());
    assertEquals(Ceiling.of(RationalScalar.of(-5, 2)), RationalScalar.of(-2, 1));
    assertEquals(Ceiling.of(RationalScalar.of(5, 2)), RationalScalar.of(3, 1));
    assertEquals(Ceiling.of(DoubleScalar.of(.123)), RealScalar.of(1));
    assertEquals(Ceiling.of(RealScalar.of(1)), RealScalar.of(1));
    assertEquals(Ceiling.of(DoubleScalar.of(-.123)), RationalScalar.of(0, 1));
  }

  public void testHash() {
    Tensor a = Tensors.of( //
        DoubleScalar.of(.123), DoubleScalar.of(3.343), DoubleScalar.of(-.123));
    Tensor b = a.map(Ceiling.function);
    Tensor c = a.map(Ceiling.function);
    assertEquals(b, c);
    assertEquals(b.hashCode(), c.hashCode());
  }

  @SuppressWarnings("cast")
  public void testGetCeiling() {
    Tensor v = Tensors.vectorDouble(3.5, 5.6, 9.12);
    Scalar s = Ceiling.function.apply(v.Get(1));
    RealScalar rs = (RealScalar) s;
    assertEquals((Integer) rs.number(), (Integer) 6);
  }

  public void testComplex() {
    Scalar c = Scalars.fromString("3-3*I");
    try {
      Ceiling.of(c);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }
}
