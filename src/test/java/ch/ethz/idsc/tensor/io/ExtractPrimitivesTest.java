// code by jph
package ch.ethz.idsc.tensor.io;

import java.util.Arrays;
import java.util.List;

import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import junit.framework.TestCase;

public class ExtractPrimitivesTest extends TestCase {
  public void testToLong() {
    Tensor a = Tensors.vectorLong(-2, -3, 4, 5, 6, 11);
    Tensor b = Tensors.vectorDouble(-2.5, -3.7, 4.3, 5.4, 6.2, 11.5);
    List<Long> listA = ExtractPrimitives.vectorToListLong(a);
    List<Long> listB = ExtractPrimitives.vectorToListLong(b);
    assertEquals(a.toString(), listA.toString());
    assertEquals(a.toString(), listB.toString());
  }

  public void testToInteger() {
    Tensor a = Tensors.vectorInt(-2, -3, 4, 5, 6, 11);
    Tensor b = Tensors.vectorDouble(-2.5, -3.7, 4.3, 5.4, 6.2, 11.5);
    List<Integer> listA = ExtractPrimitives.vectorToListInteger(a);
    List<Integer> listB = ExtractPrimitives.vectorToListInteger(b);
    assertEquals(a.toString(), listA.toString());
    assertEquals(a.toString(), listB.toString());
    assertTrue(Arrays.equals(ExtractPrimitives.vectorToArrayInt(a), //
        new int[] { -2, -3, 4, 5, 6, 11 }));
    assertTrue(Arrays.equals(ExtractPrimitives.vectorToArrayInt(b), //
        new int[] { -2, -3, 4, 5, 6, 11 }));
  }

  public void testConvert1() {
    Tensor a = Tensors.vectorDouble(-2.5, -2.7, 4.3, 5.4, 6.2, 10.5);
    List<Double> listA = ExtractPrimitives.vectorToListDouble(a);
    assertEquals(a.toString(), listA.toString());
  }
}
