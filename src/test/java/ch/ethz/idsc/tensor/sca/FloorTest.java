// code by jph
package ch.ethz.idsc.tensor.sca;

import ch.ethz.idsc.tensor.DoubleScalar;
import ch.ethz.idsc.tensor.RationalScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Scalars;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.io.StringScalar;
import ch.ethz.idsc.tensor.qty.Quantity;
import junit.framework.TestCase;

public class FloorTest extends TestCase {
  public void testFloor() {
    assertEquals(Floor.of(RealScalar.ZERO), RealScalar.ZERO);
    assertEquals(Floor.of(RationalScalar.of(-5, 2)), RationalScalar.of(-3, 1));
    assertEquals(Floor.of(RationalScalar.of(5, 2)), RationalScalar.of(2, 1));
    assertEquals(Floor.of(DoubleScalar.of(.123)), RealScalar.ZERO);
    assertEquals(Floor.of(RealScalar.ONE), RealScalar.ONE);
    assertEquals(Floor.of(DoubleScalar.of(-.123)), RationalScalar.of(-1, 1));
  }

  public void testHash() {
    Tensor a = Tensors.of( //
        DoubleScalar.of(.123), DoubleScalar.of(3.343), DoubleScalar.of(-.123));
    Tensor b = a.map(Floor.FUNCTION);
    Tensor c = a.map(Floor.FUNCTION);
    assertEquals(b, c);
    assertEquals(b.hashCode(), c.hashCode());
  }

  public void testGetFloor() {
    Tensor v = Tensors.vectorDouble(3.5, 5.6, 9.12);
    Scalar s = Floor.of(v.Get(1));
    RealScalar rs = (RealScalar) s;
    assertEquals(rs.number().doubleValue(), 5.0);
  }

  public void testLarge() {
    Scalar scalar = DoubleScalar.of(1e30);
    Scalar r = Round.of(scalar);
    Scalar f = Floor.of(scalar);
    assertEquals(r, f);
    assertEquals(r.toString(), "1000000000000000000000000000000");
  }

  public void testRational1() {
    Scalar s = RationalScalar.of(234534584545L, 13423656767L); // 17.4717
    Scalar r = Floor.of(s);
    assertEquals(r, RealScalar.of(17));
    assertTrue(r instanceof RationalScalar);
  }

  public void testRational2() {
    Scalar s = RationalScalar.of(734534584545L, 13423656767L); // 54.7194
    Scalar r = Floor.of(s);
    assertEquals(r, RealScalar.of(54));
    assertTrue(r instanceof RationalScalar);
  }

  public void testComplex() {
    Scalar c = Scalars.fromString("7-2*I");
    assertEquals(Floor.of(c), c);
    Scalar d = Scalars.fromString("7.9-1.1*I");
    assertEquals(Floor.of(d), c);
  }

  public void testQuantity() {
    Scalar scalar = Quantity.of(210.9, "K");
    assertEquals(Floor.FUNCTION.apply(scalar), Quantity.of(210, "K"));
  }

  public void testMultiple() {
    Scalar w = Quantity.of(2, "K");
    ScalarUnaryOperator suo = Floor.toMultipleOf(w);
    assertEquals(suo.apply(Quantity.of(3.9, "K")), w);
    assertEquals(suo.apply(Quantity.of(-2, "K")), w.negate());
    assertEquals(suo.apply(Quantity.of(-2.1, "K")), w.multiply(RealScalar.of(-2)));
    assertEquals(suo.apply(Quantity.of(-3.9, "K")), w.multiply(RealScalar.of(-2)));
  }

  public void testPositiveInfinity() {
    Scalar scalar = DoubleScalar.POSITIVE_INFINITY;
    assertEquals(Floor.FUNCTION.apply(scalar), scalar);
  }

  public void testNegativeInfinity() {
    Scalar scalar = DoubleScalar.NEGATIVE_INFINITY;
    assertEquals(Floor.FUNCTION.apply(scalar), scalar);
  }

  public void testNaN() {
    Scalar scalar = Floor.FUNCTION.apply(DoubleScalar.INDETERMINATE);
    assertTrue(Double.isNaN(scalar.number().doubleValue()));
  }

  public void testTypeFail() {
    try {
      Floor.of(StringScalar.of("some"));
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }
}
