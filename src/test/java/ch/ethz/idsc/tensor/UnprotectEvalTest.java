// code by jph
package ch.ethz.idsc.tensor;

import java.util.List;

import junit.framework.TestCase;

public class UnprotectEvalTest extends TestCase {
  public void testIndex0() {
    Tensor tensor = Tensors.fromString("{{1}, {2}, {3, 4}, 5, {}}");
    UnprotectEval.insert(tensor, Tensors.fromString("{{{9}}}"), 0);
    assertEquals(tensor, Tensors.fromString("{{{{9}}}, {1}, {2}, {3, 4}, 5, {}}"));
  }

  public void testIndex1() {
    Tensor tensor = Tensors.fromString("{{1}, {2}, {3, 4}, 5, {}}");
    UnprotectEval.insert(tensor, Tensors.fromString("{{{9}}}"), 1);
    assertEquals(tensor, Tensors.fromString("{{1}, {{{9}}}, {2}, {3, 4}, 5, {}}"));
  }

  public void testIndexLast() {
    Tensor tensor = Tensors.fromString("{{1}, {2}, {3, 4}, 5, {}}");
    UnprotectEval.insert(tensor, Tensors.fromString("{{{9}}}"), 5);
    assertEquals(tensor, Tensors.fromString("{{1}, {2}, {3, 4}, 5, {}, {{{9}}}}"));
  }

  public void testReserve() {
    Tensor tensor = Tensors.reserve(100);
    assertEquals(tensor, Tensors.empty());
    assertEquals(tensor, Tensors.unmodifiableEmpty());
    assertFalse(Tensors.isUnmodifiable(tensor));
    List<Tensor> list = UnprotectEval.list(tensor);
    list.add(RealScalar.ONE);
    assertEquals(tensor.length(), 1);
  }

  public void testList() {
    Tensor tensor = Tensors.vector(3, 2, 0, 1.234);
    List<Tensor> list = UnprotectEval.list(tensor);
    assertEquals(list.get(0), RealScalar.of(3));
    assertEquals(list.get(3), RealScalar.of(1.234));
  }

  public void testListFail() {
    Tensor tensor = Tensors.vector(3, 2, 0, 1.234).unmodifiable();
    try {
      UnprotectEval.list(tensor);
      fail();
    } catch (Exception exception) {
      // ---
    }
  }

  public void testUnmodifiableFail() {
    Tensor vector = Tensors.vector(1, 2, 3, 4);
    assertEquals(vector.length(), 4);
    UnprotectEval.insert(vector, RealScalar.ZERO, 2);
    try {
      UnprotectEval.insert(vector.unmodifiable(), RealScalar.ZERO, 2);
      fail();
    } catch (Exception exception) {
      // ---
    }
    assertEquals(vector.length(), 5);
    assertEquals(vector, Tensors.vector(1, 2, 0, 3, 4));
  }

  public void testFailSmall() {
    UnprotectEval.insert(Tensors.vector(1, 2, 3), RealScalar.ZERO, 0);
    try {
      UnprotectEval.insert(Tensors.vector(1, 2, 3), RealScalar.ZERO, -1);
      fail();
    } catch (Exception exception) {
      // ---
    }
  }

  public void testFailLarge() {
    UnprotectEval.insert(Tensors.vector(1, 2, 3), RealScalar.ZERO, 3);
    try {
      UnprotectEval.insert(Tensors.vector(1, 2, 3), RealScalar.ZERO, 4);
      fail();
    } catch (Exception exception) {
      // ---
    }
  }
}
