// code by jph
package ch.ethz.idsc.tensor.num;

import ch.ethz.idsc.tensor.DoubleScalar;
import ch.ethz.idsc.tensor.RationalScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Scalars;
import ch.ethz.idsc.tensor.sca.Power;
import junit.framework.TestCase;

public class GaussScalarFailTest extends TestCase {
  public void testPrimeNegative() {
    try {
      GaussScalar.of(2, -7);
      fail();
    } catch (Exception exception) {
      // ---
    }
  }

  public void testPrime() {
    try {
      GaussScalar.of(2, 20001);
      fail();
    } catch (Exception exception) {
      // ---
    }
    try {
      GaussScalar.of(2, 100101);
      fail();
    } catch (Exception exception) {
      // ---
    }
  }

  public void testIllegalGauss() {
    Scalar a = GaussScalar.of(4, 7);
    Scalar b = GaussScalar.of(4, 11);
    try {
      a.add(b);
      fail();
    } catch (Exception exception) {
      // ---
    }
  }

  public void testIllegal() {
    Scalar a = GaussScalar.of(4, 7);
    Scalar b = DoubleScalar.of(4.33);
    try {
      a.add(b);
      fail();
    } catch (Exception exception) {
      // ---
    }
    try {
      a.multiply(b);
      fail();
    } catch (Exception exception) {
      // ---
    }
  }

  public void testMultiplyFail() {
    try {
      GaussScalar.of(2, 7).multiply(RealScalar.of(.3));
      fail();
    } catch (Exception exception) {
      // ---
    }
  }

  public void testPowerFail() {
    Scalar scalar = GaussScalar.of(2, 7);
    try {
      Power.of(scalar, 2.3);
      fail();
    } catch (Exception exception) {
      // ---
    }
    try {
      Power.of(scalar, RationalScalar.of(2, 3));
      fail();
    } catch (Exception exception) {
      // ---
    }
  }

  public void testCompareFail1() {
    try {
      Scalars.compare(GaussScalar.of(2, 7), GaussScalar.of(9, 11));
      fail();
    } catch (Exception exception) {
      // ---
    }
  }

  public void testCompareTypeFail() {
    try {
      Scalars.compare(GaussScalar.of(2, 7), RealScalar.of(.3));
      fail();
    } catch (Exception exception) {
      // ---
    }
    try {
      Scalars.compare(RealScalar.of(.3), GaussScalar.of(2, 7));
      fail();
    } catch (Exception exception) {
      // ---
    }
  }

  public void testComparableFail() {
    try {
      DoubleScalar.of(3.14).compareTo(GaussScalar.of(1, 7));
      fail();
    } catch (Exception exception) {
      // ---
    }
  }
}
