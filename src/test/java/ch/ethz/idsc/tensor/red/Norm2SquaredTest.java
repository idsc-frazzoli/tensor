// code by jph
package ch.ethz.idsc.tensor.red;

import ch.ethz.idsc.tensor.DoubleScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.alg.Transpose;
import ch.ethz.idsc.tensor.sca.AbsSquared;
import ch.ethz.idsc.tensor.sca.Chop;
import junit.framework.TestCase;

public class Norm2SquaredTest extends TestCase {
  public void testBetween() {
    Tensor v1 = Tensors.vector(1, 2, 5);
    Tensor v2 = Tensors.vector(0, -2, 10);
    Scalar n1 = Norm2Squared.between(v1, v2);
    Scalar n2 = Norm._2.between(v1, v2);
    assertTrue(Chop._13.close(n1, AbsSquared.FUNCTION.apply(n2)));
  }

  public void testMatrix() {
    Tensor matrix = Tensors.matrix(new Number[][] { { 1, 2, 3 }, { 9, -3, 0 } });
    Scalar nrm = Norm2Squared.ofMatrix(matrix);
    assertEquals(nrm, Norm2Squared.ofMatrix(Transpose.of(matrix)));
    // Mathematica: 9.493062577750756
    Scalar s = DoubleScalar.of(9.493062577750756);
    assertTrue(Chop._14.close(nrm, s.multiply(s)));
  }

  public void testEmpty() {
    try {
      Norm2Squared.ofVector(Tensors.empty());
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }
}
