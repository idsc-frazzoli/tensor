// code by jph
package ch.ethz.idsc.tensor;

import junit.framework.TestCase;

public class UnprotectInsertTest extends TestCase {
  public void testIndex0() {
    Tensor tensor = Tensors.fromString("{{1}, {2}, {3, 4}, 5, {}}");
    Unprotect.insert(tensor, Tensors.fromString("{{{9}}}"), 0);
    assertEquals(tensor, Tensors.fromString("{{{{9}}}, {1}, {2}, {3, 4}, 5, {}}"));
  }

  public void testIndex1() {
    Tensor tensor = Tensors.fromString("{{1}, {2}, {3, 4}, 5, {}}");
    Unprotect.insert(tensor, Tensors.fromString("{{{9}}}"), 1);
    assertEquals(tensor, Tensors.fromString("{{1}, {{{9}}}, {2}, {3, 4}, 5, {}}"));
  }

  public void testIndexLast() {
    Tensor tensor = Tensors.fromString("{{1}, {2}, {3, 4}, 5, {}}");
    Unprotect.insert(tensor, Tensors.fromString("{{{9}}}"), 5);
    assertEquals(tensor, Tensors.fromString("{{1}, {2}, {3, 4}, 5, {}, {{{9}}}}"));
  }

  public void testUnmodifiableFail() {
    Tensor vector = Tensors.vector(1, 2, 3, 4);
    assertEquals(vector.length(), 4);
    Unprotect.insert(vector, RealScalar.ZERO, 2);
    try {
      Unprotect.insert(vector.unmodifiable(), RealScalar.ZERO, 2);
      fail();
    } catch (Exception exception) {
      // ---
    }
    assertEquals(vector.length(), 5);
    assertEquals(vector, Tensors.vector(1, 2, 0, 3, 4));
  }

  public void testFailSmall() {
    Unprotect.insert(Tensors.vector(1, 2, 3), RealScalar.ZERO, 0);
    try {
      Unprotect.insert(Tensors.vector(1, 2, 3), RealScalar.ZERO, -1);
      fail();
    } catch (Exception exception) {
      // ---
    }
  }

  public void testFailLarge() {
    Unprotect.insert(Tensors.vector(1, 2, 3), RealScalar.ZERO, 3);
    try {
      Unprotect.insert(Tensors.vector(1, 2, 3), RealScalar.ZERO, 4);
      fail();
    } catch (Exception exception) {
      // ---
    }
  }
}
