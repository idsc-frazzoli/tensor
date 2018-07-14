// code by jph
package ch.ethz.idsc.tensor.io.ext;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
import java.util.stream.Stream;

import ch.ethz.idsc.tensor.Scalars;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;

/* package */ enum StaticHelper {
  ;
  /** @param inputStream
   * @return lines in given inputStream as stream of strings */
  static Stream<String> lines(InputStream inputStream) {
    return new BufferedReader(new InputStreamReader(inputStream)).lines();
  }

  static Tensor three(String string) {
    StringTokenizer stringTokenizer = new StringTokenizer(string);
    return Tensors.of( //
        Scalars.fromString(stringTokenizer.nextToken()), //
        Scalars.fromString(stringTokenizer.nextToken()), //
        Scalars.fromString(stringTokenizer.nextToken()));
  }
}
