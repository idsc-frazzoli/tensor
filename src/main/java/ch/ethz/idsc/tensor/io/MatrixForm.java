// code by jph
package ch.ethz.idsc.tensor.io;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.alg.MatrixQ;
import ch.ethz.idsc.tensor.alg.Transpose;

/** compact display for a matrix
 * 
 * @see Pretty */
/* package */ enum MatrixForm {
  ;
  // ---
  public static String of(Tensor matrix) {
    MatrixQ.require(matrix);
    String[] array = Transpose.of(matrix).stream()
        .map(col -> " %" + col.stream().map(Scalar.class::cast).map(Scalar::toString).mapToInt(String::length).max().getAsInt() + "s ").toArray(String[]::new);
    // System.out.println(Arrays.asList(array));
    StringBuilder stringBuilder = new StringBuilder("[\n");
    for (Tensor row : matrix)
      stringBuilder.append( //
          IntStream.range(0, row.length()).mapToObj(index -> String.format(array[index], row.Get(index).toString()))
              .collect(Collectors.joining("", " [", "]\n")));
    stringBuilder.append("]");
    return stringBuilder.toString();
  }

  public static void main(String[] args) {
    Tensor matrix = Tensors.fromString("{{1,2,321341234},{2,44,12333}}");
    // String string = ;
    System.out.println(of(matrix) + "end");
    System.out.println(Pretty.of(matrix) + "end");
  }
}
