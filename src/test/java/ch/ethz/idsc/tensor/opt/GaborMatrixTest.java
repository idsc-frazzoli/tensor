// code by jph
package ch.ethz.idsc.tensor.opt;

import java.util.Arrays;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.alg.Dimensions;
import junit.framework.TestCase;

public class GaborMatrixTest extends TestCase {
  public void testDimensions() {
    Tensor mat = GaborMatrix.of(2, Tensors.vector(.2, .1), RealScalar.of(.2));
    // System.out.println(Pretty.of(mat.map(Round._4)));
    assertEquals(Dimensions.of(mat), Arrays.asList(5, 5));
  }
}
