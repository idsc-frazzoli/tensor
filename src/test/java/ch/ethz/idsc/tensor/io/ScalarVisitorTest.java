// code by jph
package ch.ethz.idsc.tensor.io;

import java.util.List;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import junit.framework.TestCase;

public class ScalarVisitorTest extends TestCase {
  public void testVector() {
    Tensor v1 = Tensors.vectorInt(2, 3, 4, -1, 5, 0);
    Tensor v2 = Tensors.vectorInt(2, 3, 4);
    Tensor v3 = RealScalar.of(-3);
    Tensor m3 = Tensors.of(v1, v2, v3, v2);
    Tensor m4 = Tensors.of(v1, m3, v3, m3);
    ScalarVisitor sv = new ScalarVisitor() {
      @Override
      public void visit(List<Integer> index, Scalar scalar) {
        // System.out.println(index + " " + scalar);
      }
    };
    ScalarVisitor.apply(sv, m4);
  }
}
