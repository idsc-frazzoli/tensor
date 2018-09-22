// code by jph
package ch.ethz.idsc.tensor;

import java.util.Iterator;

import ch.ethz.idsc.tensor.mat.IdentityMatrix;
import junit.framework.TestCase;

public class UnmodifiableTensorTest extends TestCase {
  public void testUnmodificableEmptyEquals() {
    assertTrue(Tensors.unmodifiableEmpty() == Tensors.unmodifiableEmpty());
    assertTrue(Tensors.unmodifiableEmpty() != Tensors.empty());
    assertTrue(Tensors.unmodifiableEmpty() != Tensors.empty().unmodifiable());
  }

  public void testUnmodifiable() {
    Tensor tensor = Tensors.vector(3, 4, 5, 6, -2);
    tensor.set(DoubleScalar.of(.3), 2);
    Tensor unmodi = tensor.unmodifiable();
    assertEquals(tensor, unmodi);
    try {
      unmodi.set(DoubleScalar.of(.3), 2);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
    try {
      unmodi.append(Tensors.empty());
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
    try {
      unmodi.set(t -> t.append(RealScalar.ZERO));
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
    Tensor dot = unmodi.dot(unmodi);
    assertEquals(dot, DoubleScalar.of(65.09));
    assertEquals(DoubleScalar.of(65.09), dot);
  }

  public void testUnmodifiable2() {
    Tensor matrix = Tensors.matrixInt(new int[][] { { 1, 2 }, { 3, 4 } }).unmodifiable();
    Tensor copy = matrix.copy();
    matrix.get(1).set(RealScalar.ZERO, 1);
    assertEquals(matrix, copy);
  }

  public void testHashUnmod() {
    Tensor a = Tensors.of(Tensors.vectorLong(2, -81, 7, 2, 8), Tensors.vector(32, 3.123));
    Tensor b = a.unmodifiable();
    assertEquals(a, b);
    assertEquals(a.hashCode(), b.hashCode());
  }

  public void testHashUnmodVector() {
    Tensor a = Tensors.vector(2, -81, 7, 2, 8, 3.123);
    Tensor b = a.unmodifiable();
    assertEquals(a, b);
    assertEquals(a.hashCode(), b.hashCode());
  }

  public void testHashUnmodEmpty() {
    Tensor a = Tensors.vector();
    Tensor b = Tensors.empty().unmodifiable();
    assertEquals(a, b);
    assertEquals(a.hashCode(), b.hashCode());
  }

  public void testUnmodifiableSet() {
    Tensor eye = IdentityMatrix.of(3).unmodifiable();
    try {
      eye.set(RealScalar.ZERO, 2, 2);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }

  public void testUnmodifiableIterator() {
    Tensor eye = IdentityMatrix.of(3).unmodifiable();
    Iterator<Tensor> iterator = eye.iterator();
    iterator.next();
    try {
      iterator.remove();
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }
}
