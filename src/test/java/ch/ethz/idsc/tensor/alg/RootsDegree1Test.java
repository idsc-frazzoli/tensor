// code by jph
package ch.ethz.idsc.tensor.alg;

import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.num.GaussScalar;
import ch.ethz.idsc.tensor.sca.Chop;
import junit.framework.TestCase;

public class RootsDegree1Test extends TestCase {
  public void testGaussScalar() {
    Tensor coeffs = Tensors.of(GaussScalar.of(4, 7), GaussScalar.of(5, 7));
    Tensor roots = Roots.of(coeffs);
    Tensor zeros = roots.map(Series.of(coeffs));
    Chop.NONE.requireAllZero(zeros);
    assertEquals(roots, Tensors.of(GaussScalar.of(2, 7)));
  }
}
