// code by jph
package ch.ethz.idsc.tensor.opt;

import ch.ethz.idsc.tensor.ExactTensorQ;
import ch.ethz.idsc.tensor.RationalScalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.alg.UnitVector;
import junit.framework.TestCase;

public class LinearBinaryAverageTest extends TestCase {
  public void testSimple() {
    Tensor tensor = LinearBinaryAverage.INSTANCE.split(UnitVector.of(3, 1), UnitVector.of(3, 2), RationalScalar.of(1, 3));
    assertEquals(ExactTensorQ.require(tensor), Tensors.fromString("{0, 2/3, 1/3}"));
  }
}
