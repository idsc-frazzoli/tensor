// code by jph
package ch.ethz.idsc.tensor.io;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import ch.ethz.idsc.tensor.Scalars;
import ch.ethz.idsc.tensor.Tensor;

/** Files.lines(Paths.get("filePath"))
 * Files.write(Paths.get("filePath"), (Iterable<String>) stream::iterator); */
public class CsvFormat {
  private static final String COMMA = ",";

  public static Stream<String> ofMatrix(Tensor tensor) {
    // does tensor have to be 2d array?
    return tensor.flatten(0).parallel() //
        .map(vector -> String.join(COMMA, //
            vector.flatten(0).map(Tensor::toString).collect(Collectors.toList())));
  }

  public static Tensor parse(Stream<String> stream) {
    return Tensor.of(stream.parallel() //
        .filter(line -> !line.isEmpty()) //
        .map(line -> Tensor.of(Stream.of(line.split(COMMA)).map(Scalars::fromString))));
  }
}
