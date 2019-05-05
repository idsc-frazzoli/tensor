// code by jph
package ch.ethz.idsc.tensor.io;

import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.alg.Transpose;

/** compact display for a matrix
 * 
 * <pre>
 * MatrixForm.of("{{1,2,321341234},{2,44,12333}}");
 * 1 _2 321341234
 * 2 44 ____12333
 * </pre>
 * 
 * <p>inspired by
 * <a href="https://reference.wolfram.com/language/ref/MatrixForm.html">MatrixForm</a>
 * 
 * @see Pretty */
public enum MatrixForm {
  ;
  private static final Collector<CharSequence, ?, String> COLLECTOR = Collectors.joining(" ");

  /** @param tensor not of type {@link Scalar}
   * @return
   * @throws Exception if given tensor is a {@link Scalar} */
  public static String of(Tensor tensor) {
    return of(tensor, COLLECTOR);
  }

  /** @param tensor not of type {@link Scalar}
   * @param delimiter
   * @param prefix
   * @param suffix
   * @return
   * @throws Exception if given tensor is a {@link Scalar} */
  public static String of(Tensor tensor, CharSequence delimiter, CharSequence prefix, CharSequence suffix) {
    return of(tensor, Collectors.joining(delimiter, prefix, suffix));
  }

  // helper function
  private static String of(Tensor tensor, Collector<CharSequence, ?, String> collector) {
    if (tensor.length() == 0)
      return "";
    String[] array = Transpose.of(tensor).stream() //
        .map(col -> "%" + col.stream().map(Tensor::toString).mapToInt(String::length).max().getAsInt() + "s") //
        .toArray(String[]::new);
    return tensor.stream() //
        .map(row -> IntStream.range(0, row.length()) //
            .mapToObj(index -> String.format(array[index], row.get(index))) //
            .collect(collector)) //
        .collect(Collectors.joining("\n"));
  }
}
