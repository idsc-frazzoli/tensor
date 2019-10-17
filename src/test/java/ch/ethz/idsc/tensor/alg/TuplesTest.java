// code by jph
package ch.ethz.idsc.tensor.alg;

import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.opt.Pi;
import junit.framework.TestCase;

public class TuplesTest extends TestCase {
  public void testSimple0() {
    Tensor tuples = Tuples.of(Tensors.vector(3, 4, 5), 0);
    assertEquals(tuples, Tensors.empty());
  }

  public void testOne() {
    Tensor tuples = Tuples.of(Tensors.vector(3, 4, 5), 1);
    Tensor actual = Tensors.fromString("{{3}, {4}, {5}}");
    assertEquals(tuples, actual);
  }

  public void testTwo() {
    Tensor tuples = Tuples.of(Tensors.vector(3, 4, 5), 2);
    Tensor actual = //
        Tensors.fromString("{{3, 3}, {3, 4}, {3, 5}, {4, 3}, {4, 4}, {4, 5}, {5, 3}, {5, 4}, {5, 5}}");
    assertEquals(tuples, actual);
  }

  public void testThree() {
    Tensor tuples = Tuples.of(Tensors.vector(4, 5), 3);
    Tensor actual = //
        Tensors.fromString("{{4, 4, 4}, {4, 4, 5}, {4, 5, 4}, {4, 5, 5}, {5, 4, 4}, {5, 4, 5}, {5, 5, 4}, {5, 5, 5}}");
    assertEquals(tuples, actual);
  }

  public void testFailNegative() {
    try {
      Tuples.of(Tensors.vector(1, 2, 3), -1);
      fail();
    } catch (Exception exception) {
      // ---
    }
  }

  public void testFailScalar() {
    try {
      Tuples.of(Pi.VALUE, 2);
      fail();
    } catch (Exception exception) {
      // ---
    }
  }
}
