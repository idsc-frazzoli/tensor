// code by jph
package ch.ethz.idsc.tensor.mat;

import ch.ethz.idsc.tensor.RationalScalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.lie.Rodriguez;
import ch.ethz.idsc.tensor.pdf.Distribution;
import ch.ethz.idsc.tensor.pdf.NormalDistribution;
import ch.ethz.idsc.tensor.pdf.RandomVariate;
import junit.framework.TestCase;

public class OrthogonalMatrixQTest extends TestCase {
  public void testSimple() {
    Tensor matrix = Tensors.fromString("{{1, 1, 1, -1}, {-1, 1, 1, 1}}").multiply(RationalScalar.of(1, 2));
    assertTrue(OrthogonalMatrixQ.of(matrix));
  }

  public void testRodriques() {
    Distribution dis = NormalDistribution.of();
    for (int c = 0; c < 20; ++c) {
      Tensor matrix = Rodriguez.of(RandomVariate.of(dis, 3));
      assertTrue(OrthogonalMatrixQ.of(matrix));
    }
  }
}
