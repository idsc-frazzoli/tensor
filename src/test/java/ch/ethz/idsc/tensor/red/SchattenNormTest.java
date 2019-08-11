// code by jph
package ch.ethz.idsc.tensor.red;

import java.io.IOException;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.io.Serialization;
import ch.ethz.idsc.tensor.lie.LieAlgebras;
import ch.ethz.idsc.tensor.pdf.Distribution;
import ch.ethz.idsc.tensor.pdf.RandomVariate;
import ch.ethz.idsc.tensor.pdf.UniformDistribution;
import ch.ethz.idsc.tensor.sca.Chop;
import ch.ethz.idsc.tensor.sca.Sign;
import junit.framework.TestCase;

public class SchattenNormTest extends TestCase {
  public void testFrobenius() throws ClassNotFoundException, IOException {
    NormInterface normInterface = Serialization.copy(new SchattenNorm(RealScalar.of(2)));
    Distribution distribution = UniformDistribution.unit();
    Tensor matrix = RandomVariate.of(distribution, 5, 10);
    Scalar norm1 = normInterface.ofMatrix(matrix);
    Scalar norm2 = Frobenius.NORM.ofMatrix(matrix);
    assertTrue(Chop._13.close(norm1, norm2));
  }

  public void testFrobeniusInstance() {
    assertEquals(SchattenNorm.with(2), Frobenius.NORM);
  }

  public void testFail() throws ClassNotFoundException, IOException {
    NormInterface normInterface = Serialization.copy(SchattenNorm.with(1.2));
    Distribution distribution = UniformDistribution.unit();
    Tensor matrix = RandomVariate.of(distribution, 10, 5);
    Scalar scalar = normInterface.ofMatrix(matrix);
    Sign.requirePositive(scalar);
    try {
      normInterface.ofMatrix(LieAlgebras.so3());
      fail();
    } catch (Exception exception) {
      // ---
    }
  }
}
