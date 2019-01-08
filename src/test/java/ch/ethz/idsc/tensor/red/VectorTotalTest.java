// code by jph
package ch.ethz.idsc.tensor.red;

import java.io.IOException;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.alg.Normalize;
import ch.ethz.idsc.tensor.io.Serialization;
import ch.ethz.idsc.tensor.mat.HilbertMatrix;
import ch.ethz.idsc.tensor.opt.TensorUnaryOperator;
import junit.framework.TestCase;

public class VectorTotalTest extends TestCase {
  public void testSimple() {
    Scalar scalar = VectorTotal.FUNCTION.apply(Tensors.vector(1, 2, 3));
    assertEquals(scalar, RealScalar.of(6));
  }

  public void testEmpty() {
    Scalar scalar = VectorTotal.FUNCTION.apply(Tensors.empty());
    assertEquals(scalar, RealScalar.ZERO);
  }

  public void testNormalize() throws ClassNotFoundException, IOException {
    TensorUnaryOperator tensorUnaryOperator = Normalize.with(VectorTotal.FUNCTION);
    TensorUnaryOperator copy = Serialization.copy(tensorUnaryOperator);
    Tensor vector = copy.apply(Tensors.vector(1, 2, 3));
    assertEquals(vector, Tensors.vector(1, 2, 3).divide(RealScalar.of(6)));
  }

  public void testFail() {
    try {
      VectorTotal.FUNCTION.apply(HilbertMatrix.of(3));
      fail();
    } catch (Exception exception) {
      // ---
    }
  }
}
