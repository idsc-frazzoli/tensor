// code by jph
package ch.ethz.idsc.tensor;

import java.util.Collections;

import ch.ethz.idsc.tensor.alg.Dimensions;
import junit.framework.TestCase;

public class ScalarTest extends TestCase {
  public void testIsScalar() {
    assertTrue(ScalarQ.of(DoubleScalar.POSITIVE_INFINITY));
  }

  public void testLengthNegative() {
    assertTrue(Scalar.LENGTH < 0);
  }

  public void testGet() {
    Tensor t = RealScalar.of(3);
    Scalar s = t.Get();
    assertEquals(t, s);
  }

  public void testUnmodifiable() {
    Scalar s = RealScalar.of(3);
    assertEquals(s.unmodifiable(), s);
  }

  public void testNumber() {
    Scalar zero = RealScalar.ZERO;
    assertEquals(zero.number().getClass(), Integer.class);
    long asd = (Integer) zero.number();
    assertEquals(Double.valueOf(-1.9).intValue(), -1 + asd);
    assertEquals(Double.valueOf(1.9).intValue(), 1);
  }

  public void testDimensions() {
    Scalar a = DoubleScalar.of(3);
    assertEquals(Dimensions.of(a), Collections.emptyList());
    Scalar b = GaussScalar.of(3, 7);
    assertEquals(Dimensions.of(b), Collections.emptyList());
  }

  public void testFails() {
    Scalar a = DoubleScalar.of(3);
    Scalar b = DoubleScalar.of(5);
    try {
      a.dot(b);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }

  public void testNumber2() {
    Scalar a = DoubleScalar.of(3);
    Scalar b = DoubleScalar.of(5);
    Number na = a.number();
    Number nb = b.number();
    Scalar c = a.add(b);
    Scalar d = DoubleScalar.of(na.doubleValue() + nb.doubleValue());
    assertEquals(c, d);
  }

  public void testEquals() {
    assertFalse(Tensors.empty().equals(null));
    assertFalse(RealScalar.ZERO.equals(null));
    assertFalse(DoubleScalar.of(.3).equals(null));
    assertFalse(RationalScalar.of(5, 3).equals(null));
    assertFalse(ComplexScalar.of(RationalScalar.of(5, 3), RationalScalar.of(5, 3)).equals(null));
    assertFalse(Integer.valueOf(1233).equals(null));
  }

  @SuppressWarnings("unused")
  public void testIteratorFail() {
    try {
      for (Tensor entry : RealScalar.ZERO) {
        // ---
      }
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }
}
