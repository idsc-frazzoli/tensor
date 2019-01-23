// code by jph
package ch.ethz.idsc.tensor.sca;

import ch.ethz.idsc.tensor.ComplexScalar;
import ch.ethz.idsc.tensor.DecimalScalar;
import ch.ethz.idsc.tensor.GaussScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Scalars;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.qty.Quantity;
import junit.framework.TestCase;

public class ExpTest extends TestCase {
  public void testEuler() {
    Scalar emi = Exp.of(ComplexScalar.of(0, -Math.PI));
    Scalar tru = RealScalar.of(-1);
    assertTrue(Chop._15.close(emi, tru));
  }

  public void testExpZero() {
    assertEquals(Exp.of(RealScalar.ZERO), RealScalar.ONE);
    assertEquals(Log.of(RealScalar.ONE), RealScalar.ZERO);
  }

  public void testDecimal() {
    Scalar scalar = Exp.of(DecimalScalar.of("1"));
    assertTrue(scalar instanceof DecimalScalar);
    assertTrue(scalar.toString().startsWith("2.71828182845904523536028747135266"));
  }

  public void testComplexDecimal() {
    Scalar scalar = Exp.of(ComplexScalar.of(DecimalScalar.of("1"), DecimalScalar.of("2.12")));
    assertTrue(scalar instanceof ComplexScalar);
    // mathematica gives -1.4189653368301074` + 2.3185326117622904` I
    Scalar m = Scalars.fromString("-1.4189653368301074 + 2.3185326117622904 * I");
    assertTrue(Chop._15.close(scalar, m));
  }

  public void testEmpty() {
    assertEquals(Exp.of(Tensors.empty()), Tensors.empty());
  }

  public void testFailQuantity() {
    Scalar scalar = Quantity.of(2, "m");
    try {
      Log.of(scalar);
      fail();
    } catch (Exception exception) {
      // ---
    }
  }

  public void testFail() {
    Scalar scalar = GaussScalar.of(6, 7);
    try {
      Exp.of(scalar);
      fail();
    } catch (Exception exception) {
      // ---
    }
  }
}
