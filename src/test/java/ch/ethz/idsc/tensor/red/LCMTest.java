// code by jph
package ch.ethz.idsc.tensor.red;

import ch.ethz.idsc.tensor.ComplexScalar;
import ch.ethz.idsc.tensor.RationalScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensors;
import junit.framework.TestCase;

public class LCMTest extends TestCase {
  public void testZero() {
    assertEquals(LCM.BIFUNCTION.apply(RealScalar.ZERO, RealScalar.ZERO), RealScalar.ZERO);
    assertEquals(LCM.BIFUNCTION.apply(RealScalar.of(3), RealScalar.ZERO), RealScalar.ZERO);
    assertEquals(LCM.BIFUNCTION.apply(RealScalar.ZERO, RealScalar.of(3)), RealScalar.ZERO);
  }

  public void testExamples() {
    assertEquals(LCM.BIFUNCTION.apply(RealScalar.of(+123), RealScalar.of(+345)), RealScalar.of(14145));
    assertEquals(LCM.BIFUNCTION.apply(RealScalar.of(+123 * 5), RealScalar.of(345)), RealScalar.of(14145));
    assertEquals(LCM.BIFUNCTION.apply(RealScalar.of(-123), RealScalar.of(+345)), RealScalar.of(14145));
    assertEquals(LCM.BIFUNCTION.apply(RealScalar.of(+123), RealScalar.of(-345)), RealScalar.of(14145));
    assertEquals(LCM.BIFUNCTION.apply(RealScalar.of(-123), RealScalar.of(-345)), RealScalar.of(14145));
  }

  public void testReduce() {
    Scalar scalar = Tensors.vector(13 * 700, 64 * 7, 4 * 7 * 13).stream() //
        .map(Scalar.class::cast) //
        .reduce(LCM.BIFUNCTION).get();
    assertEquals(scalar.toString(), "145600");
  }

  public void testRational() {
    Scalar scalar = LCM.BIFUNCTION.apply(RationalScalar.of(3, 2), RationalScalar.of(2, 1));
    assertEquals(scalar, RealScalar.of(6)); // Mathematica gives 6
  }

  public void testComplex() {
    Scalar scalar = LCM.BIFUNCTION.apply(ComplexScalar.of(2, 1), ComplexScalar.of(3, 1));
    assertEquals(scalar, ComplexScalar.of(5, -5)); // Mathematica gives 5 + 5 I
  }
}
