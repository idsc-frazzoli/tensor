// code by jph
package ch.ethz.idsc.tensor.io;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;

/** utility to exchange data with Wolfram Mathematica
 * 
 * Files.lines(Paths.get("filePath"))
 * Files.write(Paths.get("filePath"), (Iterable<String>) stream::iterator); */
public enum MathematicaFormat {
  ;
  /** @param tensor
   * @return strings parsed by Mathematica as tensor */
  public static Stream<String> of(Tensor tensor) {
    String string = tensor.toString() //
        .replace('[', '{') //
        .replace(']', '}') //
        .replace("}, {", "},\n{"); // <- introduce new line
    return Stream.of(string.split("\n"));
  }

  /** @param strings of Mathematica encoded tensor
   * @return tensor */
  public static Tensor parse(Stream<String> stream) {
    return Tensors.fromString(stream //
        .map(String::trim) //
        .map(s -> s.replace('{', '[')) //
        .map(s -> s.replace('}', ']')) //
        .collect(Collectors.joining("")));
  }
}
