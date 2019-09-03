// code by jph
package ch.ethz.idsc.tensor.sca;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.io.StringScalar;
import ch.ethz.idsc.tensor.num.GaussScalar;
import ch.ethz.idsc.tensor.qty.Quantity;
import ch.ethz.idsc.tensor.qty.Quaternion;
import junit.framework.TestCase;

public class UnitStepTest extends TestCase {
  public void testRealScalar() {
    assertEquals(UnitStep.of(RealScalar.of(-0.3)), RealScalar.ZERO);
    assertEquals(UnitStep.of(RealScalar.of(0)), RealScalar.ONE);
    assertEquals(UnitStep.of(RealScalar.of(0.134)), RealScalar.ONE);
  }

  public void testPredicateQuantity() {
    assertEquals(UnitStep.of(Quantity.of(-0.3, "m")), RealScalar.ZERO);
    assertEquals(UnitStep.of(Quantity.of(0.0, "m")), RealScalar.ONE);
    assertEquals(UnitStep.of(Quantity.of(0, "m")), RealScalar.ONE);
    assertEquals(UnitStep.of(Quantity.of(1, "m")), RealScalar.ONE);
  }

  public void testGaussScalar() {
    assertEquals(UnitStep.FUNCTION.apply(GaussScalar.of(2, 7)), RealScalar.ONE);
  }

  public void testQuaternionFail() {
    Scalar scalar = Quaternion.of(RealScalar.of(-4), Tensors.vector(1, 2, 3));
    try {
      UnitStep.FUNCTION.apply(scalar);
      fail();
    } catch (Exception exception) {
      // ---
    }
  }

  public void testStringFail() {
    Scalar scalar = StringScalar.of("abc");
    try {
      UnitStep.FUNCTION.apply(scalar);
      fail();
    } catch (Exception exception) {
      // ---
    }
  }
}
