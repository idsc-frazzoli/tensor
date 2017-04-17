// code by jph
package ch.ethz.idsc.tensor.io;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;

/** utility to exchange data with Wolfram Mathematica
 * 
 * <p>Mathematica::Put stores an expression to a file
 * <code>Put[{1,2,3}, "filePath"]</code>
 * 
 * <p>Mathematica::Get retrieves an expression from a file
 * <code>expr = Get["filePath"]</code>
 * 
 * <p>String expressions may also be compatible with Java,
 * for instance to define an array of type int[][], or double[][] */
public enum MathematicaFormat {
  ;
  /** @param tensor
   * @return strings parsed by Mathematica as tensor */
  public static Stream<String> of(Tensor tensor) {
    String string = tensor.toString().replace("}, {", "},\n{"); // <- introduce new line
    return Stream.of(string.split("\n"));
  }

  /** @param strings of Mathematica encoded tensor
   * @return tensor */
  public static Tensor parse(Stream<String> stream) {
    return Tensors.fromString(stream //
        .map(String::trim) //
        .map(string -> string.replace(" I", "1*I")) // TODO still cannot parse 1*I, but only 0+1*I
        .map(string -> string.replace("-I", "-1*I")) //
        .map(string -> string.replace(" ", "")) // <- depends on implementation of Scalars::fromString
        .collect(Collectors.joining("")));
  }

  /** @param string of Mathematica encoded tensor
   * @return tensor */
  public static Tensor parse(String string) {
    return parse(Stream.of(string));
  }
}
