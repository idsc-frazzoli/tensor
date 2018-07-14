// code by jph
package ch.ethz.idsc.tensor.io;

import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import ch.ethz.idsc.tensor.ComplexScalar;
import ch.ethz.idsc.tensor.DoubleScalar;
import ch.ethz.idsc.tensor.RationalScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.alg.Dimensions;
import ch.ethz.idsc.tensor.qty.Quantity;
import ch.ethz.idsc.tensor.sca.ScalarUnaryOperator;

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
 * <p>Careful: ensure that decimal numbers adhere to the java format.
 * The letter for the exponent has to be capitalized:
 * <ul>
 * <li>{@code 1.23E-45} valid numeric expression, imported as {@link DoubleScalar}
 * <li>{@code 1.23e-45} non numeric, imported as {@link StringScalar}
 * </ul>
 * 
 * <p>MATLAB::dlmwrite creates CSV files in the required decimal format
 * <pre>
 * dlmwrite(filename, matrix, 'precision', '%E');
 * dlmwrite(filename, matrix, '-append', 'precision', '%E');
 * </pre>
 * Reference: https://www.mathworks.com/help/matlab/ref/dlmwrite.html
 * 
 * <p>For export of matrices to Mathematica, {@link Put} is
 * the preferred option. However, the csv format may produce smaller
 * files. Mathematica::Import of csv files requires the table entries
 * to be decimal numbers. In particular, exact fractions, e.g. 5/7,
 * are imported to string expressions "5/7". The scalar operator
 * {@link CsvFormat#strict()} can be used to map the entries to decimal
 * expressions prior to export.
 * 
 * <p>Within the realm of Java, use {@link ObjectFormat}
 * to store and reload tensors, and do not use csv format. */
public enum CsvFormat {
  ;
  private static final Collector<CharSequence, ?, String> COLLECTOR = Collectors.joining(",");

  /** In Mathematica the csv file is imported using
   * A=Import["filename.csv"];
   * 
   * <p>In MATLAB the csv file can be imported using
   * A=load('filename.csv');
   * 
   * @param tensor that may also be a {@link Scalar}
   * @return stream of lines that make up the csv format
   * @see Export */
  public static Stream<String> of(Tensor tensor) {
    // flatten(0) handles scalars as opposed to stream()
    return tensor.flatten(0).map(CsvFormat::row);
  }

  /** Example: The stream of the following strings
   * <pre>
   * "10,200,3"
   * "78"
   * "-3,2.3"
   * </pre>
   * results in the tensor {{10, 200, 3}, {78}, {-3, 2.3}}
   * 
   * <p>Hint: To import a table from a csv file use {@link Import}.
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
    return Tensor.of(stream.parallel().map(CsvFormat::embrace).map(function));
  }

  /** the scalar operator attempts to guarantee that the CSV import in Mathematica
   * as numeric values.
   * 
   * <p>Scalars of type
   * <ul>
   * <li>{@link RationalScalar} are converted to {@link DoubleScalar} unless the
   * fraction has denominator == 1.
   * <li>{@link StringScalar} is enclosed in quotes if necessary. The result must
   * not contain any other quotes character.
   * <li>{@link ComplexScalar}, or {@link Quantity} are not allowed.
   * </ul>
   * 
   * <p>Example use:
   * <pre>
   * Export.of(new File("name.csv"), tensor.map(CsvFormat.strict()));
   * </pre> */
  public static ScalarUnaryOperator strict() {
    return CsvHelper.FUNCTION;
  }

  // helper function
  private static String row(Tensor tensor) {
    // flatten(0) handles scalars as opposed to stream()
    return tensor.flatten(0).map(Tensor::toString).collect(COLLECTOR);
  }

  // helper function
  private static String embrace(String string) {
    return Tensor.OPENING_BRACKET + string + Tensor.CLOSING_BRACKET;
  }
}
