// code by jph
package ch.ethz.idsc.tensor.io;

import java.util.stream.Stream;

import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;

/** comma separated values format */
public enum CsvFormat {
  ;
  /** The stream of strings can be written to a file using
   * <code>Files.write(Paths.get("filePath"), (Iterable<String>) stream::iterator);</code>
   * 
   * @param tensor
   * @return */
  public static Stream<String> of(Tensor tensor) {
    return tensor.flatten(0).parallel() //
        .map(Tensor::toString) //
        .map(string -> string.substring(1, string.length() - 1)); // remove first and last bracket
  }

  /** The strings can be read from a file using
   * <code>Files.lines(Paths.get("filePath"));</code>
   * 
   * <p>Example: The stream of the following strings
   * <pre>
   * "10, 200, 3"
   * "78"
   * "-3, 2.3"
   * </pre>
   * results in the tensor [[10, 200, 3], [78], [-3, 2.3]]
   * 
   * @param stream
   * @return tensor with rows defined by the entries of the input stream */
  public static Tensor parse(Stream<String> stream) {
    return Tensor.of(stream.parallel() //
        .map(line -> "[" + line + "]") // insert first and last bracket
        .map(Tensors::fromString));
  }
}
