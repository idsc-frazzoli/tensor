// code by jph
package ch.ethz.idsc.tensor.io;

import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.LongBuffer;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.alg.Numel;

/** the extraction of primitive data types from a {@link Tensor}
 * only works for tensors with {@link Scalar} entries
 * that all support the operation {@link Scalar#number()} */
public enum Primitives {
  ;
  /** @param tensor
   * @return stream of all scalars in tensor mapped to {@link Number} */
  public static Stream<Number> toStreamNumber(Tensor tensor) {
    return tensor.flatten(-1).map(Scalar.class::cast).map(Scalar::number);
  }

  /***************************************************/
  /** @param tensor
   * @return list of double values of all scalars in tensor */
  public static List<Double> toListDouble(Tensor tensor) {
    return toStreamNumber(tensor).map(Number::doubleValue).collect(Collectors.toList());
  }

  /** @param tensor
   * @return array of double values of all scalars in tensor */
  public static double[] toDoubleArray(Tensor tensor) {
    return toStreamNumber(tensor).mapToDouble(Number::doubleValue).toArray();
  }

  /** @param tensor
   * @return 2-dimensional array of double's with first dimension equal to tensor.length() */
  public static double[][] toDoubleArray2D(Tensor tensor) {
    double[][] array = new double[tensor.length()][];
    int index = -1;
    for (Tensor entry : tensor)
      array[++index] = toDoubleArray(entry);
    return array;
  }

  /** @param tensor
   * @return */
  public static DoubleBuffer toDoubleBuffer(Tensor tensor) {
    DoubleBuffer doubleBuffer = DoubleBuffer.allocate(Numel.of(tensor));
    toStreamNumber(tensor).mapToDouble(Number::doubleValue).forEach(doubleBuffer::put);
    doubleBuffer.flip();
    return doubleBuffer;
  }

  /***************************************************/
  /** @param tensor
   * @return list of double values of all scalars in tensor */
  public static List<Float> toListFloat(Tensor tensor) {
    return toStreamNumber(tensor).map(Number::floatValue).collect(Collectors.toList());
  }

  /** @param tensor
   * @return array of double values of all scalars in tensor */
  public static float[] toFloatArray(Tensor tensor) {
    return toFloatBuffer(tensor).array();
  }

  /** @param tensor
   * @return 2-dimensional array of float's with first dimension equal to tensor.length() */
  public static float[][] toFloatArray2D(Tensor tensor) {
    float[][] array = new float[tensor.length()][];
    int index = -1;
    for (Tensor entry : tensor)
      array[++index] = toFloatArray(entry);
    return array;
  }

  /** @param tensor
   * @return */
  public static FloatBuffer toFloatBuffer(Tensor tensor) {
    FloatBuffer floatBuffer = FloatBuffer.allocate(Numel.of(tensor));
    toStreamNumber(tensor).map(Number::floatValue).forEach(floatBuffer::put);
    floatBuffer.flip();
    return floatBuffer;
  }

  /***************************************************/
  /** does not perform rounding, but uses Scalar::number().longValue()
   * 
   * @param tensor
   * @return list of long values of all scalars in tensor */
  public static List<Long> toListLong(Tensor tensor) {
    return toStreamNumber(tensor).map(Number::longValue).collect(Collectors.toList());
  }

  /** does not perform rounding, but uses Scalar::number().longValue()
   * 
   * @param tensor
   * @return array of long values of all scalars in tensor */
  public static long[] toLongArray(Tensor tensor) {
    return toStreamNumber(tensor).mapToLong(Number::longValue).toArray();
  }

  /** @param tensor
   * @return */
  public static LongBuffer toLongBuffer(Tensor tensor) {
    LongBuffer longBuffer = LongBuffer.allocate(Numel.of(tensor));
    toStreamNumber(tensor).mapToLong(Number::longValue).forEach(longBuffer::put);
    longBuffer.flip();
    return longBuffer;
  }

  /***************************************************/
  /** does not perform rounding, but uses Scalar::number().intValue()
   * 
   * @param tensor
   * @return list of int values of all scalars in tensor */
  public static List<Integer> toListInteger(Tensor tensor) {
    return toStreamNumber(tensor).map(Number::intValue).collect(Collectors.toList());
  }

  /** does not perform rounding, but uses Scalar::number().intValue()
   * 
   * @param tensor
   * @return array of int values of all scalars in tensor */
  public static int[] toIntArray(Tensor tensor) {
    return toStreamNumber(tensor).mapToInt(Number::intValue).toArray();
  }

  /** does not perform rounding, but uses Scalar::number().intValue()
   * 
   * @param tensor
   * @return 2-dimensional array of int's with first dimension equal to tensor.length() */
  public static int[][] toIntArray2D(Tensor tensor) {
    int[][] array = new int[tensor.length()][];
    int index = -1;
    for (Tensor entry : tensor)
      array[++index] = toIntArray(entry);
    return array;
  }

  /** @param tensor
   * @return */
  public static IntBuffer toIntBuffer(Tensor tensor) {
    IntBuffer intBuffer = IntBuffer.allocate(Numel.of(tensor));
    toStreamNumber(tensor).mapToInt(Number::intValue).forEach(intBuffer::put);
    intBuffer.flip();
    return intBuffer;
  }
}
