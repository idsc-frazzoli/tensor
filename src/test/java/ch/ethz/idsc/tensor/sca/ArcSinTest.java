// code by jph
package ch.ethz.idsc.tensor.sca;

import ch.ethz.idsc.tensor.ComplexScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Scalars;
import junit.framework.TestCase;

public class ArcSinTest extends TestCase {
  public void testReal() {
    assertEquals(ArcSin.of(Scalars.fromString("1")), RealScalar.of(Math.asin(1)));
    assertEquals(ArcSin.of(Scalars.fromString("-1")), RealScalar.of(Math.asin(-1)));
  }

  public void testRealOutside() {
    Scalar s = RealScalar.of(3);
    Scalar r = ArcSin.of(s);
    // 1.5707963267948966192 - 1.7627471740390860505 I
    assertTrue(Statics.PRECISION.close(r, Scalars.fromString("1.5707963267948966-1.762747174039086*I")));
  }

  public void testRealOutsideNeg() {
    Scalar s = RealScalar.of(-3);
    Scalar r = ArcSin.FUNCTION.apply(s);
    assertEquals(r, ArcSin.of(s));
    // -1.5707963267948966192 + 1.7627471740390860505 I
    assertTrue(Statics.PRECISION.close(r, Scalars.fromString("-1.5707963267948966+1.7627471740390872*I")));
  }

  public void testComplex() {
    Scalar s = ComplexScalar.of(5, -7);
    Scalar r = ArcSin.FUNCTION.apply(s);
    assertEquals(r, ArcSin.of(s));
    // 0.617064 - 2.84629 I
    assertTrue(Statics.PRECISION.close(r, Scalars.fromString("0.6170642966759935-2.8462888282083862*I")));
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
