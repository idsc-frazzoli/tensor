// code by jph
package ch.ethz.idsc.tensor.sca;

import ch.ethz.idsc.tensor.ComplexScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Scalars;
import junit.framework.TestCase;

public class ArcSinhTest extends TestCase {
  public void testReal() {
    Scalar value = RealScalar.of(0.88137358701954302523);
    Chop._12.requireClose(ArcSinh.of(RealScalar.ONE), value);
    Chop._12.requireClose(ArcSinh.of(RealScalar.ONE.negate()), value.negate());
    Chop._12.requireClose(ArcSinh.of(RealScalar.of(3)), RealScalar.of(1.8184464592320668235));
  }

  public void testComplex() {
    Scalar r = ArcSinh.FUNCTION.apply(ComplexScalar.of(5, -7));
    // 2.8440976626506525285 - 0.9473406443130488244 I
    Chop._12.requireClose(r, ComplexScalar.of(2.8440976626506525285, -0.9473406443130488244));
  }

  public void testArcSinh() {
    Scalar s = ComplexScalar.of(5, -7);
    Scalar r = ArcSinh.of(s);
    // 2.8441 - 0.947341 I
    Scalar a = Scalars.fromString("2.8440976626506527-0.9473406443130488*I");
    assertTrue(Statics.PRECISION.close(a, r));
    assertEquals(a, ArcSinh.of(s));
  }
}
