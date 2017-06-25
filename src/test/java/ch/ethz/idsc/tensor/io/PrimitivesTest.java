// code by jph
package ch.ethz.idsc.tensor.io;

import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.Arrays;
import java.util.List;

import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import junit.framework.TestCase;

public class PrimitivesTest extends TestCase {
  public void testToLong() {
    Tensor a = Tensors.vector(-2, -3, 4, 5, 6, 11);
    Tensor b = Tensors.vector(-2.5, -3.7, 4.3, 5.4, 6.2, 11.5);
    List<Long> listA = Primitives.toListLong(a);
    List<Long> listB = Primitives.toListLong(b);
    assertEquals(a, Tensors.vector(listA));
    assertEquals(a, Tensors.vector(listB));
  }

  public void testToInteger() {
    Tensor a = Tensors.vector(-2, -3, 4, 5, 6, 11);
    Tensor b = Tensors.vector(-2.5, -3.7, 4.3, 5.4, 6.2, 11.5);
    List<Integer> listA = Primitives.toListInteger(a);
    List<Integer> listB = Primitives.toListInteger(b);
    assertEquals(a, Tensors.vector(listA));
    assertEquals(a, Tensors.vector(listB));
    assertTrue(Arrays.equals(Primitives.toArrayInt(a), //
        new int[] { -2, -3, 4, 5, 6, 11 }));
    assertTrue(Arrays.equals(Primitives.toArrayInt(b), //
        new int[] { -2, -3, 4, 5, 6, 11 }));
  }

  public void testConvert1() {
    Tensor a = Tensors.vector(-2.5, -2.7, 4.3, 5.4, 6.2, 10.5);
    List<Double> listA = Primitives.toListDouble(a);
    assertEquals(a, Tensors.vector(listA));
  }

  public void testDoubleBuffer() {
    Tensor a = Tensors.vector(-2.5, -2.7, Math.PI);
    Tensor b = Tensors.vector(4.3, 5.4, 6.2, 10.5);
    DoubleBuffer doubleBuffer = Primitives.toDoubleBuffer(Tensors.of(a, b));
    assertEquals(doubleBuffer.get(), -2.5);
    assertEquals(doubleBuffer.get(), -2.7);
    assertEquals(doubleBuffer.get(), Math.PI);
    assertEquals(doubleBuffer.get(), 4.3);
    assertEquals(doubleBuffer.get(), 5.4);
  }

  public void testFloatBuffer() {
    Tensor a = Tensors.vector(-2.5f, -2.7f);
    Tensor b = Tensors.vector(4.3f, 5.4f, 6.2f, 10.5f);
    FloatBuffer floatBuffer = Primitives.toFloatBuffer(Tensors.of(a, b));
    assertEquals(floatBuffer.get(), -2.5f);
    assertEquals(floatBuffer.get(), -2.7f);
    assertEquals(floatBuffer.get(), 4.3f);
    assertEquals(floatBuffer.get(), 5.4f);
  }

  public void testFloatArray() {
    Tensor a = Tensors.vector(-2.5f, -2.7f);
    Tensor b = Tensors.vector(4.3f, 5.4f, 6.2f, 10.5f);
    float[] array = Primitives.toArrayFloat(Tensors.of(a, b));
    assertEquals(array[0], -2.5f);
    assertEquals(array[1], -2.7f);
    assertEquals(array[2], 4.3f);
    assertEquals(array[3], 5.4f);
    assertEquals(array[4], 6.2f);
    assertEquals(array[5], 10.5f);
    assertEquals(array.length, 6);
  }

  public void testIntBuffer() {
    Tensor a = Tensors.vector(-2, -27, Math.PI);
    Tensor b = Tensors.vector(43, 54, 62, 105);
    IntBuffer doubleBuffer = Primitives.toIntBuffer(Tensors.of(a, b));
    assertEquals(doubleBuffer.get(), -2);
    assertEquals(doubleBuffer.get(), -27);
    assertEquals(doubleBuffer.get(), 3);
    assertEquals(doubleBuffer.get(), 43);
    assertEquals(doubleBuffer.get(), 54);
  }
}
