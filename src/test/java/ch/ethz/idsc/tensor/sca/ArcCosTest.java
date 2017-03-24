// code by jph
package ch.ethz.idsc.tensor.sca;

import ch.ethz.idsc.tensor.ComplexScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Scalars;
import junit.framework.TestCase;

public class ArcCosTest extends TestCase {
  public void testReal() {
    assertEquals(ArcCos.function.apply(Scalars.fromString("1")), RealScalar.of(Math.acos(1)));
    assertEquals(ArcCos.function.apply(Scalars.fromString("-1")), RealScalar.of(Math.acos(-1)));
  }

  public void testRealOutside() {
    Scalar s = RealScalar.of(3);
    Scalar r = ArcCos.function.apply(s);
    // 1.7627471740390860505 I
    assertEquals(r, Scalars.fromString("0+1.7627471740390872*I"));
  }

  public void testRealOutsideNeg() {
    Scalar s = RealScalar.of(-3);
    Scalar r = ArcCos.function.apply(s);
    assertEquals(r, ArcCos.of(s));
    // 3.1415926535897932385 - 1.7627471740390860505 I
    assertEquals(r, Scalars.fromString("3.141592653589793-1.762747174039086*I"));
  }

  public void testComplex() {
    Scalar s = ComplexScalar.of(5, -7);
    Scalar r = ArcCos.function.apply(s);
    assertEquals(r, ArcCos.of(s));
    // 0.9537320301189030967 + 2.8462888282083865345 I
    assertEquals(r, Scalars.fromString("0.9537320301189085+2.846288828208389*I"));
  }
}
