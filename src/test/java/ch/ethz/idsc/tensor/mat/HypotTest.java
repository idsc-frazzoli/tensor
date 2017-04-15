// code by jph
package ch.ethz.idsc.tensor.mat;

import ch.ethz.idsc.tensor.RationalScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.red.Hypot;
import ch.ethz.idsc.tensor.red.Norm;
import junit.framework.TestCase;

public class HypotTest extends TestCase {
  private static void checkPair(double x, double y) {
    // Tensor vec = Tensors.vector(x, y);
    Scalar res = Hypot.bifunction.apply(RealScalar.of(x), RealScalar.of(y));
    double jav = Math.hypot(x, y);
    //
    // assertEquals(hyp.number().doubleValue(), jav);
    assertEquals(res.number().doubleValue(), jav);
    // Tensor vector = Tensors.vector(x, y);
    // Scalar hyp = Hypot.ofVector(vector);
    // System.out.println(hyp.number().doubleValue() + " " + jav);
    // Scalar nrm = Norm._2.of(vector);
    // System.out.println(hyp + " " + nrm);
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
}
