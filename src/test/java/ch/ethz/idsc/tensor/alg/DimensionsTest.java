// code by jph
package ch.ethz.idsc.tensor.alg;

import java.util.Arrays;
import java.util.HashSet;

import ch.ethz.idsc.tensor.DoubleScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.mat.HilbertMatrix;
import junit.framework.TestCase;

public class DimensionsTest extends TestCase {
  public void testScalar() {
    assertTrue(Dimensions.of(DoubleScalar.of(0.123)).isEmpty());
  }

  public void testEmpty() {
    assertEquals(Dimensions.of(Tensors.empty()), Arrays.asList(0));
  }

  public void testVectors() {
    Tensor a = Tensors.vectorLong(1, 2, 3);
    assertEquals(Dimensions.of(a), Arrays.asList(3));
    Tensor b = Tensors.vectorLong(1, 2, 2);
    Tensor e = Tensors.of(a, b);
    assertEquals(Dimensions.of(e), Arrays.asList(2, 3));
  }

  public void testDimensions4() {
    Tensor a = Tensors.vectorLong(1, 2);
    Tensor b = Tensors.vectorLong(3, 4, 5);
    Tensor c = Tensors.vectorLong(6);
    Tensor d = Tensors.of(a, b, c);
    Tensor e = Tensors.of(a, b);
    Tensor f = Tensors.of(d, e);
    assertEquals(Dimensions.of(f), Arrays.asList(2));
    Tensor g = Tensors.of(d, d, d, d);
    assertEquals(Dimensions.of(g), Arrays.asList(4, 3));
  }

  public void testDimensions5() {
    Tensor a = DoubleScalar.of(2.32123);
    Tensor b = Tensors.vectorLong(3, 2);
    Tensor c = DoubleScalar.of(1.23);
    Tensor d = Tensors.of(a, b, c);
    assertEquals(Dimensions.of(d), Arrays.asList(3));
  }

  public void testIsEmpty() {
    assertTrue(Tensors.isEmpty(Tensors.empty()));
    assertFalse(Tensors.isEmpty(RealScalar.ONE));
    assertFalse(Tensors.isEmpty(Tensors.vector(3, 4)));
  }

  public void testDepth() {
    assertEquals(new Dimensions(RealScalar.ONE).maxDepth(), 0);
    assertEquals(new Dimensions(UnitVector.of(3, 2)).maxDepth(), 1);
    assertEquals(new Dimensions(HilbertMatrix.of(2, 3)).maxDepth(), 2);
    Tensor tensor = Tensors.fromString("{{{2, 3}, {{}}}, {4, 5, 7}, 3}");
    assertEquals(new Dimensions(tensor).maxDepth(), 3);
  }

  public void testLengths() {
    Tensor tensor = Tensors.fromString("{{{2, 3}, {{}}}, {4, 5, 7}, 3}");
    Dimensions dimensions = new Dimensions(tensor);
    assertEquals(dimensions.lengths(0), new HashSet<>(Arrays.asList(3)));
    assertEquals(dimensions.lengths(1), new HashSet<>(Arrays.asList(Scalar.LENGTH, 2, 3)));
    assertEquals(dimensions.lengths(2), new HashSet<>(Arrays.asList(Scalar.LENGTH, 1, 2)));
    assertEquals(dimensions.lengths(3), new HashSet<>(Arrays.asList(Scalar.LENGTH, 0)));
  }

  public void testLengthsFail() {
    Tensor tensor = Tensors.fromString("{{{2, 3}, {{}}}, {4, 5, 7}, 3}");
    Dimensions dimensions = new Dimensions(tensor);
    try {
      dimensions.lengths(-1);
      fail();
    } catch (Exception exception) {
      // ---
    }
    dimensions.lengths(dimensions.maxDepth());
    try {
      dimensions.lengths(dimensions.maxDepth() + 1);
      fail();
    } catch (Exception exception) {
      // ---
    }
    try {
      dimensions.lengths(0).add(1);
      fail();
    } catch (Exception exception) {
      // ---
    }
  }
}
