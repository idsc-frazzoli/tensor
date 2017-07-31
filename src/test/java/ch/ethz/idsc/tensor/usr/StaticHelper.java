// code by jph
package ch.ethz.idsc.tensor.usr;

import java.util.function.BiFunction;
import java.util.stream.IntStream;

import ch.ethz.idsc.tensor.Tensor;

// TODO extract functionality to ParallelArray or so
enum StaticHelper {
  ;
  static final int GALLERY_RES = 128 + 64;

  /** @param biFunction
   * @param n number of rows
   * @param m number of columns
   * @return (n x m)-matrix with (i,j)th-entry == bifunction.apply(i,j) */
  static Tensor parallel(BiFunction<Integer, Integer, ? extends Tensor> biFunction, int n, int m) {
    return Tensor.of(IntStream.range(0, n).parallel().boxed().map( //
        i -> Tensor.of(IntStream.range(0, m).boxed().map(j -> biFunction.apply(i, j)))));
  }
}
