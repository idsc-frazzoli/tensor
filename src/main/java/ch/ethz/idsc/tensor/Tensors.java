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

/** utility class that provides constructors of tensors for convenience */
public enum Tensors {
  ;
  /** @return new modifiable tensor instance with no entries, i.e. length() == 0 */
  public static Tensor empty() {
    return Tensor.of(Stream.empty());
  }

  /** @param tensors
   * @return concatenation of {@link Tensor}s or {@link Scalar}s listed in tensors */
  public static Tensor of(Tensor... tensors) {
    return Tensor.of(Stream.of(tensors));
  }

  /** @param numbers
   * @return */
  public static Tensor vector(Number... numbers) {
    return Tensor.of(Stream.of(numbers).map(RealScalar::of));
  }

  /** @param list
   * @return */
  public static Tensor vector(List<? extends Number> list) {
    return Tensor.of(list.stream().map(RealScalar::of));
  }

  /** @param function
   * @param length
   * @return vector of length with i-th entry == function.apply(i) */
  public static Tensor vector(Function<Integer, ? extends Tensor> function, int length) {
    return Tensor.of(IntStream.range(0, length).boxed().map(function::apply));
  }

  /** @param values
   * @return tensor of {@link RationalScalar} with given values */
  public static Tensor vectorInt(int... values) {
    return Tensor.of(IntStream.of(values).boxed().map(IntegerScalar::of));
  }

  /** @param values
   * @return tensor of {@link RationalScalar} with given values */
  public static Tensor vectorLong(long... values) {
    return Tensor.of(LongStream.of(values).boxed().map(IntegerScalar::of));
  }

  /** @param values
   * @return tensor of {@link DoubleScalar} with given values */
  public static Tensor vectorDouble(double... values) {
    return Tensor.of(DoubleStream.of(values).boxed().map(DoubleScalar::of));
  }

  /** @param biFunction
   * @param n
   * @param m
   * @return (n x m)-matrix with (i,j)-entry == bifunction.apply(i,j) */
  public static Tensor matrix(BiFunction<Integer, Integer, ? extends Tensor> biFunction, int n, int m) {
    return Tensor.of(IntStream.range(0, n).boxed().map( //
        i -> Tensor.of(IntStream.range(0, m).boxed().map(j -> biFunction.apply(i, j)))));
  }

  /** @param data
   * @return matrix with dimensions and {@link RationalScalar} entries as array data */
  public static Tensor matrixInt(int[][] data) {
    return matrix((i, j) -> RealScalar.of(data[i][j]), data.length, data[0].length);
  }

  /** @param data
   * @return matrix with dimensions and {@link RationalScalar} entries as array data */
  public static Tensor matrixLong(long[][] data) {
    return matrix((i, j) -> RealScalar.of(data[i][j]), data.length, data[0].length);
  }

  /** @param data
   * @return matrix with dimensions and {@link DoubleScalar} entries as array data */
  public static Tensor matrixDouble(double[][] data) {
    return matrix((i, j) -> DoubleScalar.of(data[i][j]), data.length, data[0].length);
  }

  /** @param data
   * @return matrix with dimensions and {@link Scalar} entries as array data */
  public static Tensor matrix(Scalar[][] data) {
    return matrix((i, j) -> data[i][j], data.length, data[0].length);
  }

  /** @param string
   * @return */
  public static Tensor fromString(final String string) {
    // TODO implement using stack
    if (string.startsWith("[")) {
      List<Tensor> list = new ArrayList<>();
      int level = 0;
      int beg = -1;
      for (int index = 0; index < string.length(); ++index) {
        final char chr = string.charAt(index);
        if (chr == '[') {
          ++level;
          if (level == 1)
            beg = index + 1;
        }
        if (level == 1 && (chr == ',' || chr == ']')) {
          String entry = string.substring(beg, index).trim(); // <- TODO not sure if trim is good
          if (!entry.isEmpty())
            list.add(fromString(entry));
          beg = index + 1;
        }
        if (chr == ']')
          --level;
      }
      return Tensor.of(list.stream());
    }
    return Scalars.fromString(string);
  }
}
