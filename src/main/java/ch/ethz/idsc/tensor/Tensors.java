// code by jph
package ch.ethz.idsc.tensor;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

/** utility class that provides constructors of tensors for convenience.
 * 
 * <p>The methods are intentionally non-parallel to ensure a deterministic
 * construction process.
 * 
 * <p>Parallel stream processing can lead to significant speed-up.
 * Parallel stream processing has to be decided case by case. */
public enum Tensors {
  ;
  /** @return new modifiable tensor instance with no entries, i.e. length() == 0
   * @see #isEmpty(Tensor) */
  public static Tensor empty() {
    return Tensor.of(Stream.empty());
  }

  /** @param tensors
   * @return concatenation of {@link Tensor}s or {@link Scalar}s listed in tensors */
  public static Tensor of(Tensor... tensors) {
    return Tensor.of(Stream.of(tensors));
  }

  /** @param numbers
   * @return vector of numbers as {@link RealScalar}s */
  public static Tensor vector(Number... numbers) {
    return Tensor.of(Stream.of(numbers).map(RealScalar::of));
  }

  /** @param list
   * @return vector of numbers in list as {@link RealScalar}s */
  public static Tensor vector(List<? extends Number> list) {
    return Tensor.of(list.stream().map(RealScalar::of));
  }

  /** @param function
   * @param length
   * @return vector of length with i-th entry == function.apply(i) */
  public static Tensor vector(Function<Integer, ? extends Tensor> function, int length) {
    return Tensor.of(IntStream.range(0, length).boxed().map(function));
  }

  /** @param values
   * @return tensor of {@link RationalScalar} with given values */
  public static Tensor vectorInt(int... values) {
    return Tensor.of(IntStream.of(values).mapToObj(IntegerScalar::of));
  }

  /** @param values
   * @return tensor of {@link RationalScalar} with given values */
  public static Tensor vectorLong(long... values) {
    return Tensor.of(LongStream.of(values).mapToObj(IntegerScalar::of));
  }

  /** @param values
   * @return tensor of {@link DoubleScalar} with given values */
  public static Tensor vectorDouble(double... values) {
    return Tensor.of(DoubleStream.of(values).mapToObj(DoubleScalar::of));
  }

  /** @param biFunction
   * @param n number of rows
   * @param m number of columns
   * @return (n x m)-matrix with (i,j)th-entry == bifunction.apply(i,j) */
  public static Tensor matrix(BiFunction<Integer, Integer, ? extends Tensor> biFunction, int n, int m) {
    return Tensor.of(IntStream.range(0, n).mapToObj( //
        i -> Tensor.of(IntStream.range(0, m).mapToObj(j -> biFunction.apply(i, j)))));
  }

  /** @param data
   * @return matrix with dimensions and {@link Tensor} entries as array data */
  public static Tensor matrix(Tensor[][] data) {
    return Tensor.of(Stream.of(data).map(Tensors::of));
  }

  /** @param data
   * @return matrix with dimensions and {@link RealScalar} entries */
  public static Tensor matrix(Number[][] data) {
    return Tensor.of(Stream.of(data).map(Tensors::vector));
  }

  /** @param data
   * @return matrix with dimensions and {@link RationalScalar} entries as array data */
  public static Tensor matrixInt(int[][] data) {
    return Tensor.of(Stream.of(data).map(Tensors::vectorInt));
  }

  /** @param data
   * @return matrix with dimensions and {@link RationalScalar} entries as array data */
  public static Tensor matrixLong(long[][] data) {
    return Tensor.of(Stream.of(data).map(Tensors::vectorLong));
  }

  /** @param data
   * @return matrix with dimensions and {@link DoubleScalar} entries as array data */
  public static Tensor matrixDouble(double[][] data) {
    return Tensor.of(Stream.of(data).map(Tensors::vectorDouble));
  }

  private static final String OPENING_BRACKET_STRING = "" + Tensor.OPENING_BRACKET;

  /** @param string
   * @return */
  public static Tensor fromString(final String string) {
    return fromString(string, Scalars::fromString);
  }

  /** @param string
   * @param function that parses a string to a scalar
   * @return */
  public static Tensor fromString(final String string, Function<String, Scalar> function) {
    // could implement using stack?
    if (string.startsWith(OPENING_BRACKET_STRING)) {
      List<Tensor> list = new ArrayList<>();
      int level = 0;
      int beg = -1;
      for (int index = 0; index < string.length(); ++index) {
        final char chr = string.charAt(index);
        if (chr == Tensor.OPENING_BRACKET) {
          ++level;
          if (level == 1)
            beg = index + 1;
        }
        if (level == 1 && (chr == ',' || chr == Tensor.CLOSING_BRACKET)) {
          String entry = string.substring(beg, index).trim(); // trim is required
          if (!entry.isEmpty())
            list.add(fromString(entry, function));
          beg = index + 1;
        }
        if (chr == Tensor.CLOSING_BRACKET)
          --level;
      }
      return Tensor.of(list.stream());
    }
    return function.apply(string);
  }

  /***************************************************/
  /** @param tensor
   * @return true if tensor is a vector with zero entries, and
   * false if tensor contains entries or is a {@link Scalar} */
  public static boolean isEmpty(Tensor tensor) { // Marc's function
    return tensor.length() == 0;
  }

  /** @param tensor
   * @return true if tensor is not modifiable */
  public static boolean isUnmodifiable(Tensor tensor) {
    return tensor instanceof UnmodifiableTensor;
  }
}