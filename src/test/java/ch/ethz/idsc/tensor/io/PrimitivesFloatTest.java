// code by jph
package ch.ethz.idsc.tensor.io;

import java.nio.FloatBuffer;
import java.util.List;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import junit.framework.TestCase;

public class PrimitivesFloatTest extends TestCase {
  public void testToListFloat() {
    Tensor tensor = Tensors.vector(-2.5f, -2.7f);
    List<Float> list = Primitives.toListFloat(tensor);
    assertEquals(list.get(0), -2.5f);
    assertEquals(list.get(1), -2.7f);
    assertEquals(list.size(), 2);
  }

  public void testToFloatArray() {
    Tensor a = Tensors.vector(-2.5f, -2.7f);
    Tensor b = Tensors.vector(4.3f, 5.4f, 6.2f, 10.5f);
    float[] array = Primitives.toFloatArray(Tensors.of(a, b));
    assertEquals(array[0], -2.5f);
    assertEquals(array[1], -2.7f);
    assertEquals(array[2], 4.3f);
    assertEquals(array[3], 5.4f);
    assertEquals(array[4], 6.2f);
    assertEquals(array[5], 10.5f);
    assertEquals(array.length, 6);
  }

  public void testToFloatArray2D() {
    Tensor a = Tensors.vector(-2.5f, -2.7f);
    Tensor b = Tensors.vector(4.3f, 5.4f, 6.2f, 10.5f);
    float[][] array = Primitives.toFloatArray2D(Tensors.of(a, b));
    assertEquals(array[0][0], -2.5f);
    assertEquals(array[0][1], -2.7f);
    assertEquals(array[1][0], 4.3f);
    assertEquals(array[1][1], 5.4f);
    assertEquals(array[1][2], 6.2f);
    assertEquals(array[1][3], 10.5f);
    assertEquals(array.length, 2);
    assertEquals(array[0].length, 2);
    assertEquals(array[1].length, 4);
  }

  public void testToFloatBuffer() {
    Tensor a = Tensors.vector(-2.5f, -2.7f);
    Tensor b = Tensors.vector(4.3f, 5.4f, 6.2f, 10.5f);
    FloatBuffer floatBuffer = Primitives.toFloatBuffer(Tensors.of(a, b));
    assertEquals(floatBuffer.get(), -2.5f);
    assertEquals(floatBuffer.get(), -2.7f);
    assertEquals(floatBuffer.get(), 4.3f);
    assertEquals(floatBuffer.get(), 5.4f);
    assertEquals(floatBuffer.limit(), 6);
  }

  public void testToFloatArray2Dscalar() {
    try {
      Primitives.toFloatArray2D(RealScalar.of(123.456));
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }
}
