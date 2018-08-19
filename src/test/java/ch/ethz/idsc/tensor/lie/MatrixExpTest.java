// code by jph
package ch.ethz.idsc.tensor.lie;

import java.util.Random;

import ch.ethz.idsc.tensor.DoubleScalar;
import ch.ethz.idsc.tensor.ExactScalarQ;
import ch.ethz.idsc.tensor.RationalScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.alg.Array;
import ch.ethz.idsc.tensor.alg.Dimensions;
import ch.ethz.idsc.tensor.alg.Transpose;
import ch.ethz.idsc.tensor.mat.HermitianMatrixQ;
import ch.ethz.idsc.tensor.mat.IdentityMatrix;
import ch.ethz.idsc.tensor.pdf.NormalDistribution;
import ch.ethz.idsc.tensor.pdf.RandomVariate;
import ch.ethz.idsc.tensor.qty.Quantity;
import ch.ethz.idsc.tensor.red.Trace;
import ch.ethz.idsc.tensor.sca.Chop;
import junit.framework.TestCase;

public class MatrixExpTest extends TestCase {
  public void testZeros() {
    Tensor zeros = Array.zeros(7, 7);
    Tensor eye = MatrixExp.of(zeros);
    assertEquals(eye, IdentityMatrix.of(7));
    assertTrue(ExactScalarQ.all(eye));
  }

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

  public void testRodriguez() {
    Tensor vector = RandomVariate.of(NormalDistribution.standard(), 3);
    Tensor wedge = LieAlgebras.so3().dot(vector);
    assertTrue(Chop._13.close(MatrixExp.of(wedge), Rodrigues.exp(vector)));
  }

  public void testExp1() {
    Scalar exp1 = MatrixExp.of(Tensors.fromString("{{1}}")).Get(0, 0);
    assertFalse(ExactScalarQ.of(exp1));
    assertTrue(Chop._13.close(exp1, RealScalar.of(Math.exp(1))));
  }

  public void testExp2() {
    int n = 10;
    Random random = new Random();
    Tensor A = Tensors.matrix((i, j) -> DoubleScalar.of(random.nextGaussian()), n, n);
    Tensor S = A.subtract(Transpose.of(A));
    Tensor o = MatrixExp.of(S);
    assertEquals( //
        o.dot(Transpose.of(o)).subtract(IdentityMatrix.of(o.length())).map(Chop._10), //
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
    assertTrue(ExactScalarQ.all(result));
    Tensor actual = Tensors.matrixInt(new int[][] { { 1, 2, 2 }, { 0, 1, -1 }, { 0, 0, 1 } });
    assertEquals(result, actual);
    assertEquals(result.toString(), actual.toString());
  }

  public void testQuantity1() {
    // Mathematica can't do this :-)
    Scalar qs1 = Quantity.of(3, "m");
    Tensor ve1 = Tensors.of(RealScalar.ZERO, qs1);
    Tensor ve2 = Tensors.vector(0, 0);
    Tensor mat = Tensors.of(ve1, ve2);
    Tensor sol = MatrixExp.of(mat);
    // assertEquals(sol, mat.add(IdentityMatrix.of(2)));
    assertTrue(Chop.NONE.close(sol, mat.add(IdentityMatrix.of(2))));
  }

  public void testQuantity2() {
    Scalar qs1 = Quantity.of(2, "m");
    Scalar qs2 = Quantity.of(3, "s");
    Scalar qs3 = Quantity.of(4, "m");
    Scalar qs4 = Quantity.of(5, "s");
    Tensor mat = Tensors.of( //
        Tensors.of(RealScalar.ZERO, qs1, qs3.multiply(qs4)), //
        Tensors.of(RealScalar.ZERO, RealScalar.ZERO, qs2), //
        Tensors.of(RealScalar.ZERO, RealScalar.ZERO, RealScalar.ZERO) //
    );
    Tensor actual = IdentityMatrix.of(3).add(mat).add(mat.dot(mat).multiply(RationalScalar.of(1, 2)));
    // assertEquals(MatrixExp.of(mat), actual);
    assertTrue(Chop.NONE.close(MatrixExp.of(mat), actual));
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

  public void testEmptyFail() {
    try {
      MatrixExp.of(Tensors.empty());
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }
}
