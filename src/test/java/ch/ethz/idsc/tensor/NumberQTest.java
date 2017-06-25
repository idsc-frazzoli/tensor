// code by jph
package ch.ethz.idsc.tensor;

import junit.framework.TestCase;

public class NumberQTest extends TestCase {
  public void testSimple() {
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
    assertFalse(NumberQ.of(RealScalar.POSITIVE_INFINITY));
    assertFalse(NumberQ.of(RealScalar.NEGATIVE_INFINITY));
    assertFalse(NumberQ.of(RealScalar.of(Double.NaN)));
  }
}
