// code by jph
package ch.ethz.idsc.tensor;

import ch.ethz.idsc.tensor.qty.Quantity;
import junit.framework.TestCase;

public class NumberQTest extends TestCase {
  public void testRealFinite() {
    assertTrue(NumberQ.of(RealScalar.of(0.)));
    assertTrue(NumberQ.of(RealScalar.ZERO));
  }

  public void testComplex() {
    assertTrue(NumberQ.of(ComplexScalar.of(0., .3)));
    assertTrue(NumberQ.of(ComplexScalar.of(0., 2)));
  }

  public void testComplexCorner() {
    assertFalse(NumberQ.of(ComplexScalar.of(Double.POSITIVE_INFINITY, .3)));
    assertFalse(NumberQ.of(ComplexScalar.of(0., Double.NaN)));
  }

  public void testGauss() {
    assertTrue(NumberQ.of(GaussScalar.of(3, 7)));
    assertTrue(NumberQ.of(GaussScalar.of(0, 7)));
  }

  public void testTensor() {
    assertFalse(NumberQ.of(Tensors.vector(1.)));
  }

  public void testCorner() {
    assertFalse(NumberQ.of(DoubleScalar.POSITIVE_INFINITY));
    assertFalse(NumberQ.of(DoubleScalar.NEGATIVE_INFINITY));
    assertFalse(NumberQ.of(RealScalar.of(Double.NaN)));
    assertFalse(NumberQ.of(DoubleScalar.INDETERMINATE));
  }

  public void testCornerFloat() {
    assertFalse(NumberQ.of(RealScalar.of(Float.POSITIVE_INFINITY)));
    assertFalse(NumberQ.of(RealScalar.of(Float.POSITIVE_INFINITY)));
    assertFalse(NumberQ.of(RealScalar.of(Float.NaN)));
  }

  public void testQuantity() {
    assertFalse(NumberQ.of(Quantity.of(3, "m")));
    assertFalse(NumberQ.of(Quantity.of(3.14, "m")));
  }

  public void testVector() {
    assertFalse(NumberQ.of(Tensors.vector(1, 2, 3)));
  }

  public void testAll() {
    assertTrue(NumberQ.all(Tensors.fromString("{1, 3}")));
    assertFalse(NumberQ.all(Tensors.fromString("{1, 3[m]}")));
  }

  public void testRequire() {
    Scalar scalar = NumberQ.require(RealScalar.of(123.456));
    assertEquals(scalar, RealScalar.of(123.456));
  }

  public void testRequireFail() {
    try {
      NumberQ.require(Quantity.of(6, "apples"));
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }
}
