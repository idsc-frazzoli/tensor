// code by jph
package ch.ethz.idsc.tensor.sca;

import ch.ethz.idsc.tensor.ComplexScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Scalars;
import junit.framework.TestCase;

public class ArcCosTest extends TestCase {
  public void testReal() {
    assertEquals(ArcCos.of(Scalars.fromString("1")), RealScalar.of(Math.acos(1)));
    assertEquals(ArcCos.of(Scalars.fromString("-1")), RealScalar.of(Math.acos(-1)));
  }

  public void testRealOutside() {
    Scalar s = RealScalar.of(3);
    Scalar r = ArcCos.of(s);
    // 1.7627471740390860505 I
    assertEquals(r, Scalars.fromString("0+1.7627471740390872*I"));
  }

  public void testRealOutsideNeg() {
    Scalar s = RealScalar.of(-3);
    Scalar r = ArcCos.of(s);
    assertEquals(r, ArcCos.of(s));
    // 3.1415926535897932385 - 1.7627471740390860505 I
    assertTrue(Statics.PRECISION.close(r, Scalars.fromString("3.141592653589793-1.762747174039086*I")));
  }

  public void testComplex() {
    Scalar s = ComplexScalar.of(5, -7);
    Scalar r = ArcCos.of(s);
    assertEquals(r, ArcCos.of(s));
    // num/(double)den double conversion:
    // 0.9537320301189085............. + 2.846288828208389
    // Mathematica:
    // 0.95373203011890309673440616093 + 2.84628882820838653446176723296 I
    // bigDecimal double conversion:
    // 0.9537320301188659............. + 2.846288828208396
    // _14 is insufficient on aarch64
    // aarch64: 0.9537320301188748+2.8462888282083836*I
    // x86_64 : 0.9537320301188659+2.846288828208396*I
    assertTrue(Chop._12.close(r, Scalars.fromString("0.9537320301188659+2.846288828208396*I")));
  }

  public void testArcCosh() {
    Scalar s = ComplexScalar.of(5, -7);
    Scalar r = ArcCosh.of(s);
    // 2.84629 - 0.953732 I
    Scalar a = Scalars.fromString("2.8462888282083862-0.9537320301189031*I");
    assertEquals(a, r);
    assertEquals(a, ArcCosh.of(s));
  }
}
