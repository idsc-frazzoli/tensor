// code by jph
package ch.ethz.idsc.tensor.io;

import java.util.stream.Stream;

import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.alg.Dimensions;

/** comma separated values format
 * 
 * <p>The csv format cannot reliably encode the {@link Dimensions}
 * of tensors. For instance, csv does not distinguish between
 * vectors and matrices with dimensions [n x 1] or [1 x n].
 * 
 * <p>If possible, only use {@link CsvFormat} for export of
 * vectors or matrices to other applications such as MATLAB.
 * {@link MatlabExport} preserves dimensions of multi-dimensional arrays.
 * 
 * <p>Within the realm of Java, use {@link ObjectFormat}
 * to store and reload tensors, and do not use csv format. */
public enum CsvFormat {
  ;
  /** The stream of strings can be written to a file using
   * <code>Files.write(Paths.get("filePath"), (Iterable<String>) stream::iterator);</code>
   * 
   * <p>In MATLAB the csv file can be imported using
   * A=load('filename.csv');
   * 
   * @param tensor
   * @return stream of lines that make up the csv format */
  public static Stream<String> of(Tensor tensor) {
    return tensor.flatten(0).parallel() //
        .map(Tensor::toString) //
        .map(string -> string.replace(", ", ",")) // remove whitespace
        .map(Utils::removeEnclosingBracketsIfPresent); // destroys information about dimension
  }

  /** The strings can be read from a file using
   * <code>Files.lines(Paths.get("filePath"));</code>
   * 
   * <p>Example: The stream of the following strings
   * <pre>
   * "10,200,3"
   * "78"
   * "-3,2.3"
   * </pre>
   * results in the tensor {{10, 200, 3}, {78}, {-3, 2.3}}
   * 
   * @param stream
   * @return tensor with rows defined by the entries of the input stream */
  public static Tensor parse(Stream<String> stream) {
    return Tensor.of(stream.parallel() //
        .map(Utils::encloseWithBrackets) //
        .map(Tensors::fromString));
  }
}
