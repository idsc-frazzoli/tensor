// code by jph
package ch.ethz.idsc.tensor.sca;

import ch.ethz.idsc.tensor.DecimalScalar;
import ch.ethz.idsc.tensor.DoubleScalar;
import ch.ethz.idsc.tensor.RationalScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Scalars;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.qty.Quantity;
import junit.framework.TestCase;

public class ChopTest extends TestCase {
  public void testChop() {
    Tensor v = Tensors.vectorDouble(1e-10, 1e-12, 1e-14, 1e-16);
    Tensor c = v.map(Chop._12);
    assertFalse(c.get(0).equals(RealScalar.ZERO));
    assertFalse(c.get(1).equals(RealScalar.ZERO));
    assertTrue(c.get(2).equals(RealScalar.ZERO));
    assertTrue(c.get(3).equals(RealScalar.ZERO));
  }

  public void testCustom() {
    Chop chop = Chop.below(3.142);
    assertTrue(chop.close(DoubleScalar.of(Math.PI), RealScalar.ZERO));
    assertFalse(chop.close(DoubleScalar.of(3.15), RealScalar.ZERO));
  }

  public void testExclusive() {
    assertFalse(Chop._12.allZero(RealScalar.of(Chop._12.threshold())));
  }

  public void testQuantity() {
    Scalar qs1 = Quantity.of(1e-9, "kg");
    Scalar act = Quantity.of(0, "kg");
    assertEquals(Chop._07.of(qs1), act);
    assertEquals(Chop._10.of(qs1), qs1);
  }

  public void testFail() {
    try {
      Chop.below(-1e-9);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }

  public void testComplex() {
    assertTrue(Chop._05.close( //
        Scalars.fromString("1.2+3.1*I"), //
        Scalars.fromString("1.2+3.1000006*I")));
  }

  public void testNaN() {
    Scalar scalar = Chop._05.apply(DoubleScalar.INDETERMINATE);
    assertTrue(scalar instanceof DoubleScalar);
    assertTrue(Double.isNaN(scalar.number().doubleValue()));
  }

  public void testInf() {
    Scalar scalar = Chop._05.apply(DoubleScalar.NEGATIVE_INFINITY);
    assertTrue(scalar instanceof DoubleScalar);
    assertTrue(Double.isInfinite(scalar.number().doubleValue()));
  }

  public void testClose() {
    Scalar s1 = DoubleScalar.of(1);
    Scalar s2 = DoubleScalar.of(1 + 1e-10);
    assertTrue(Chop._07.close(s1, s2));
    assertTrue(Chop._09.close(s1, s2));
    assertFalse(Chop._10.close(s1, s2));
    assertFalse(Chop._12.close(s1, s2));
  }

  public void testCloseExact() {
    Scalar s1 = RationalScalar.of(1, 10000000);
    Scalar s2 = RationalScalar.of(2, 10000000);
    assertFalse(Chop._05.close(s1, s2));
    assertTrue(Chop._05.close(N.DOUBLE.apply(s1), N.DOUBLE.apply(s2)));
    Scalar s3 = RationalScalar.of(1, 10000000);
    assertTrue(Chop._05.close(s1, s3));
  }

  public void testDecimal() {
    Scalar scalar = DecimalScalar.of("0.0000001");
    assertTrue(Chop._05.allZero(scalar));
    assertFalse(Chop._10.allZero(scalar));
  }

  public void testCloseFail() {
    try {
      Chop._05.close(Tensors.vector(1), Tensors.vector(1, 1));
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }
}
