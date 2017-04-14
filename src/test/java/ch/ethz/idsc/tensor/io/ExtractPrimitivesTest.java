// code by jph
package ch.ethz.idsc.tensor.io;

import java.util.Arrays;
import java.util.List;

import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import junit.framework.TestCase;

public class ExtractPrimitivesTest extends TestCase {
  public void testToLong() {
    Tensor a = Tensors.vector(-2, -3, 4, 5, 6, 11);
    Tensor b = Tensors.vector(-2.5, -3.7, 4.3, 5.4, 6.2, 11.5);
    List<Long> listA = ExtractPrimitives.toListLong(a);
    List<Long> listB = ExtractPrimitives.toListLong(b);
    assertEquals(a.toString(), listA.toString());
    assertEquals(a.toString(), listB.toString());
  }

  public void testToInteger() {
    Tensor a = Tensors.vector(-2, -3, 4, 5, 6, 11);
    Tensor b = Tensors.vector(-2.5, -3.7, 4.3, 5.4, 6.2, 11.5);
    List<Integer> listA = ExtractPrimitives.toListInteger(a);
    List<Integer> listB = ExtractPrimitives.toListInteger(b);
    assertEquals(a.toString(), listA.toString());
    assertEquals(a.toString(), listB.toString());
    assertTrue(Arrays.equals(ExtractPrimitives.toArrayInt(a), //
        new int[] { -2, -3, 4, 5, 6, 11 }));
    assertTrue(Arrays.equals(ExtractPrimitives.toArrayInt(b), //
        new int[] { -2, -3, 4, 5, 6, 11 }));
  }

  public void testConvert1() {
    Tensor a = Tensors.vector(-2.5, -2.7, 4.3, 5.4, 6.2, 10.5);
    List<Double> listA = ExtractPrimitives.toListDouble(a);
    assertEquals(a.toString(), listA.toString());
  }
}
