// code by jph
package ch.ethz.idsc.tensor;

import junit.framework.TestCase;

public class GaussScalarFailTest extends TestCase {
  public void testPrime() {
    try {
      GaussScalar.of(2, 20001);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
    try {
      GaussScalar.of(2, 100101);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }

  public void testIllegalGauss() {
    Scalar a = GaussScalar.of(4, 7);
    Scalar b = GaussScalar.of(4, 11);
    try {
      a.add(b);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }

  public void testIllegal() {
    Scalar a = GaussScalar.of(4, 7);
    Scalar b = DoubleScalar.of(4.33);
    try {
      a.add(b);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
    try {
      a.multiply(b);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }

  public void testMultiplyFail() {
    try {
      GaussScalar.of(2, 7).multiply(RealScalar.of(.3));
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }

  public void testCompareFail1() {
    try {
      Scalars.compare(GaussScalar.of(2, 7), GaussScalar.of(9, 11));
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }

  public void testCompareTypeFail() {
    try {
      Scalars.compare(GaussScalar.of(2, 7), RealScalar.of(.3));
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
    try {
      Scalars.compare(RealScalar.of(.3), GaussScalar.of(2, 7));
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }

  public void testComparableFail() {
    try {
      DoubleScalar.of(3.14).compareTo(GaussScalar.of(1, 7));
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }
}
