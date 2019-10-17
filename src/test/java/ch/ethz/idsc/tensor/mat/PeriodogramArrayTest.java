// code by jph
package ch.ethz.idsc.tensor.mat;

import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.sca.Chop;
import junit.framework.TestCase;

public class PeriodogramArrayTest extends TestCase {
  public void testDefault() {
    Tensor tensor = PeriodogramArray.of(Tensors.vector(0, 1, 0, -1, 0, 1, 0, -1));
    Tensor result = Tensors.vector(0, 0, 2, 0, 0, 0, 2, 0);
    Chop._12.requireClose(tensor, result);
  }

  public void testSize() {
    Tensor tensor = PeriodogramArray.of(Tensors.vector(0, 1, 0, -1, 0, 1, 0, -1), 4);
    Tensor result = Tensors.vector(0, 1, 0, 1); // confirmed with Mathematica
    Chop._12.requireClose(tensor, result);
  }

  public void testSizeOffset() {
    Tensor tensor = PeriodogramArray.of(Tensors.vector(0, 1, 0, -1, 0, 1, 0, -1), 4, 1);
    Tensor result = Tensors.vector(0, 1, 0, 1); // confirmed with Mathematica
    Chop._12.requireClose(tensor, result);
  }

  public void testZeroFail() {
    try {
      PeriodogramArray.of(Tensors.vector(0, 1, 0, -1, 0, 1, 0, -1), 0);
      fail();
    } catch (Exception exception) {
      // ---
    }
  }
}
