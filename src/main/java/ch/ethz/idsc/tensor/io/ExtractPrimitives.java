// code by jph
package ch.ethz.idsc.tensor.io;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Tensor;

/** only works for {@link Tensor}s with entries as {@link RealScalar} */
public enum ExtractPrimitives {
  ;
  public static Stream<Number> toStreamNumber(Tensor tensor) {
    return tensor.flatten(-1) //
        .map(RealScalar.class::cast) //
        .map(RealScalar::number);
  }

  public static List<Double> toListDouble(Tensor tensor) {
    return toStreamNumber(tensor) //
        .map(Number::doubleValue).collect(Collectors.toList());
  }

  public static double[] toArrayDouble(Tensor tensor) {
    return toStreamNumber(tensor) //
        .mapToDouble(Number::doubleValue).toArray();
  }

  /** does not perform rounding, but uses Number::longValue
   * 
   * @param tensor
   * @return */
  public static List<Long> toListLong(Tensor tensor) {
    return toStreamNumber(tensor) //
        .map(Number::longValue).collect(Collectors.toList());
  }

  /** does not perform rounding, but uses Number::longValue
   * 
   * @param tensor
   * @return */
  public static long[] toArrayLong(Tensor tensor) {
    return toStreamNumber(tensor) //
        .mapToLong(Number::longValue).toArray();
  }

  /** does not perform rounding, but uses Number::intValue
   * 
   * @param tensor
   * @return */
  public static List<Integer> toListInteger(Tensor tensor) {
    return toStreamNumber(tensor) //
        .map(Number::intValue).collect(Collectors.toList());
  }

  /** does not perform rounding, but uses Number::intValue
   * 
   * @param tensor
   * @return */
  public static int[] toArrayInt(Tensor tensor) {
    return toStreamNumber(tensor) //
        .mapToInt(Number::intValue).toArray();
  }
}
