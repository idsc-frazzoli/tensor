// code by jph
package ch.ethz.idsc.tensor.red;

import ch.ethz.idsc.tensor.ComplexScalar;
import ch.ethz.idsc.tensor.DoubleScalar;
import ch.ethz.idsc.tensor.RationalScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Scalars;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.qty.Quantity;
import ch.ethz.idsc.tensor.sca.ArcTan;
import ch.ethz.idsc.tensor.sca.Chop;
import ch.ethz.idsc.tensor.sca.Sqrt;
import junit.framework.TestCase;

public class HypotTest extends TestCase {
  private static void _checkPair(double x, double y) {
    Scalar res = Hypot.BIFUNCTION.apply(RealScalar.of(x), RealScalar.of(y));
    double jav = Math.hypot(x, y);
    assertTrue(Chop._17.close(res, RealScalar.of(jav)));
  }

  private static void checkPair(double x, double y) {
    _checkPair(x, y);
    _checkPair(y, x);
    _checkPair(x, -y);
    _checkPair(y, -x);
    _checkPair(-x, y);
    _checkPair(-y, x);
    _checkPair(-x, -y);
    _checkPair(-y, -x);
  }

  public void testBasic() {
    checkPair(1e-300, 1e-300);
    checkPair(0, 1e-300);
    checkPair(0, 0);
    checkPair(1, 1);
    // System.out.println(1/Math.nextDown(0.0));
    checkPair(Math.nextDown(0.0), 0);
    checkPair(Math.nextDown(0.f), 0);
    checkPair(Math.nextUp(0.0), 0);
    checkPair(Math.nextUp(0.f), 0);
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
    Scalar pair = Hypot.BIFUNCTION.apply(c1, c2);
    assertEquals(Sqrt.of(RealScalar.of(46)), pair);
    Scalar func = Hypot.ofVector(Tensors.of(c1, c2));
    assertEquals(func, pair);
    Scalar norm = Norm._2.ofVector(Tensors.of(c1, c2));
    assertEquals(norm, pair);
  }

  public void testNaNdivNaN() {
    Scalar s1 = DoubleScalar.INDETERMINATE;
    Scalar s2 = DoubleScalar.INDETERMINATE;
    Scalar s3 = s1.divide(s2);
    assertEquals(s3.toString(), "NaN");
  }

  public void testInfNan() {
    Scalar s1 = DoubleScalar.POSITIVE_INFINITY;
    assertFalse(Scalars.isZero(s1));
    Scalar s2 = DoubleScalar.INDETERMINATE;
    assertFalse(Scalars.isZero(s2));
    try {
      Scalar s3 = Hypot.BIFUNCTION.apply(s1, s2); // NaN+NaN*I
      assertTrue(s3 instanceof ComplexScalar);
      assertFalse(Scalars.isZero(s3));
      @SuppressWarnings("unused")
      Scalar s4 = ArcTan.FUNCTION.apply(s2);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }

  public void testDoubleNaNFail() {
    try {
      ArcTan.FUNCTION.apply(ComplexScalar.of(Double.NaN, Double.NaN));
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }

  public void testFailScalar() {
    try {
      Hypot.ofVector(RealScalar.ONE);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }

  public void testQuantity() {
    Scalar qs1 = Quantity.of(3, "m");
    Scalar qs2 = Quantity.of(4, "m");
    Scalar qs3 = Quantity.of(5, "m");
    assertEquals(Hypot.BIFUNCTION.apply(qs1, qs2), qs3);
  }

  public void testQuantityZero() {
    Scalar qs1 = Quantity.of(1, "m");
    Scalar qs2 = Quantity.of(0, "m");
    assertEquals(Hypot.BIFUNCTION.apply(qs1, qs2), qs1);
  }

  public void testQuantityZeroFail() {
    try {
      Hypot.BIFUNCTION.apply( //
          Quantity.of(1, "m"), //
          Quantity.of(0, "s"));
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }
}
