// code by jph
package ch.ethz.idsc.tensor.lie;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.alg.Transpose;
import ch.ethz.idsc.tensor.mat.DiagonalMatrix;
import ch.ethz.idsc.tensor.mat.IdentityMatrix;
import ch.ethz.idsc.tensor.sca.Chop;
import junit.framework.TestCase;

public class RodriguesTest extends TestCase {
  private static void checkDiff(Tensor c) {
    Tensor e = Rodrigues.exp(c);
    assertTrue(Chop._14.close(e, MatrixExp.of(Cross.of(c))));
    assertTrue(Chop._14.close(e.dot(c), c));
  }

  public void testXY() {
    Tensor m22 = RotationMatrix.of(RealScalar.ONE);
    Tensor mat = Rodrigues.exp(Tensors.vector(0, 0, 1));
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

  public void testRotZ() {
    Tensor matrix = Rodrigues.exp(Tensors.vector(0, 0, 1));
    assertEquals(matrix.get(2, 0), RealScalar.ZERO);
    assertEquals(matrix.get(2, 1), RealScalar.ZERO);
    assertEquals(matrix.get(0, 2), RealScalar.ZERO);
    assertEquals(matrix.get(1, 2), RealScalar.ZERO);
    assertEquals(matrix.get(2, 2), RealScalar.ONE);
  }

  public void testPi() {
    Tensor matrix = Rodrigues.exp(Tensors.vector(0, 0, Math.PI));
    Tensor expected = DiagonalMatrix.of(-1, -1, 1);
    assertTrue(Chop._14.close(matrix, expected));
  }

  public void testTwoPi() {
    Tensor matrix = Rodrigues.exp(Tensors.vector(0, 0, 2 * Math.PI));
    Tensor expected = DiagonalMatrix.of(1, 1, 1);
    assertTrue(Chop._14.close(matrix, expected));
  }

  public void testLogEye() {
    Tensor matrix = IdentityMatrix.of(3);
    Tensor log = Rodrigues.log(matrix);
    assertTrue(Chop.NONE.allZero(log));
  }

  public void testLog1() {
    Tensor vec = Tensors.vector(.3, .5, -0.4);
    Tensor matrix = Rodrigues.exp(vec);
    Tensor lom = Rodrigues.logMatrix(matrix);
    Tensor log = Rodrigues.log(matrix);
    assertTrue(Chop._14.close(vec, log));
    assertTrue(Chop._14.close(lom, Cross.of(vec)));
  }

  public void testLogEps() {
    double v = 0.25;
    Tensor log;
    do {
      v = v / 1.1;
      Tensor vec = Tensors.vector(v, v, v);
      Tensor matrix = Rodrigues.exp(vec);
      {
        Tensor logM = Rodrigues.logMatrix(matrix);
        assertTrue(Chop._13.close(logM.negate(), Transpose.of(logM)));
      }
      log = Rodrigues.log(matrix);
    } while (!Chop.NONE.allZero(log));
  }

  public void testLogEps2() {
    double eps = Double.MIN_VALUE; // 4.9e-324
    Tensor vec = Tensors.vector(eps, 0, 0);
    Tensor matrix = Rodrigues.exp(vec);
    Tensor log = Rodrigues.log(matrix);
    assertTrue(Chop._50.allZero(log));
  }
}
