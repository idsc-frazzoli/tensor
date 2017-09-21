// code by jph
package ch.ethz.idsc.tensor.lie;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.red.Norm;
import junit.framework.TestCase;

public class AngleVectorTest extends TestCase {
  public void testSimple() {
    Scalar theta = RealScalar.ONE;
    Tensor vector = AngleVector.of(theta);
    assertEquals(Norm._2.ofVector(vector), theta);
  }

  public void testMatrix() {
    Scalar theta = RealScalar.ONE;
    Tensor vector = AngleVector.of(theta);
    Tensor matrix = RotationMatrix.of(theta);
    assertEquals(vector, matrix.get(Tensor.ALL, 0));
  }
}
