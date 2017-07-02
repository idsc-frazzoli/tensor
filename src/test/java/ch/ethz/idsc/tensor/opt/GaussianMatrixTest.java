// code by jph
package ch.ethz.idsc.tensor.opt;

import java.util.Arrays;

import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.alg.Dimensions;
import junit.framework.TestCase;

/** [
 * [ 0.0113 0.0838 0.0113 ]
 * [ 0.0838 0.6193 0.0838 ]
 * [ 0.0113 0.0838 0.0113 ]
 * ] */
public class GaussianMatrixTest extends TestCase {
  public void testSimple() {
    Tensor mat = GaussianMatrix.of(1);
    assertEquals(Dimensions.of(mat), Arrays.asList(3, 3));
    // System.out.println(Pretty.of(mat.map(Round._4)));
  }
}
