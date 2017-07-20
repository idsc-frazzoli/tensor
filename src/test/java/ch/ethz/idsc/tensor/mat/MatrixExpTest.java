// code by jph
package ch.ethz.idsc.tensor.mat;

import java.util.Random;

import ch.ethz.idsc.tensor.DoubleScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.alg.Array;
import ch.ethz.idsc.tensor.alg.Dimensions;
import ch.ethz.idsc.tensor.alg.Transpose;
import ch.ethz.idsc.tensor.red.Trace;
import ch.ethz.idsc.tensor.sca.Chop;
import junit.framework.TestCase;

public class MatrixExpTest extends TestCase {
  public void testExp() {
    Random ra = new Random();
    double val = ra.nextGaussian();
    double va2 = ra.nextGaussian();
    double va3 = ra.nextGaussian();
    double[][] mat = new double[][] { { 0, val, va2 }, { -val, 0, va3 }, { -va2, -va3, 0 } };
    Tensor bu = Tensors.matrixDouble(mat);
    Tensor o = MatrixExp.of(bu);
    assertEquals( //
        Chop._12.of(o.dot(Transpose.of(o)).subtract(IdentityMatrix.of(o.length()))), //
        Array.zeros(Dimensions.of(o)));
  }

  public void testExp2() {
    int n = 10;
    Random random = new Random();
    Tensor A = Tensors.matrix((i, j) -> DoubleScalar.of(random.nextGaussian()), n, n);
    Tensor S = A.subtract(Transpose.of(A));
    Tensor o = MatrixExp.of(S);
    assertEquals( //
        o.dot(Transpose.of(o)).subtract(IdentityMatrix.of(o.length())).map(Chop.below(1e-10)), //
        Array.zeros(Dimensions.of(o)));
  }

  public void testGoldenThompsonInequality() {
    Tensor a = Tensors.fromString("{{2,I},{-I,2}}");
    Tensor b = Tensors.fromString("{{2,1-I},{1+I,2}}");
    assertTrue(HermitianMatrixQ.of(a));
    assertTrue(HermitianMatrixQ.of(b));
    Scalar tra = Trace.of(MatrixExp.of(a.add(b)));
    Scalar trb = Trace.of(MatrixExp.of(a).dot(MatrixExp.of(b)));
    assertTrue(Chop._05.close(tra, RealScalar.of(168.49869602))); // mathematica
    assertTrue(Chop._05.close(trb, RealScalar.of(191.43054831))); // mathematica
  }

  public void testExact() {
    Tensor mat = Tensors.matrixInt(new int[][] { { 0, 2, 3 }, { 0, 0, -1 }, { 0, 0, 0 } });
    Tensor result = MatrixExp.of(mat);
    Tensor actual = Tensors.matrixInt(new int[][] { { 1, 2, 2 }, { 0, 1, -1 }, { 0, 0, 1 } });
    assertEquals(result, actual);
    assertEquals(result.toString(), actual.toString());
  }

  public void testFail() {
    try {
      MatrixExp.of(Array.zeros(4, 3));
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
    try {
      MatrixExp.of(Array.zeros(3, 4));
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }
}
