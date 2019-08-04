// code by jph
package ch.ethz.idsc.tensor.alg;

import java.util.Arrays;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import junit.framework.TestCase;

public class ConstantArrayTest extends TestCase {
  public void testRepmat() {
    Tensor vector = Tensors.vector(1, 2, 3);
    Tensor repmat = ConstantArray.of(vector, 2, 3, 4);
    assertTrue(Tensors.isUnmodifiable(repmat));
    assertEquals(Dimensions.of(repmat), Arrays.asList(2, 3, 4, 3));
    Tensor tensor = repmat.copy();
    assertEquals(repmat, tensor);
    assertEquals(repmat.hashCode(), tensor.hashCode());
    assertFalse(Tensors.isUnmodifiable(tensor));
    tensor.set(RealScalar.ONE::add, Tensor.ALL, Tensor.ALL, Tensor.ALL, Tensor.ALL);
    assertEquals(ConstantArray.of(Tensors.vector(2, 3, 4), 2, 3, 4), tensor);
  }

  public void testSingle() {
    Tensor vector = Tensors.vector(1, 2, 3);
    Tensor repmat = ConstantArray.of(vector);
    assertTrue(Tensors.isUnmodifiable(repmat));
    assertEquals(Dimensions.of(repmat), Arrays.asList(3));
  }

  public void testScalar() {
    Tensor repmat = ConstantArray.of(RealScalar.ZERO);
    assertTrue(repmat instanceof Scalar);
    assertEquals(Dimensions.of(repmat), Arrays.asList());
  }

  public void testZeros() {
    Tensor repmat = ConstantArray.of(RealScalar.ZERO, 2, 4, 1);
    Tensor zeros = Array.zeros(2, 4, 1);
    assertEquals(repmat, zeros);
  }

  public void testNCopies() {
    Tensor tensor = ConstantArray.of(Tensors.vector(1, 2, 3), 6);
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
    Tensor tensor = ConstantArray.of(RealScalar.of(3), 6);
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

  public void testFailNull() {
    try {
      ConstantArray.of(null, 6, 3);
      fail();
    } catch (Exception exception) {
      // ---
    }
  }

  public void testFailNegative() {
    Tensor repmat = ConstantArray.of(RealScalar.ONE, 6, 0, 3);
    assertEquals(Dimensions.of(repmat), Arrays.asList(6, 0));
    assertEquals(repmat, Tensors.fromString("{{}, {}, {}, {}, {}, {}}"));
    try {
      ConstantArray.of(RealScalar.ONE, 6, -1, 3);
      fail();
    } catch (Exception exception) {
      // ---
    }
  }
}
