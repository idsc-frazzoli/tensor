// code by jph
package ch.ethz.idsc.tensor.red;

import ch.ethz.idsc.tensor.ComplexScalar;
import ch.ethz.idsc.tensor.RationalScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Scalars;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.sca.ArcTan;
import ch.ethz.idsc.tensor.sca.Sqrt;
import junit.framework.TestCase;

public class HypotTest extends TestCase {
  private static void checkPair(double x, double y) {
    Scalar res = Hypot.bifunction.apply(RealScalar.of(x), RealScalar.of(y));
    double jav = Math.hypot(x, y);
    assertEquals(res.number().doubleValue(), jav);
  }

  public void testBasic() {
    checkPair(1e-300, 1e-300);
    checkPair(0, 1e-300);
    checkPair(0, 0);
    checkPair(1, 1);
  }

  private static void checkVectorExact(Tensor vec) {
    Scalar hyp = Hypot.ofVector(vec);
    assertTrue(hyp instanceof RationalScalar);
    Scalar nrm = Norm._2.of(vec);
    assertTrue(nrm instanceof RationalScalar);
    assertEquals(hyp, nrm);
  }

  public void testExact3() {
    int[][] array = new int[][] { //
        { 1, 4, 8 }, { 2, 3, 6 }, { 2, 5, 14 }, { 2, 6, 9 }, { 2, 8, 16 }, //
        { 2, 10, 11 }, { 3, 4, 12 }, { 4, 6, 12 }, { 4, 12, 18 }, { 4, 13, 16 }, //
        { 6, 9, 18 }, { 6, 10, 15 }, { 6, 13, 18 }, { 7, 14, 22 }, { 8, 9, 12 }, //
        { 8, 11, 16 }, { 9, 12, 20 } };
    for (Tensor vec : Tensors.matrixInt(array))
      checkVectorExact(vec);
  }

  public void testTuple() {
    int[][] array = new int[][] { //
        { 3, 4 }, { 5, 12 }, { 6, 8 }, { 7, 24 }, { 8, 15 }, { 9, 12 }, { 10, 24 }, //
        { 12, 16 }, { 15, 20 }, { 16, 30 }, { 18, 24 }, { 20, 21 } };
    for (Tensor vec : Tensors.matrixInt(array))
      checkVectorExact(vec);
  }

  public void testComplex() {
    Scalar c1 = ComplexScalar.of(1, -5);
    Scalar c2 = ComplexScalar.of(2, 4);
    Scalar pair = Hypot.bifunction.apply(c1, c2);
    assertEquals(Sqrt.of(RealScalar.of(46)), pair);
    Scalar func = Hypot.ofVector(Tensors.of(c1, c2));
    assertEquals(func, pair);
    Scalar norm = Norm._2.of(Tensors.of(c1, c2));
    assertEquals(norm, pair);
  }

  public void testNaNdivNaN() {
    Scalar s1 = RealScalar.INDETERMINATE;
    Scalar s2 = RealScalar.INDETERMINATE;
    Scalar s3 = s1.divide(s2);
    assertEquals(s3.toString(), "NaN");
  }

  public void testInfNan() {
    Scalar s1 = RealScalar.POSITIVE_INFINITY;
    assertFalse(Scalars.isZero(s1));
    Scalar s2 = RealScalar.INDETERMINATE;
    assertFalse(Scalars.isZero(s2));
    try {
      Scalar s3 = Hypot.bifunction.apply(s1, s2); // NaN+NaN*I
      assertTrue(s3 instanceof ComplexScalar);
      assertFalse(Scalars.isZero(s3));
      // System.out.println(s3);
      @SuppressWarnings("unused")
      Scalar s4 = ArcTan.function.apply(s2);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
    // System.out.println(s4);
    // Cos.of(s3);
    // FIXME the next line causes infinite recursion... ARG
    // Scalar s5 = ArcTan.function.apply(s3);
    // System.out.println(s5);
  }
}
