// code by jph
package ch.ethz.idsc.tensor;

import java.util.Iterator;

import ch.ethz.idsc.tensor.mat.IdentityMatrix;
import junit.framework.TestCase;

public class TensorImplTest extends TestCase {
  public void testUnmodifiable() {
    Tensor eye = IdentityMatrix.of(4).unmodifiable();
    try {
      eye.flatten(0).forEach(e -> e.set(RealScalar.of(4), 2));
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }

  public void testIteratorSize() {
    int count = 0;
    for (Tensor scalar : Tensors.vector(4, 2, 6, 3, 8).unmodifiable()) {
      scalar.add(scalar);
      ++count;
    }
    assertEquals(count, 5);
  }

  public void testCopy() {
    Tensor eye = IdentityMatrix.of(4).unmodifiable().copy();
    eye.flatten(0).forEach(e -> e.set(RealScalar.of(4), 2));
    assertEquals(eye.get(Tensor.ALL, 2), Tensors.vector(4, 4, 4, 4));
  }

  public void testIteratorUnmod() {
    Tensor eye = IdentityMatrix.of(4).unmodifiable();
    for (Tensor unit : eye)
      try {
        unit.set(RealScalar.of(4), 2);
        assertTrue(false);
      } catch (Exception exception) {
        // ---
      }
    assertEquals(eye.get(Tensor.ALL, 2), Tensors.vector(0, 0, 1, 0));
  }

  public void testIteratorUnmod2() {
    Tensor eye = IdentityMatrix.of(4).unmodifiable();
    Tensor rep = Tensors.empty();
    for (Tensor unit : eye)
      rep.append(unit);
    assertEquals(eye, rep);
  }

  public void testIteratorUnmod3() {
    Tensor eye = IdentityMatrix.of(4).unmodifiable();
    for (Tensor unit : eye)
      try {
        unit.append(RealScalar.ZERO);
        assertTrue(false);
      } catch (Exception exception) {
        // ---
      }
  }

  public void testIteratorRemove() {
    Tensor tensor = IdentityMatrix.of(4);
    Iterator<Tensor> iterator = tensor.iterator();
    while (iterator.hasNext()) {
      iterator.next();
      iterator.remove();
    }
    assertEquals(tensor, Tensors.empty());
  }

  public void testIteratorCopy() {
    Tensor eye = IdentityMatrix.of(4).unmodifiable().copy();
    for (Tensor unit : eye)
      unit.set(RealScalar.of(4), 2);
    assertEquals(eye.get(Tensor.ALL, 2), Tensors.vector(4, 4, 4, 4));
  }

  public void testExtract() {
    Tensor eye = IdentityMatrix.of(4).unmodifiable();
    eye.extract(2, 4).set(RealScalar.of(4), 1);
  }
}
