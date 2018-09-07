// code by jph
package ch.ethz.idsc.tensor.lie;

import ch.ethz.idsc.tensor.ComplexScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.alg.Array;
import ch.ethz.idsc.tensor.alg.Transpose;
import ch.ethz.idsc.tensor.mat.DiagonalMatrix;
import ch.ethz.idsc.tensor.mat.IdentityMatrix;
import ch.ethz.idsc.tensor.pdf.Distribution;
import ch.ethz.idsc.tensor.pdf.NormalDistribution;
import ch.ethz.idsc.tensor.pdf.RandomVariate;
import ch.ethz.idsc.tensor.red.Trace;
import ch.ethz.idsc.tensor.sca.Chop;
import junit.framework.TestCase;

public class MatrixLog2Test extends TestCase {
  private static void _checkExpLog(Tensor matrix) {
    Tensor exp = MatrixExp.of(matrix);
    Tensor log = MatrixLog.of(exp);
    Tensor elg = MatrixExp.of(log);
    assertTrue(Chop._12.close(elg, exp));
    // Chop._10.close(log, matrix); // not generally true!
  }

  private static void _checkLogExp(Tensor matrix) {
    Tensor log = MatrixLog.of(matrix);
    Tensor exp = MatrixExp.of(log);
    assertTrue(Chop._12.close(exp, matrix));
  }

  public void testIdentity() {
    Tensor mlog = MatrixLog.of(IdentityMatrix.of(2));
    assertEquals(mlog, Array.zeros(2, 2));
  }

  public void testDiagonal() {
    Tensor mlog = MatrixLog.of(DiagonalMatrix.of(2, 3));
    assertEquals(mlog, DiagonalMatrix.of(Math.log(2), Math.log(3)));
  }

  public void testFull() {
    Tensor matrix = Tensors.fromString("{{4,2},{-1,1}}");
    Tensor mlog = MatrixLog.of(matrix);
    Tensor mathematica = Tensors.fromString( //
        "{{1.5040773967762740734, 0.81093021621632876396},{-0.40546510810816438198, 0.28768207245178092744}}");
    assertTrue(Chop._14.close(mlog, mathematica));
    assertTrue(Chop._14.close(matrix, MatrixExp.of(mlog)));
  }

  public void testUpper() {
    Tensor matrix = Tensors.fromString("{{4,2},{0,1}}");
    Tensor mlog = MatrixLog.of(matrix);
    Tensor mathematica = Tensors.fromString( //
        "{{1.3862943611198906188, 0.92419624074659374589}, {0, 0}}");
    assertTrue(Chop._14.close(mlog, mathematica));
    assertTrue(Chop._14.close(matrix, MatrixExp.of(mlog)));
  }

  public void testLower() {
    Tensor matrix = Tensors.fromString("{{4,0},{2,1}}");
    Tensor mlog = MatrixLog.of(matrix);
    Tensor mathematica = Transpose.of(Tensors.fromString( //
        "{{1.3862943611198906188, 0.92419624074659374589}, {0, 0}}"));
    assertTrue(Chop._14.close(mlog, mathematica));
    assertTrue(Chop._14.close(matrix, MatrixExp.of(mlog)));
  }

  public void testTraceZero() {
    Distribution distribution = NormalDistribution.of(0, 2);
    for (int index = 0; index < 10; ++index) {
      Tensor alg = RandomVariate.of(distribution, 2, 2);
      alg.set(alg.Get(0, 0).negate(), 1, 1);
      assertEquals(Trace.of(alg), RealScalar.ZERO);
      _checkExpLog(alg);
      _checkLogExp(alg);
    }
  }

  public void testComplex() {
    Distribution distribution = NormalDistribution.standard();
    for (int index = 0; index < 10; ++index) {
      Tensor re = RandomVariate.of(distribution, 2, 2);
      Tensor im = RandomVariate.of(distribution, 2, 2);
      Tensor alg = re.add(im.multiply(ComplexScalar.I));
      _checkExpLog(alg);
      _checkLogExp(alg);
    }
  }

  public void testComplexTraceZero() {
    Distribution distribution = NormalDistribution.standard();
    for (int index = 0; index < 10; ++index) {
      Tensor re = RandomVariate.of(distribution, 2, 2);
      Tensor im = RandomVariate.of(distribution, 2, 2);
      Tensor alg = re.add(im.multiply(ComplexScalar.I));
      alg.set(alg.Get(0, 0).negate(), 1, 1);
      assertEquals(Trace.of(alg), RealScalar.ZERO);
      _checkExpLog(alg);
      _checkLogExp(alg);
    }
  }

  public void testFail() {
    Distribution distribution = NormalDistribution.of(0, 2);
    Tensor matrix = RandomVariate.of(distribution, 2, 3);
    try {
      MatrixLog.of(matrix);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }
}
