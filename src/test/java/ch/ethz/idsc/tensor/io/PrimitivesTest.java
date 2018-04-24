// code by jph
package ch.ethz.idsc.tensor.io;

import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.LongBuffer;
import java.util.Arrays;
import java.util.List;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import junit.framework.TestCase;

public class PrimitivesTest extends TestCase {
  public void testToLong() {
    Tensor a = Tensors.vector(-2, -3, 4, 5, 6, 11, Long.MAX_VALUE);
    Tensor b = Tensors.vector(-2.5, -3.7, 4.3, 5.4, 6.2, 11.5, Long.MAX_VALUE);
    List<Long> listA = Primitives.toListLong(a);
    List<Long> listB = Primitives.toListLong(b);
    assertEquals(a, Tensors.vector(listA));
    assertEquals(a, Tensors.vector(listB));
    long[] array = Primitives.toArrayLong(a);
    assertEquals(array[6], Long.MAX_VALUE);
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

  public void testToArrayDouble2D() {
    Tensor tensor = Tensors.fromString("{{1,2},{3,{4},5},{6}}");
    double[][] array = Primitives.toArrayDouble2D(tensor);
    assertEquals(Tensors.vectorDouble(array[0]), Tensors.vector(1, 2));
    assertEquals(Tensors.vectorDouble(array[1]), Tensors.vector(3, 4, 5));
    assertEquals(Tensors.vectorDouble(array[2]), Tensors.vector(6));
    assertEquals(array.length, 3);
  }

  public void testToArrayDouble2Dvector() {
    Tensor tensor = Tensors.fromString("{1,2,{3,{4},5},{{6},7}}");
    double[][] array = Primitives.toArrayDouble2D(tensor);
    assertEquals(Tensors.vectorDouble(array[0]), Tensors.vector(1));
    assertEquals(Tensors.vectorDouble(array[1]), Tensors.vector(2));
    assertEquals(Tensors.vectorDouble(array[2]), Tensors.vector(3, 4, 5));
    assertEquals(Tensors.vectorDouble(array[3]), Tensors.vector(6, 7));
    assertEquals(array.length, 4);
  }

  public void testToArrayDouble2Dscalar() {
    try {
      Primitives.toArrayDouble2D(RealScalar.of(123.456));
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }

  public void testToDouble() {
    Tensor tensor = Tensors.vector(Double.NaN, Math.PI, Double.POSITIVE_INFINITY);
    double[] array = Primitives.toArrayDouble(tensor);
    assertEquals(array.length, 3);
    assertTrue(Double.isNaN(array[0]));
    assertEquals(array[1], Math.PI);
    assertEquals(array[2], Double.POSITIVE_INFINITY);
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
    assertEquals(floatBuffer.limit(), 6);
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

  public void testFloatList() {
    Tensor tensor = Tensors.vector(-2.5f, -2.7f);
    List<Float> list = Primitives.toListFloat(tensor);
    assertEquals(list.get(0), -2.5f);
    assertEquals(list.get(1), -2.7f);
    assertEquals(list.size(), 2);
  }

  public void testIntBuffer() {
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

  public void testLongBuffer() {
    Tensor a = Tensors.vector(-2, -27, Math.PI);
    Tensor b = Tensors.vector(43, 54, 62, 105);
    LongBuffer longBuffer = Primitives.toLongBuffer(Tensors.of(a, b));
    assertEquals(longBuffer.get(), -2);
    assertEquals(longBuffer.get(), -27);
    assertEquals(longBuffer.get(), 3);
    assertEquals(longBuffer.get(), 43);
    assertEquals(longBuffer.get(), 54);
    assertEquals(longBuffer.limit(), 7);
  }
}
