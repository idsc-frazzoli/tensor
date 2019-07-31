// code by jph
package ch.ethz.idsc.tensor.num;

import ch.ethz.idsc.tensor.ComplexScalar;
import ch.ethz.idsc.tensor.RationalScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensors;
import junit.framework.TestCase;

public class LCMTest extends TestCase {
  public void testZero() {
    assertEquals(LCM.of(RealScalar.ZERO, RealScalar.ZERO), RealScalar.ZERO);
    assertEquals(LCM.of(RealScalar.of(3), RealScalar.ZERO), RealScalar.ZERO);
    assertEquals(LCM.of(RealScalar.ZERO, RealScalar.of(3)), RealScalar.ZERO);
  }

  public void testExamples() {
    assertEquals(LCM.of(RealScalar.of(+123), RealScalar.of(+345)), RealScalar.of(14145));
    assertEquals(LCM.of(RealScalar.of(+123 * 5), RealScalar.of(345)), RealScalar.of(14145));
    assertEquals(LCM.of(RealScalar.of(-123), RealScalar.of(+345)), RealScalar.of(14145));
    assertEquals(LCM.of(RealScalar.of(+123), RealScalar.of(-345)), RealScalar.of(14145));
    assertEquals(LCM.of(RealScalar.of(-123), RealScalar.of(-345)), RealScalar.of(14145));
  }

  public void testReduce() {
    Scalar scalar = Tensors.vector(13 * 700, 64 * 7, 4 * 7 * 13).stream() //
        .map(Scalar.class::cast) //
        .reduce(LCM::of).get();
    assertEquals(scalar.toString(), "145600");
  }

  public void testRational() {
    Scalar scalar = LCM.of(RationalScalar.of(3, 2), RationalScalar.of(2, 1));
    assertEquals(scalar, RealScalar.of(6)); // Mathematica gives 6
  }

  public void testComplex() {
    Scalar scalar = LCM.of(ComplexScalar.of(2, 1), ComplexScalar.of(3, 1));
    assertEquals(scalar, ComplexScalar.of(5, -5)); // Mathematica gives 5 + 5 I
  }
}
