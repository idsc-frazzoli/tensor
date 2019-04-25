// code by jph
package ch.ethz.idsc.tensor.lie;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.opt.Pi;
import ch.ethz.idsc.tensor.pdf.Distribution;
import ch.ethz.idsc.tensor.pdf.RandomVariate;
import ch.ethz.idsc.tensor.pdf.UniformDistribution;
import ch.ethz.idsc.tensor.red.Norm;
import ch.ethz.idsc.tensor.sca.ArcTan;
import ch.ethz.idsc.tensor.sca.Chop;
import junit.framework.TestCase;

public class AngleVectorTest extends TestCase {
  public void testNorm() {
    Distribution distribution = UniformDistribution.of(Pi.VALUE.negate(), Pi.VALUE);
    for (int index = 0; index < 10; ++index) {
      Scalar angle = RandomVariate.of(distribution).negate(); // prevent angle == -pi
      Tensor vector = AngleVector.of(angle);
      Chop._14.requireClose(Norm._2.ofVector(vector), RealScalar.ONE);
      Scalar check = ArcTan.of(vector.Get(0), vector.Get(1));
      Chop._14.requireClose(angle, check);
    }
  }

  public void testMatrix() {
    Scalar angle = RealScalar.ONE;
    Tensor vector = AngleVector.of(angle);
    Tensor matrix = RotationMatrix.of(angle);
    assertEquals(vector, matrix.get(Tensor.ALL, 0));
  }

  public void testNullFail() {
    try {
      AngleVector.of(null);
      fail();
    } catch (Exception exception) {
      // ---
    }
  }
}
