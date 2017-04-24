// code by jph
package ch.ethz.idsc.tensor.io;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;

/** only works for {@link Tensor}s with {@link Scalar} entries
 * that support the operation {@link Scalar#number()} */
public enum ExtractPrimitives {
  ;
  /** @param tensor
   * @return stream of all scalars in tensor mapped to {@link Number} */
  public static Stream<Number> toStreamNumber(Tensor tensor) {
    return tensor.flatten(-1).map(Scalar.class::cast).map(Scalar::number);
  }

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
}
