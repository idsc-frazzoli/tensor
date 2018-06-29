// code by jph
package ch.ethz.idsc.tensor.lie;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.pdf.Distribution;
import ch.ethz.idsc.tensor.pdf.NormalDistribution;
import ch.ethz.idsc.tensor.pdf.RandomVariate;
import ch.ethz.idsc.tensor.red.Norm;
import ch.ethz.idsc.tensor.sca.Chop;
import junit.framework.TestCase;

public class AngleVectorTest extends TestCase {
  public void testNorm() {
    Distribution distribution = NormalDistribution.standard();
    for (int index = 0; index < 10; ++index) {
      Scalar theta = RandomVariate.of(distribution);
      Tensor vector = AngleVector.of(theta);
      assertTrue(Chop._14.close(Norm._2.ofVector(vector), RealScalar.ONE));
    }
  }

  public void testMatrix() {
    Scalar theta = RealScalar.ONE;
    Tensor vector = AngleVector.of(theta);
    Tensor matrix = RotationMatrix.of(theta);
    assertEquals(vector, matrix.get(Tensor.ALL, 0));
  }
}
