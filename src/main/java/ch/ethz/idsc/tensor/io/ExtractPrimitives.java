// code by jph
package ch.ethz.idsc.tensor.io;

import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.alg.Numel;

/** the extraction of primitive data types from a {@link Tensor}
 * only works for tensors with {@link Scalar} entries
 * that all support the operation {@link Scalar#number()} */
public enum ExtractPrimitives {
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
  public static double[] toArrayDouble(Tensor tensor) {
    return toStreamNumber(tensor).mapToDouble(Number::doubleValue).toArray();
  }

  public static DoubleBuffer toDoubleBuffer(Tensor tensor) {
    DoubleBuffer doubleBuffer = DoubleBuffer.allocate(Numel.of(tensor));
    toStreamNumber(tensor).map(Number::doubleValue).forEach(doubleBuffer::put);
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
  public static float[] toArrayFloat(Tensor tensor) {
    List<Float> list = toListFloat(tensor);
    float[] array = new float[list.size()];
    int index = -1;
    for (float value : list)
      array[++index] = value;
    return array;
  }

  public static FloatBuffer toFloatBuffer(Tensor tensor) {
    FloatBuffer floatBuffer = FloatBuffer.allocate(Numel.of(tensor));
    toStreamNumber(tensor).map(Number::floatValue).forEach(floatBuffer::put);
    floatBuffer.flip();
    return floatBuffer;
  }

  /***************************************************/
  /** does not perform rounding, but uses Number::longValue
   * 
   * @param tensor
   * @return list of long values of all scalars in tensor */
  public static List<Long> toListLong(Tensor tensor) {
    return toStreamNumber(tensor).map(Number::longValue).collect(Collectors.toList());
  }

  /** does not perform rounding, but uses Number::longValue
   * 
   * @param tensor
   * @return array of long values of all scalars in tensor */
  public static long[] toArrayLong(Tensor tensor) {
    return toStreamNumber(tensor).mapToLong(Number::longValue).toArray();
  }

  /***************************************************/
  /** does not perform rounding, but uses Number::intValue
   * 
   * @param tensor
   * @return list of int values of all scalars in tensor */
  public static List<Integer> toListInteger(Tensor tensor) {
    return toStreamNumber(tensor).map(Number::intValue).collect(Collectors.toList());
  }

  /** does not perform rounding, but uses Number::intValue
   * 
   * @param tensor
   * @return array of int values of all scalars in tensor */
  public static int[] toArrayInt(Tensor tensor) {
    return toStreamNumber(tensor).mapToInt(Number::intValue).toArray();
  }

  public static IntBuffer toIntBuffer(Tensor tensor) {
    IntBuffer intBuffer = IntBuffer.allocate(Numel.of(tensor));
    toStreamNumber(tensor).map(Number::intValue).forEach(intBuffer::put);
    intBuffer.flip();
    return intBuffer;
  }
}
