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
import junit.framework.TestCase;

public class OrthogonalMatrixQTest extends TestCase {
  public void testExact() {
    Tensor matrix = Tensors.fromString("{{1, 1, 1, -1}, {-1, 1, 1, 1}}").multiply(RationalScalar.of(1, 2));
    assertTrue(OrthogonalMatrixQ.of(matrix));
  }

  public void testRodriques() {
    Distribution dis = NormalDistribution.standard();
    for (int c = 0; c < 20; ++c) {
      Tensor matrix = Rodrigues.exp(RandomVariate.of(dis, 3));
      assertTrue(OrthogonalMatrixQ.of(matrix));
    }
  }

  public void testCornerCase() {
    assertFalse(OrthogonalMatrixQ.of(RealScalar.of(1)));
    assertFalse(OrthogonalMatrixQ.of(Tensors.vector(1, 0, 0)));
    assertFalse(OrthogonalMatrixQ.of(Tensors.vector(1, 0, 2)));
    assertFalse(OrthogonalMatrixQ.of(LieAlgebras.so3()));
  }
}
