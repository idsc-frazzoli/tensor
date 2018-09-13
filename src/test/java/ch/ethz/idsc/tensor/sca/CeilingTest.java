// code by jph
package ch.ethz.idsc.tensor.sca;

import java.math.BigInteger;

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

public class CeilingTest extends TestCase {
  public void testCeiling() {
    assertEquals(Ceiling.of(RealScalar.ZERO), RealScalar.ZERO);
    assertEquals(Ceiling.of(RationalScalar.of(-5, 2)), RationalScalar.of(-2, 1));
    assertEquals(Ceiling.of(RationalScalar.of(5, 2)), RationalScalar.of(3, 1));
    assertEquals(Ceiling.of(DoubleScalar.of(.123)), RealScalar.ONE);
    assertEquals(Ceiling.of(RealScalar.ONE), RealScalar.ONE);
    assertEquals(Ceiling.of(DoubleScalar.of(-.123)), RationalScalar.of(0, 1));
  }

  public void testHash() {
    Tensor a = Tensors.of( //
        DoubleScalar.of(.123), DoubleScalar.of(3.343), DoubleScalar.of(-.123));
    Tensor b = a.map(Ceiling.FUNCTION);
    Tensor c = a.map(Ceiling.FUNCTION);
    assertEquals(b, c);
    assertEquals(b.hashCode(), c.hashCode());
  }

  public void testGetCeiling() {
    Tensor v = Tensors.vectorDouble(3.5, 5.6, 9.12);
    Scalar s = Ceiling.of(v.Get(1));
    RealScalar rs = (RealScalar) s;
    assertEquals(rs.number(), 6);
  }

  public void testComplex() {
    Scalar c = Scalars.fromString("7-2*I");
    assertEquals(Ceiling.of(c), c);
    Scalar d = Scalars.fromString("6.1-2.1*I");
    assertEquals(Ceiling.of(d), c);
  }

  public void testRational1() {
    Scalar s = RationalScalar.of(234534584545L, 13423656767L); // 17.4717
    Scalar r = Ceiling.of(s);
    assertEquals(r, RealScalar.of(18));
    assertTrue(r instanceof RationalScalar);
  }

  public void testRational2() {
    Scalar s = RationalScalar.of(734534584545L, 13423656767L); // 54.7194
    Scalar r = Ceiling.of(s);
    assertEquals(r, RealScalar.of(55));
    assertTrue(r instanceof RationalScalar);
  }

  public void testLarge() {
    BigInteger bi = new BigInteger("97826349587623498756234545976");
    Scalar s = RealScalar.of(bi);
    Scalar r = Ceiling.of(s);
    assertEquals(s, r);
    assertTrue(r instanceof RationalScalar);
  }

  public void testMultiple() {
    Scalar w = Quantity.of(2, "K");
    ScalarUnaryOperator suo = Ceiling.toMultipleOf(w);
    assertEquals(suo.apply(Quantity.of(3.9, "K")), w.multiply(RealScalar.of(2)));
    assertEquals(suo.apply(Quantity.of(-2, "K")), w.negate());
    assertEquals(suo.apply(Quantity.of(-2.1, "K")), w.multiply(RealScalar.of(-1)));
    assertEquals(suo.apply(Quantity.of(-3.9, "K")), w.multiply(RealScalar.of(-1)));
  }

  public void testPositiveInfinity() {
    Scalar scalar = DoubleScalar.POSITIVE_INFINITY;
    assertEquals(Ceiling.of(scalar), scalar);
  }

  public void testNegativeInfinity() {
    Scalar scalar = DoubleScalar.NEGATIVE_INFINITY;
    assertEquals(Ceiling.of(scalar), scalar);
  }

  public void testNaN() {
    Scalar scalar = Ceiling.of(DoubleScalar.INDETERMINATE);
    assertTrue(Double.isNaN(scalar.number().doubleValue()));
  }

  public void testQuantity() {
    Scalar scalar = Quantity.of(2.1, "K");
    assertEquals(Ceiling.FUNCTION.apply(scalar), Quantity.of(3, "K"));
  }

  public void testTypeFail() {
    Scalar scalar = StringScalar.of("some");
    try {
      Ceiling.of(scalar);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }
}
