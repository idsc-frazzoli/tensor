// code by jph
package ch.ethz.idsc.tensor.mat;

import ch.ethz.idsc.tensor.RationalScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.lie.LieAlgebras;
import ch.ethz.idsc.tensor.lie.Rodrigues;
import ch.ethz.idsc.tensor.pdf.Distribution;
import ch.ethz.idsc.tensor.pdf.NormalDistribution;
import ch.ethz.idsc.tensor.pdf.RandomVariate;
import ch.ethz.idsc.tensor.sca.Chop;
import junit.framework.TestCase;

public class OrthogonalMatrixQTest extends TestCase {
  public void testExact() {
    Tensor matrix = Tensors.fromString("{{1, 1, 1, -1}, {-1, 1, 1, 1}}").multiply(RationalScalar.of(1, 2));
    assertTrue(OrthogonalMatrixQ.of(matrix));
    OrthogonalMatrixQ.require(matrix, Chop.NONE);
  }

  public void testRodriques() {
    Distribution distribution = NormalDistribution.standard();
    for (int count = 0; count < 20; ++count) {
      Tensor matrix = Rodrigues.exp(RandomVariate.of(distribution, 3));
      assertTrue(OrthogonalMatrixQ.of(matrix));
      OrthogonalMatrixQ.require(matrix);
      try {
        OrthogonalMatrixQ.require(matrix, Chop.NONE);
        fail();
      } catch (Exception exception) {
        // ---
      }
    }
  }

  public void testCornerCase() {
    assertFalse(OrthogonalMatrixQ.of(RealScalar.of(1)));
    assertFalse(OrthogonalMatrixQ.of(Tensors.vector(1, 0, 0)));
    assertFalse(OrthogonalMatrixQ.of(Tensors.vector(1, 0, 2)));
    assertFalse(OrthogonalMatrixQ.of(LieAlgebras.so3()));
  }

  public void testRequire() {
    OrthogonalMatrixQ.require(IdentityMatrix.of(4), Chop.NONE);
    try {
      OrthogonalMatrixQ.require(HilbertMatrix.of(3));
      fail();
    } catch (Exception exception) {
      // ---
    }
  }
}
