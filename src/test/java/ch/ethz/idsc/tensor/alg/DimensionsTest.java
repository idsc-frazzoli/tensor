// code by jph
package ch.ethz.idsc.tensor.alg;

import java.util.Arrays;

import ch.ethz.idsc.tensor.DoubleScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import junit.framework.TestCase;

public class DimensionsTest extends TestCase {
  public void testScalar() {
    assertTrue(Dimensions.of(DoubleScalar.of(.123)).isEmpty());
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

  public void testIsArray() {
    Tensor d = DoubleScalar.of(.12);
    assertTrue(Dimensions.isArray(d));
    assertTrue(Dimensions.isArray(Tensors.empty()));
    Tensor a = Tensors.vectorLong(3, 2, 3);
    assertTrue(Dimensions.isArray(a));
    Tensor b = Tensors.vectorLong(3, 2);
    Tensor c = Tensors.of(a, b);
    assertFalse(Dimensions.isArray(c));
  }

  public void testIsEmpty() {
    assertTrue(Dimensions.isEmpty(Tensors.empty()));
    assertFalse(Dimensions.isEmpty(RealScalar.ONE));
    assertFalse(Dimensions.isEmpty(Tensors.vectorInt(3, 4)));
  }
}
