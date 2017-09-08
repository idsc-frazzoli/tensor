// code by jph
package ch.ethz.idsc.tensor.io;

import java.util.function.Function;
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
        .map(CsvFormat::removeEnclosingBracketsIfPresent); // destroys information about dimension
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
   * @param stream of lines of file
   * @return tensor with rows defined by the entries of the input stream */
  public static Tensor parse(Stream<String> stream) {
    return parse(stream, Tensors::fromString);
  }

  /** Default function for parsing:
   * Tensors::fromString
   * 
   * Example for extended functionality:
   * string -> Tensors.fromString(string, Quantity::fromString)
   * 
   * @param stream of lines of file
   * @param function that parses a string to a tensor
   * @return */
  public static Tensor parse(Stream<String> stream, Function<String, Tensor> function) {
    return Tensor.of(stream.parallel() //
        .map(CsvFormat::encloseWithBrackets) //
        .map(function));
  }

  private static final String OPENING_BRACKET_STRING = "" + Tensor.OPENING_BRACKET;
  private static final String CLOSING_BRACKET_STRING = "" + Tensor.CLOSING_BRACKET;

  /** @param string
   * @return string with opening and closing bracket removed, if brackets are present */
  // function only used in CsvFormat
  private static String removeEnclosingBracketsIfPresent(final String string) {
    if (string.startsWith(OPENING_BRACKET_STRING) && string.endsWith(CLOSING_BRACKET_STRING))
      return string.substring(1, string.length() - 1);
    return string;
  }

  /** @param string
   * @return '{' + string + '}' */
  // function only used in CsvFormat
  private static String encloseWithBrackets(final String string) {
    StringBuilder stringBuilder = new StringBuilder(1 + string.length() + 1);
    stringBuilder.append(Tensor.OPENING_BRACKET);
    stringBuilder.append(string);
    stringBuilder.append(Tensor.CLOSING_BRACKET);
    return stringBuilder.toString();
  }
}
