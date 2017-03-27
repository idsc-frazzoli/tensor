// code by jph
package ch.ethz.idsc.tensor.mat;

import java.util.Arrays;

import ch.ethz.idsc.tensor.ExactPrecision;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.alg.Array;
import ch.ethz.idsc.tensor.alg.Dimensions;
import junit.framework.TestCase;

public class NullSpaceTest extends TestCase {
  public void testZeros() {
    int n = 5;
    // gives the symbolic identity matrix
    Tensor nul = NullSpace.of(Array.zeros(n, n));
    assertEquals(Dimensions.of(nul), Arrays.asList(n, n));
    assertTrue(nul.get(0, 0) instanceof ExactPrecision);
    assertEquals(nul, IdentityMatrix.of(n));
    // System.out.println(Pretty.of(nul));
    // System.out.println(Pretty.of(N.of(nul)));
  }
}
