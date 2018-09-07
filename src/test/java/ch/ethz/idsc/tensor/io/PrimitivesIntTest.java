// code by jph
package ch.ethz.idsc.tensor.io;

import java.nio.IntBuffer;
import java.util.Arrays;
import java.util.List;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import junit.framework.TestCase;

public class PrimitivesIntTest extends TestCase {
  public void testToListInteger() {
    Tensor tensor = Tensors.vector(-2.5, -2.7, 4.3, 5.4, 6.2, 10.5);
    List<Integer> list = Primitives.toListInteger(tensor);
    assertEquals(Arrays.asList(-2, -2, 4, 5, 6, 10), list);
  }

  public void testToListInteger2() {
    Tensor a = Tensors.vector(-2, -3, 4, 5, 6, 11);
    Tensor b = Tensors.vector(-2.5, -3.7, 4.3, 5.4, 6.2, 11.5);
    List<Integer> listA = Primitives.toListInteger(a);
    List<Integer> listB = Primitives.toListInteger(b);
    assertEquals(a, Tensors.vector(listA));
    assertEquals(a, Tensors.vector(listB));
  }

  public void testToIntArray() {
    Tensor a = Tensors.vector(-2, -3, 4, 5, 6, 11);
    Tensor b = Tensors.vector(-2.5, -3.7, 4.3, 5.4, 6.2, 11.5);
    assertTrue(Arrays.equals(Primitives.toIntArray(a), //
        new int[] { -2, -3, 4, 5, 6, 11 }));
    assertTrue(Arrays.equals(Primitives.toIntArray(b), //
        new int[] { -2, -3, 4, 5, 6, 11 }));
  }

  public void testToIntArray2D() {
    Tensor tensor = Tensors.fromString("{{1,2},{3,{4},5},{6}}");
    int[][] array = Primitives.toIntArray2D(tensor);
    assertEquals(Tensors.vectorInt(array[0]), Tensors.vector(1, 2));
    assertEquals(Tensors.vectorInt(array[1]), Tensors.vector(3, 4, 5));
    assertEquals(Tensors.vectorInt(array[2]), Tensors.vector(6));
    assertEquals(array.length, 3);
  }

  public void testToIntArray2Dvector() {
    Tensor tensor = Tensors.fromString("{1,2,{3,{4},5},{{6},7}}");
    int[][] array = Primitives.toIntArray2D(tensor);
    assertEquals(Tensors.vectorInt(array[0]), Tensors.vector(1));
    assertEquals(Tensors.vectorInt(array[1]), Tensors.vector(2));
    assertEquals(Tensors.vectorInt(array[2]), Tensors.vector(3, 4, 5));
    assertEquals(Tensors.vectorInt(array[3]), Tensors.vector(6, 7));
    assertEquals(array.length, 4);
  }

  public void testToIntBuffer() {
    Tensor a = Tensors.vector(-2, -27, Math.PI);
    Tensor b = Tensors.vector(43, 54, 62, 105);
    IntBuffer intBuffer = Primitives.toIntBuffer(Tensors.of(a, b));
    assertEquals(intBuffer.get(), -2);
    assertEquals(intBuffer.get(), -27);
    assertEquals(intBuffer.get(), 3);
    assertEquals(intBuffer.get(), 43);
    assertEquals(intBuffer.get(), 54);
    assertEquals(intBuffer.limit(), 7);
  }

  public void testToIntArray2Dscalar() {
    try {
      Primitives.toIntArray2D(RealScalar.of(123.456));
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }
}
