// code by jph
package ch.ethz.idsc.tensor.alg;

import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.opt.Pi;
import junit.framework.TestCase;

public class SubsetsTest extends TestCase {
  public void testCardinality() {
    Tensor vector = Tensors.vector(3, 4, 5, 6);
    for (int k = 0; k <= vector.length(); ++k)
      assertEquals(Subsets.of(vector, k).length(), Binomial.of(4, k).number().intValue());
  }

  public void testZero() {
    Tensor tensor = Subsets.of(Tensors.vector(2, 3, 4), 0);
    assertEquals(tensor, Tensors.fromString("{{}}"));
  }

  public void testOne() {
    Tensor tensor = Subsets.of(Tensors.vector(2, 3, 4), 1);
    assertEquals(tensor, Tensors.fromString("{{2}, {3}, {4}}"));
  }

  public void testTwo() {
    Tensor tensor = Subsets.of(Tensors.vector(2, 3, 4), 2);
    assertEquals(tensor, Tensors.fromString("{{2, 3}, {2, 4}, {3, 4}}"));
  }

  public void testThree() {
    Tensor tensor = Subsets.of(Tensors.vector(2, 3, 4), 3);
    assertEquals(tensor, Tensors.fromString("{{2, 3, 4}}"));
  }

  public void testNegativeFail() {
    try {
      Subsets.of(Tensors.vector(2, 3, 4), -1);
      fail();
    } catch (Exception exception) {
      // ---
    }
  }

  public void testScalarFail() {
    try {
      Subsets.of(Pi.HALF, 2);
      fail();
    } catch (Exception exception) {
      // ---
    }
  }
}
