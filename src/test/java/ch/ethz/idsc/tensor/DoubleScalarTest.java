// code by jph
package ch.ethz.idsc.tensor;

import ch.ethz.idsc.tensor.alg.DeleteDuplicates;
import ch.ethz.idsc.tensor.alg.Sort;
import ch.ethz.idsc.tensor.red.Max;
import ch.ethz.idsc.tensor.red.Min;
import ch.ethz.idsc.tensor.sca.Chop;
import junit.framework.TestCase;

public class DoubleScalarTest extends TestCase {
  public void testZero() {
    assertEquals(RealScalar.ZERO, DoubleScalar.of(0));
    assertFalse(DoubleScalar.of(0) instanceof RationalScalar);
  }

  public void testAdd() {
    RealScalar.ZERO.hashCode();
    Tensor a = DoubleScalar.of(1.23);
    Tensor b = DoubleScalar.of(2.3);
    assertTrue(a.add(b).equals(b.add(a)));
    Tensor c = DoubleScalar.of(1.23 + 2.3);
    assertTrue(a.add(b).equals(c));
  }

  public void testZeroReciprocal() {
    Scalar nzero = DoubleScalar.of(0.0);
    assertEquals(nzero.reciprocal(), DoubleScalar.POSITIVE_INFINITY);
    assertEquals(DoubleScalar.POSITIVE_INFINITY.reciprocal(), nzero);
  }

  public void testChop() {
    Scalar s = DoubleScalar.of(3.14);
    assertEquals(Chop._12.of(s), s);
    Scalar r = DoubleScalar.of(1e-14);
    assertEquals(Chop._12.of(r), r.zero());
    assertEquals(Chop._12.of(RealScalar.ZERO), RealScalar.ZERO);
  }

  public void testEquality() {
    assertEquals(RealScalar.ONE, DoubleScalar.of(1));
    assertEquals(DoubleScalar.of(1), RationalScalar.of(1, 1));
    assertEquals(DoubleScalar.of(1), IntegerScalar.of(1));
  }

  public void testInf() {
    Scalar inf = DoubleScalar.of(Double.POSITIVE_INFINITY);
    Scalar c = RealScalar.of(-2);
    assertEquals(inf.multiply(c), inf.negate());
    assertEquals(c.multiply(inf), inf.negate());
    Scalar nan = inf.multiply(inf.zero());
    assertTrue(Double.isNaN(nan.number().doubleValue()));
  }

  public void testMin() {
    Scalar a = RealScalar.of(3);
    Scalar b = RealScalar.of(7.2);
    assertEquals(Min.of(a, b), a);
  }

  public void testMax1() {
    Scalar a = RealScalar.of(3);
    Scalar b = RealScalar.of(7.2);
    assertEquals(Max.of(a, b), b);
  }

  public void testMax2() {
    Scalar a = RealScalar.of(0);
    Scalar b = RealScalar.of(7.2);
    assertEquals(Max.of(a, b), b);
  }

  public void testNegativeZero() {
    Scalar d1 = DoubleScalar.of(0.0);
    Scalar d2 = DoubleScalar.of(-0.0);
    assertEquals(d1.toString(), "0.0");
    assertEquals(d2.toString(), "-0.0"); // -0.0 is tolerated as value
    assertTrue(Scalars.isZero(d1));
    assertTrue(Scalars.isZero(d2));
    assertEquals(d1.subtract(d2).toString(), "0.0");
    assertEquals(d2.subtract(d1).toString(), "-0.0"); // -0.0 is tolerated as value
    assertTrue(Scalars.compare(d1, d2) == 0);
    assertTrue(d1.hashCode() == d2.hashCode());
    assertEquals(d1.hashCode(), d2.hashCode());
    assertEquals(d1.negate().toString(), "-0.0");
    assertEquals(d2.negate().toString(), "0.0");
  }

  public void testNegZeroString() {
    Scalar scalar = Scalars.fromString("-0.0");
    assertTrue(scalar instanceof DoubleScalar);
    assertEquals(scalar.toString(), "0.0");
  }

  public void testNegZeroSort() {
    Tensor vector = Tensors.vectorDouble(0.0, -0.0, -0.0, 0.0, -0.0, 0.0);
    Tensor sorted = Sort.of(vector);
    assertEquals(vector.toString(), sorted.toString());
  }

  public void testDeleteDuplicates() {
    Tensor vector = DeleteDuplicates.of(Tensors.vectorDouble(0.0, -0.0, 0.0, -0.0));
    assertEquals(vector.length(), 1);
  }

  public void testNaN() {
    try {
      DoubleScalar nan = (DoubleScalar) DoubleScalar.INDETERMINATE;
      nan.isNonNegative();
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
    try {
      DoubleScalar nan = (DoubleScalar) DoubleScalar.INDETERMINATE;
      nan.signInt();
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }

  public void testCompareFail() {
    Scalar a = RealScalar.of(7.2);
    Scalar b = GaussScalar.of(3, 5);
    try {
      Max.of(a, b);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
    try {
      Max.of(b, a);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }

  public void testValue() {
    DoubleScalar ds = (DoubleScalar) DoubleScalar.of(3.14);
    assertEquals(ds.number(), 3.14);
  }

  public void testEquals() {
    assertFalse(DoubleScalar.of(3.14).equals(null));
    assertFalse(DoubleScalar.of(3.14).equals("hello"));
    assertFalse(DoubleScalar.of(3.14).equals(ComplexScalar.of(1, 2)));
  }
}
