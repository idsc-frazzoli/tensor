// code by jph
package ch.ethz.idsc.tensor.lie;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.alg.Array;
import ch.ethz.idsc.tensor.alg.Transpose;
import ch.ethz.idsc.tensor.mat.IdentityMatrix;
import ch.ethz.idsc.tensor.mat.OrthogonalMatrixQ;
import ch.ethz.idsc.tensor.mat.Orthogonalize;
import ch.ethz.idsc.tensor.pdf.NormalDistribution;
import ch.ethz.idsc.tensor.pdf.RandomVariate;
import ch.ethz.idsc.tensor.sca.Chop;
import junit.framework.TestCase;

public class RodriguezTest extends TestCase {
  private static void checkDiff(Tensor c) {
    Tensor e = Rodriguez.exp(c);
    assertTrue(Chop._14.close(e, MatrixExp.of(Cross.of(c))));
    assertTrue(Chop._14.close(e.dot(c), c));
  }

  public void testXY() {
    Tensor m22 = RotationMatrix.of(RealScalar.ONE);
    Tensor mat = Rodriguez.exp(Tensors.vector(0, 0, 1));
    Tensor blu = Tensors.of( //
        mat.get(0).extract(0, 2), //
        mat.get(1).extract(0, 2));
    assertEquals(blu, m22);
  }

  public void testFormula() {
    checkDiff(Tensors.vector(-.2, .1, .3));
    checkDiff(Tensors.vector(-.5, -.1, .03));
    checkDiff(Tensors.vector(-.3, -.2, .1));
    checkDiff(Tensors.vector(-.3, -.2, -.3));
  }

  public void testFail() {
    try {
      Rodriguez.exp(RealScalar.ZERO);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
    try {
      Rodriguez.exp(Tensors.vector(0, 0));
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
    try {
      Rodriguez.exp(Tensors.vector(0, 0, 0, 0));
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }

  public void testRotZ() {
    Tensor matrix = Rodriguez.exp(Tensors.vector(0, 0, 1));
    assertEquals(matrix.get(2, 0), RealScalar.ZERO);
    assertEquals(matrix.get(2, 1), RealScalar.ZERO);
    assertEquals(matrix.get(0, 2), RealScalar.ZERO);
    assertEquals(matrix.get(1, 2), RealScalar.ZERO);
    assertEquals(matrix.get(2, 2), RealScalar.ONE);
  }

  public void testLogEye() {
    Tensor matrix = IdentityMatrix.of(3);
    Tensor log = Rodriguez.log(matrix);
    assertTrue(Chop.NONE.allZero(log));
  }

  public void testLog1() {
    Tensor vec = Tensors.vector(.3, .5, -0.4);
    Tensor matrix = Rodriguez.exp(vec);
    Tensor lom = Rodriguez.logMatrix(matrix);
    Tensor log = Rodriguez.log(matrix);
    assertTrue(Chop._14.close(vec, log));
    assertTrue(Chop._14.close(lom, Cross.of(vec)));
  }

  public void testLogEps() {
    double v = 0.25;
    Tensor log;
    do {
      v = v / 1.1;
      Tensor vec = Tensors.vector(v, v, v);
      Tensor matrix = Rodriguez.exp(vec);
      {
        Tensor logM = Rodriguez.logMatrix(matrix);
        assertTrue(Chop._13.close(logM.negate(), Transpose.of(logM)));
      }
      log = Rodriguez.log(matrix);
    } while (!Chop.NONE.allZero(log));
  }

  public void testOrthPassFormatFailEye() {
    Scalar one = RealScalar.ONE;
    Tensor eyestr = Tensors.matrix((i, j) -> i.equals(j) ? one : one.zero(), 3, 4);
    try {
      Rodriguez.logMatrix(eyestr);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }

  public void testOrthPassFormatFail2() {
    Tensor matrix = RandomVariate.of(NormalDistribution.standard(), 3, 5);
    Tensor orthog = Orthogonalize.of(matrix);
    assertTrue(OrthogonalMatrixQ.of(orthog));
    try {
      Rodriguez.logMatrix(orthog);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }

  public void testLogEps2() {
    double eps = Double.MIN_VALUE; // 4.9e-324
    Tensor vec = Tensors.vector(eps, 0, 0);
    Tensor matrix = Rodriguez.exp(vec);
    Tensor log = Rodriguez.log(matrix);
    assertTrue(Chop._50.allZero(log));
  }

  public void testLogTrash() {
    Tensor matrix = RandomVariate.of(NormalDistribution.standard(), 3, 3);
    try {
      Rodriguez.log(matrix);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }

  public void testLogFail() {
    try {
      Rodriguez.logMatrix(Array.zeros(3));
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
    try {
      Rodriguez.logMatrix(Array.zeros(3, 4));
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
    try {
      Rodriguez.logMatrix(LieAlgebras.sl3());
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }
}
