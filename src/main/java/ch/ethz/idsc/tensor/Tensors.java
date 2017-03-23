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

public class Tensors {
  /** @return new tensor instance with no entries */
  public static Tensor empty() {
    return Tensor.of(Stream.empty());
  }

  /** @param tensors
   * @return concatenation of {@link Tensor}s or {@link Scalar}s listed in tensors */
  public static Tensor of(Tensor... tensors) {
    return Tensor.of(Stream.of(tensors));
  }

  /** @param values
   * @return tensor of {@link RationalScalar} with given values */
  public static Tensor vectorInt(int... values) {
    return Tensor.of(IntStream.of(values).boxed().map(i -> RationalScalar.of(i, 1)));
  }

  /** @param values
   * @return tensor of {@link RationalScalar} with given values */
  public static Tensor vectorLong(long... values) {
    return Tensor.of(LongStream.of(values).boxed().map(i -> RationalScalar.of(i, 1)));
  }

  /** @param values
   * @return tensor of {@link DoubleScalar} with given values */
  public static Tensor vectorDouble(double... values) {
    return Tensor.of(DoubleStream.of(values).boxed().map(DoubleScalar::of));
  }

  public static Tensor vector(Function<Integer, Scalar> function, int length) {
    return Tensor.of(IntStream.range(0, length).boxed().map(function::apply));
  }

  public static Tensor matrix(BiFunction<Integer, Integer, Scalar> biFunction, int n, int m) {
    return Tensor.of(IntStream.range(0, n).boxed().map( //
        i -> Tensor.of(IntStream.range(0, m).boxed().map(j -> biFunction.apply(i, j)))));
  }

  public static Tensor matrixInt(int[][] mat) {
    return matrix((i, j) -> RealScalar.of(mat[i][j]), mat.length, mat[0].length);
  }

  public static Tensor matrixLong(long[][] mat) {
    return matrix((i, j) -> RealScalar.of(mat[i][j]), mat.length, mat[0].length);
  }

  public static Tensor matrixDouble(double[][] mat) {
    return matrix((i, j) -> DoubleScalar.of(mat[i][j]), mat.length, mat[0].length);
  }

  public static Tensor matrix(Scalar[][] mat) {
    return matrix((i, j) -> mat[i][j], mat.length, mat[0].length);
  }

  /** @param string
   * @return */
  public static Tensor fromString(final String string) {
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
          String entry = string.substring(beg, index).trim();
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

  // class cannot be instantiated
  private Tensors() {
  }
}
