// code by jph
package ch.ethz.idsc.tensor.usr;

import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.io.Pretty;
import ch.ethz.idsc.tensor.mat.Inverse;
import junit.framework.TestCase;

public class DemoTest extends TestCase {
  public void testEmpty() {
    // ---
  }

  public void _testReadme() {
    Tensor matrix = Tensors.matrixInt(new int[][] { { 4, 3 }, { 8, 2 } });
    System.out.println(Pretty.of(matrix));
    System.out.println(Pretty.of(Inverse.of(matrix)));
  }
}
