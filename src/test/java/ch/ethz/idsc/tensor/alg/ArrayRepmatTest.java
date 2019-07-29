// code by jph
package ch.ethz.idsc.tensor.alg;

import java.util.Arrays;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import junit.framework.TestCase;

public class ArrayRepmatTest extends TestCase {
  public void testRepmat() {
    Tensor vector = Tensors.vector(1, 2, 3);
    Tensor repmat = Array.repmat(vector, 2, 3, 4);
    assertTrue(Tensors.isUnmodifiable(repmat));
    assertEquals(Dimensions.of(repmat), Arrays.asList(2, 3, 4, 3));
    Tensor tensor = repmat.copy();
    assertEquals(repmat, tensor);
    assertEquals(repmat.hashCode(), tensor.hashCode());
    assertFalse(Tensors.isUnmodifiable(tensor));
    tensor.set(RealScalar.ONE::add, Tensor.ALL, Tensor.ALL, Tensor.ALL, Tensor.ALL);
    assertEquals(Array.repmat(Tensors.vector(2, 3, 4), 2, 3, 4), tensor);
  }

  public void testNCopies() {
    Tensor tensor = Array.repmat(Tensors.vector(1, 2, 3), 6);
    assertTrue(Tensors.isUnmodifiable(tensor));
    assertEquals(tensor.length(), 6);
    assertEquals(tensor.get(1), Tensors.vector(1, 2, 3));
    assertEquals(tensor.Get(1, 2), RealScalar.of(3));
    try {
      tensor.set(s -> s, 0, 0);
      fail();
    } catch (Exception exception) {
      // ---
    }
    try {
      tensor.append(RealScalar.ONE);
      fail();
    } catch (Exception exception) {
      // ---
    }
  }

  public void testNCopiesScalar() {
    Tensor tensor = Array.repmat(RealScalar.of(3), 6);
    assertTrue(Tensors.isUnmodifiable(tensor));
    assertEquals(tensor.length(), 6);
    assertEquals(tensor.get(1), RealScalar.of(3));
    assertEquals(tensor.extract(2, 5), Tensors.vector(3, 3, 3));
    try {
      tensor.set(s -> s, 0);
      fail();
    } catch (Exception exception) {
      // ---
    }
    try {
      tensor.append(RealScalar.ONE);
      fail();
    } catch (Exception exception) {
      // ---
    }
  }

  public void testFailRepmatNull() {
    try {
      Array.repmat(null, 6, 3);
      fail();
    } catch (Exception exception) {
      // ---
    }
  }
}
