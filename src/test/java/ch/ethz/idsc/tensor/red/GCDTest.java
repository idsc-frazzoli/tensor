// code by jph
package ch.ethz.idsc.tensor.red;

import ch.ethz.idsc.tensor.ComplexScalar;
import ch.ethz.idsc.tensor.RationalScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensors;
import junit.framework.TestCase;

public class GCDTest extends TestCase {
  public void testExamples() {
    assertEquals(GCD.BIFUNCTION.apply(RealScalar.of(+90), RealScalar.of(+60)), RealScalar.of(30));
    assertEquals(GCD.BIFUNCTION.apply(RealScalar.of(+90), RealScalar.of(-60)), RealScalar.of(30));
    assertEquals(GCD.BIFUNCTION.apply(RealScalar.of(-90), RealScalar.of(-60)), RealScalar.of(30));
    assertEquals(GCD.BIFUNCTION.apply(RealScalar.of(-90), RealScalar.of(+60)), RealScalar.of(30));
  }

  public void testZero() {
    assertEquals(GCD.BIFUNCTION.apply(RealScalar.of(0), RealScalar.of(+60)), RealScalar.of(60));
    assertEquals(GCD.BIFUNCTION.apply(RealScalar.of(+60), RealScalar.of(0)), RealScalar.of(60));
    assertEquals(GCD.BIFUNCTION.apply(RealScalar.of(0), RealScalar.of(-60)), RealScalar.of(60));
    assertEquals(GCD.BIFUNCTION.apply(RealScalar.of(-60), RealScalar.of(0)), RealScalar.of(60));
    assertEquals(GCD.BIFUNCTION.apply(RealScalar.of(0), RealScalar.of(0)), RealScalar.of(0));
  }

  public void testReduce() {
    Scalar scalar = Tensors.vector(13 * 700, 64 * 7, 4 * 7 * 13).stream() //
        .map(Scalar.class::cast) //
        .reduce(GCD.BIFUNCTION).get();
    assertEquals(scalar.toString(), "28");
  }

  public void testRational() {
    Scalar scalar = GCD.BIFUNCTION.apply(RationalScalar.of(3, 2), RationalScalar.of(2, 1));
    assertEquals(scalar, RationalScalar.of(1, 2)); // Mathematica gives 1/2
  }

  public void testComplex1() {
    Scalar scalar = GCD.BIFUNCTION.apply(ComplexScalar.of(2, 1), ComplexScalar.of(3, 1));
    assertEquals(scalar, ComplexScalar.I); // Mathematica gives 1
  }

  public void testComplex2() {
    // GCD[9 + 3 I, 123 + 9 I]
    Scalar scalar = GCD.BIFUNCTION.apply(ComplexScalar.of(9, 3), ComplexScalar.of(123, 9));
    assertEquals(scalar, ComplexScalar.of(-3, 3));
    // System.out.println(scalar);
  }
}
