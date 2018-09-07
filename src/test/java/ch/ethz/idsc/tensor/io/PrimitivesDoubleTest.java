// code by jph
package ch.ethz.idsc.tensor.io;

import java.nio.DoubleBuffer;
import java.util.List;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import junit.framework.TestCase;

public class PrimitivesDoubleTest extends TestCase {
  public void testToListDouble() {
    Tensor a = Tensors.vector(-2.5, -2.7, 4.3, 5.4, 6.2, 10.5);
    List<Double> listA = Primitives.toListDouble(a);
    assertEquals(a, Tensors.vector(listA));
  }

  public void testToDoubleArray() {
    Tensor tensor = Tensors.vector(Double.NaN, Math.PI, Double.POSITIVE_INFINITY);
    double[] array = Primitives.toDoubleArray(tensor);
    assertEquals(array.length, 3);
    assertTrue(Double.isNaN(array[0]));
    assertEquals(array[1], Math.PI);
    assertEquals(array[2], Double.POSITIVE_INFINITY);
  }

  public void testToDoubleArray2D() {
    Tensor tensor = Tensors.fromString("{{1,2},{3,{4},5},{6}}");
    double[][] array = Primitives.toDoubleArray2D(tensor);
    assertEquals(Tensors.vectorDouble(array[0]), Tensors.vector(1, 2));
    assertEquals(Tensors.vectorDouble(array[1]), Tensors.vector(3, 4, 5));
    assertEquals(Tensors.vectorDouble(array[2]), Tensors.vector(6));
    assertEquals(array.length, 3);
  }

  public void testToDoubleArray2Dvector() {
    Tensor tensor = Tensors.fromString("{1,2,{3,{4},5},{{6},7}}");
    double[][] array = Primitives.toDoubleArray2D(tensor);
    assertEquals(Tensors.vectorDouble(array[0]), Tensors.vector(1));
    assertEquals(Tensors.vectorDouble(array[1]), Tensors.vector(2));
    assertEquals(Tensors.vectorDouble(array[2]), Tensors.vector(3, 4, 5));
    assertEquals(Tensors.vectorDouble(array[3]), Tensors.vector(6, 7));
    assertEquals(array.length, 4);
  }

  public void testToDoubleBuffer() {
    Tensor a = Tensors.vector(-2.5, -2.7, Math.PI);
    Tensor b = Tensors.vector(4.3, 5.4, 6.2, 10.5);
    DoubleBuffer doubleBuffer = Primitives.toDoubleBuffer(Tensors.of(a, b));
    assertEquals(doubleBuffer.get(), -2.5);
    assertEquals(doubleBuffer.get(), -2.7);
    assertEquals(doubleBuffer.get(), Math.PI);
    assertEquals(doubleBuffer.get(), 4.3);
    assertEquals(doubleBuffer.get(), 5.4);
  }

  public void testToDoubleArray2Dscalar() {
    try {
      Primitives.toDoubleArray2D(RealScalar.of(123.456));
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }
}
