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

public class FloorTest extends TestCase {
  public void testFloor() {
    assertEquals(Floor.of(ZeroScalar.get()), ZeroScalar.get());
    assertEquals(Floor.of(RationalScalar.of(-5, 2)), RationalScalar.of(-3, 1));
    assertEquals(Floor.of(RationalScalar.of(5, 2)), RationalScalar.of(2, 1));
    assertEquals(Floor.of(DoubleScalar.of(.123)), ZeroScalar.get());
    assertEquals(Floor.of(RealScalar.ONE), RealScalar.ONE);
    assertEquals(Floor.of(DoubleScalar.of(-.123)), RationalScalar.of(-1, 1));
  }

  public void testHash() {
    Tensor a = Tensors.of( //
        DoubleScalar.of(.123), DoubleScalar.of(3.343), DoubleScalar.of(-.123));
    Tensor b = a.map(Floor.function);
    Tensor c = a.map(Floor.function);
    assertEquals(b, c);
    assertEquals(b.hashCode(), c.hashCode());
  }

  public void testGetFloor() {
    Tensor v = Tensors.vectorDouble(3.5, 5.6, 9.12);
    Scalar s = Floor.function.apply(v.Get(1));
    RealScalar rs = (RealScalar) s;
    assertEquals(rs.number().doubleValue(), 5.0);
  }

  public void testLarge() {
    Scalar scalar = DoubleScalar.of(1e30);
    Scalar r = Round.function.apply(scalar);
    Scalar f = Floor.function.apply(scalar);
    assertEquals(r, f);
    assertEquals(r.toString(), "1000000000000000000000000000000");
  }

  public void testComplex() {
    Scalar c = Scalars.fromString("3-3*I");
    try {
      Floor.of(c);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }
}
