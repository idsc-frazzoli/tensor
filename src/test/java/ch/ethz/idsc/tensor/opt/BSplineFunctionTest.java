// code by jph
package ch.ethz.idsc.tensor.opt;

import ch.ethz.idsc.tensor.ExactTensorQ;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.alg.Subdivide;
import ch.ethz.idsc.tensor.mat.HilbertMatrix;
import ch.ethz.idsc.tensor.qty.Quantity;
import junit.framework.TestCase;

public class BSplineFunctionTest extends TestCase {
  public void testIdentical() {
    Tensor control = HilbertMatrix.of(20, 3).map(scalar -> Quantity.of(scalar, "bsp"));
    Tensor domain = Subdivide.of(6, 14, 28);
    for (int degree = 0; degree <= 5; ++degree) {
      ScalarTensorFunction stfC = BSplineFunction.cyclic(3, control);
      ScalarTensorFunction stfS = BSplineFunction.string(3, control);
      Tensor tensor = domain.map(stfC);
      assertEquals(tensor, domain.map(stfS));
      ExactTensorQ.require(tensor);
    }
  }
}
