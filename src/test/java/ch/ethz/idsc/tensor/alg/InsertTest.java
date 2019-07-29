// code by jph
package ch.ethz.idsc.tensor.alg;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import junit.framework.TestCase;

public class InsertTest extends TestCase {
  public void testAIndex0() {
    Tensor tensor = Tensors.fromString("{{1}, {2}, {3, 4}, 5, {}}");
    Tensor result = Insert.of(tensor, Tensors.fromString("{{{9}}}"), 0);
    assertEquals(result, Tensors.fromString("{{{{9}}}, {1}, {2}, {3, 4}, 5, {}}"));
  }

  public void testAIndex1() {
    Tensor tensor = Tensors.fromString("{{1}, {2}, {3, 4}, 5, {}}");
    Tensor result = Insert.of(tensor, Tensors.fromString("{{{9}}}"), 1);
    assertEquals(result, Tensors.fromString("{{1}, {{{9}}}, {2}, {3, 4}, 5, {}}"));
  }

  public void testAIndexLast() {
    Tensor tensor = Tensors.fromString("{{1}, {2}, {3, 4}, 5, {}}");
    Tensor result = Insert.of(tensor, Tensors.fromString("{{{9}}}"), 5);
    assertEquals(result, Tensors.fromString("{{1}, {2}, {3, 4}, 5, {}, {{{9}}}}"));
  }

  public void testAFailSmall() {
    Insert.of(Tensors.vector(1, 2, 3), RealScalar.ZERO, 0);
    try {
      Insert.of(Tensors.vector(1, 2, 3), RealScalar.ZERO, -1);
      fail();
    } catch (Exception exception) {
      // ---
    }
  }

  public void testAFailLarge() {
    Insert.of(Tensors.vector(1, 2, 3), RealScalar.ZERO, 3);
    try {
      Insert.of(Tensors.vector(1, 2, 3), RealScalar.ZERO, 4);
      fail();
    } catch (Exception exception) {
      // ---
    }
  }
}
