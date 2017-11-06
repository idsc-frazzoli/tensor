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

  public void testIsEmpty() {
    assertTrue(Tensors.isEmpty(Tensors.empty()));
    assertFalse(Tensors.isEmpty(RealScalar.ONE));
    assertFalse(Tensors.isEmpty(Tensors.vector(3, 4)));
  }

  public void testArrayWithDimensions() {
    Tensor tensor = Tensors.fromString("{{1,2},{3,{4}},{5,6}}");
    assertFalse(Dimensions.isArrayWithDimensions(tensor, Arrays.asList(3, 2)));
  }
}
