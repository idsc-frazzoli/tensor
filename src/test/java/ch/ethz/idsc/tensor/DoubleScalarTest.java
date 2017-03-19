// code by jph
package ch.ethz.idsc.tensor;

import ch.ethz.idsc.tensor.sca.Chop;
import junit.framework.TestCase;

public class DoubleScalarTest extends TestCase {
  public void testAdd() {
    ZeroScalar.get().hashCode();
    Tensor a = DoubleScalar.of(1.23);
    Tensor b = DoubleScalar.of(2.3);
    assertTrue(a.add(b).equals(b.add(a)));
    Tensor c = DoubleScalar.of(1.23 + 2.3);
    assertTrue(a.add(b).equals(c));
  }

  public void testChop() {
    {
      Scalar s = DoubleScalar.of(3.14);
      assertEquals(Chop.of(s), s);
    }
    {
      Scalar s = DoubleScalar.of(1e-14);
      assertEquals(Chop.of(s), ZeroScalar.get());
      assertEquals(Chop.of(ZeroScalar.get()), ZeroScalar.get());
    }
  }

  public void testEquality() {
    assertEquals(RealScalar.of(1), DoubleScalar.of(1));
    assertEquals(DoubleScalar.of(1), RationalScalar.of(1, 1));
  }
}
