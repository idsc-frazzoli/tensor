// code by jph
package ch.ethz.idsc.tensor.mat;

import java.util.Arrays;

import ch.ethz.idsc.tensor.RationalScalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.alg.Array;
import ch.ethz.idsc.tensor.alg.Dimensions;
import ch.ethz.idsc.tensor.alg.Reverse;
import junit.framework.TestCase;

public class NullSpaceTest extends TestCase {
  public void testZerosUsingSvd() {
    int n = 5;
    // gives the symbolic identity matrix
    Tensor nul = NullSpace.usingSvd(Array.zeros(n, n));
    assertEquals(Dimensions.of(nul), Arrays.asList(n, n));
    assertTrue(nul.get(0, 0) instanceof RationalScalar);
    assertEquals(nul, IdentityMatrix.of(n));
    // System.out.println(Pretty.of(nul));
    // System.out.println(Pretty.of(N.of(nul)));
  }

  public void testRowReduce() {
    Tensor m = Tensors.fromString("{{1, 2, 3, 4}, {5, 6, 7, 8}, {9, 10, 11, 12}, {13, 14, 15, 16}}");
    Tensor r = NullSpace.of(m);
    assertEquals(r, Tensors.fromString("{{1, 0, -3, 2}, {0, 1, -2, 1}}"));
    assertEquals(m.dot(r.get(0)), Array.zeros(4));
    assertEquals(m.dot(r.get(1)), Array.zeros(4));
  }

  public void testZeros2() {
    Tensor m = Array.zeros(5, 5);
    Tensor r = NullSpace.of(m);
    assertEquals(r, IdentityMatrix.of(5));
  }

  public void testIdentity() {
    Tensor m = IdentityMatrix.of(5);
    Tensor r = NullSpace.of(m);
    assertEquals(r, Tensors.empty());
  }

  public void testIdentityReversed() {
    Tensor m = Reverse.of(IdentityMatrix.of(5));
    Tensor r = NullSpace.of(m);
    assertEquals(r, Tensors.empty());
  }
}
