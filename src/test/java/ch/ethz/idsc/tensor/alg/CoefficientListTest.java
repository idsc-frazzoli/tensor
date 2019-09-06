// code by jph
package ch.ethz.idsc.tensor.alg;

import ch.ethz.idsc.tensor.ExactTensorQ;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.qty.Quantity;
import ch.ethz.idsc.tensor.sca.Chop;
import junit.framework.TestCase;

public class CoefficientListTest extends TestCase {
  public void testSimple() {
    Tensor zeros = Tensors.vector(3);
    Tensor coeffs = CoefficientList.of(zeros);
    Chop.NONE.requireAllZero(Series.of(coeffs).apply(RealScalar.of(3)));
    Tensor roots = Roots.of(coeffs);
    assertEquals(roots, zeros);
  }

  public void testQuantityD1() {
    Tensor zeros = Tensors.fromString("{3[m]}");
    Tensor coeffs = CoefficientList.of(zeros);
    Chop.NONE.requireAllZero(Series.of(coeffs).apply(Quantity.of(3, "m")));
    Tensor roots = Roots.of(coeffs);
    assertEquals(roots, zeros);
  }

  public void testQuantityD2() {
    Tensor zeros = Tensors.fromString("{3[m], 4[m]}");
    Tensor coeffs = CoefficientList.of(zeros);
    Chop.NONE.requireAllZero(Series.of(coeffs).apply(Quantity.of(3, "m")));
    Chop.NONE.requireAllZero(Series.of(coeffs).apply(Quantity.of(4, "m")));
    Tensor roots = Roots.of(coeffs);
    ExactTensorQ.require(roots);
    assertEquals(roots, zeros);
  }

  public void testQuantityD3() {
    Tensor zeros = Tensors.fromString("{3[m], 4[m], 6[m]}");
    Tensor coeffs = CoefficientList.of(zeros);
    Chop._14.requireAllZero(Series.of(coeffs).apply(Quantity.of(3, "m")));
    Chop._14.requireAllZero(Series.of(coeffs).apply(Quantity.of(4, "m")));
    Chop._14.requireAllZero(Series.of(coeffs).apply(Quantity.of(6, "m")));
    Tensor roots = Roots.of(coeffs);
    Chop._14.requireClose(roots, zeros);
  }

  public void testEmptyFail() {
    try {
      CoefficientList.of(Tensors.empty());
      fail();
    } catch (Exception exception) {
      // ---
    }
  }
}
