// code by jph
package ch.ethz.idsc.tensor.opt;

import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.io.Pretty;
import ch.ethz.idsc.tensor.sca.Round;
import junit.framework.TestCase;

/** [
 * [ 0.0113 0.0838 0.0113 ]
 * [ 0.0838 0.6193 0.0838 ]
 * [ 0.0113 0.0838 0.0113 ]
 * ] */
public class GaussianMatrixTest extends TestCase {
  public void testSimple() {
    @SuppressWarnings("unused")
    Tensor rep = GaussianMatrix.of(1);
    // TODO doesn't work yet
    System.out.println(Pretty.of(rep.map(Round._4)));
  }
}
