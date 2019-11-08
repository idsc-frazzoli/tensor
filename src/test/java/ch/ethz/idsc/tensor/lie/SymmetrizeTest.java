// code by jph
package ch.ethz.idsc.tensor.lie;

import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.alg.Transpose;
import ch.ethz.idsc.tensor.pdf.Distribution;
import ch.ethz.idsc.tensor.pdf.RandomVariate;
import ch.ethz.idsc.tensor.pdf.UniformDistribution;
import ch.ethz.idsc.tensor.sca.Chop;
import junit.framework.TestCase;

public class SymmetrizeTest extends TestCase {
  public void testSimple() {
    Distribution distribution = UniformDistribution.unit();
    Tensor tensor = RandomVariate.of(distribution, 3, 3, 3);
    Tensor symmet = Symmetrize.of(tensor);
    Chop._04.requireClose(symmet, Transpose.of(symmet, 0, 1, 2));
    Chop._04.requireClose(symmet, Transpose.of(symmet, 0, 2, 1));
    Chop._04.requireClose(symmet, Transpose.of(symmet, 1, 0, 2));
    Chop._04.requireClose(symmet, Transpose.of(symmet, 1, 2, 0));
    Chop._04.requireClose(symmet, Transpose.of(symmet, 2, 0, 1));
    Chop._04.requireClose(symmet, Transpose.of(symmet, 2, 1, 0));
    Tensor zeros = TensorWedge.of(symmet);
    Chop._10.requireAllZero(zeros);
  }

  public void testRectangularFail() {
    Distribution distribution = UniformDistribution.unit();
    try {
      Symmetrize.of(RandomVariate.of(distribution, 3, 2));
      fail();
    } catch (Exception exception) {
      // ---
    }
    try {
      Symmetrize.of(RandomVariate.of(distribution, 3, 3, 2));
      fail();
    } catch (Exception exception) {
      // ---
    }
  }
}
