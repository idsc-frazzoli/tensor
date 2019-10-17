// code by jph
package ch.ethz.idsc.tensor.sca;

import ch.ethz.idsc.tensor.ExactTensorQ;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Scalars;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.io.StringScalar;
import ch.ethz.idsc.tensor.num.GaussScalar;
import junit.framework.TestCase;

public class AbsSquaredTest extends TestCase {
  public void testQuantity() {
    Scalar qs1 = Scalars.fromString("3+4*I[s^2*m^-1]");
    Scalar qs2 = AbsSquared.FUNCTION.apply(qs1);
    assertEquals(qs2.toString(), "25[m^-2*s^4]");
  }

  public void testTensor() {
    Tensor qs1 = Tensors.fromString("{3+4*I[s^2*m^-1]}");
    Tensor qs2 = AbsSquared.of(qs1);
    ExactTensorQ.require(qs2);
    assertEquals(qs2.toString(), "{25[m^-2*s^4]}");
  }

  public void testBetween() {
    assertEquals(AbsSquared.between(RealScalar.of(101), RealScalar.of(103)), RealScalar.of(4));
    assertEquals(AbsSquared.between(RealScalar.of(104), RealScalar.of(101)), RealScalar.of(9));
  }

  public void testNonConjugate() {
    GaussScalar a = GaussScalar.of(3, 12347);
    GaussScalar b = GaussScalar.of(3962, 12347);
    Scalar scalar = AbsSquared.between(a, b);
    assertEquals(scalar, GaussScalar.of(5338, 12347));
    assertEquals(scalar, GaussScalar.of(3959 * 3959, 12347));
  }

  public void testFail() {
    try {
      AbsSquared.FUNCTION.apply(StringScalar.of("idsc"));
      fail();
    } catch (Exception exception) {
      // ---
    }
  }

  public void testFailNull() {
    try {
      AbsSquared.FUNCTION.apply(null);
      fail();
    } catch (Exception exception) {
      // ---
    }
  }
}
