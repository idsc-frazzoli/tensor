// code by jph
package ch.ethz.idsc.tensor.lie;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.alg.Array;
import ch.ethz.idsc.tensor.mat.OrthogonalMatrixQ;
import ch.ethz.idsc.tensor.mat.Orthogonalize;
import ch.ethz.idsc.tensor.pdf.NormalDistribution;
import ch.ethz.idsc.tensor.pdf.RandomVariate;
import junit.framework.TestCase;

public class RodriguesFailTest extends TestCase {
  public void testOrthPassFormatFailEye() {
    Scalar one = RealScalar.ONE;
    Tensor eyestr = Tensors.matrix((i, j) -> i.equals(j) ? one : one.zero(), 3, 4);
    try {
      Rodrigues.logMatrix(eyestr);
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
      Rodrigues.logMatrix(orthog);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }

  public void testFail() {
    try {
      Rodrigues.exp(RealScalar.ZERO);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
    try {
      Rodrigues.exp(Tensors.vector(0, 0));
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
    try {
      Rodrigues.exp(Tensors.vector(0, 0, 0, 0));
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }

  public void testLogTrash() {
    Tensor matrix = RandomVariate.of(NormalDistribution.standard(), 3, 3);
    try {
      Rodrigues.log(matrix);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }

  public void testLogFail() {
    try {
      Rodrigues.logMatrix(Array.zeros(3));
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
    try {
      Rodrigues.logMatrix(Array.zeros(3, 4));
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
    try {
      Rodrigues.logMatrix(LieAlgebras.sl2());
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }
}
