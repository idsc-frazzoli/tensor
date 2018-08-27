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
 * for instance to define an array of type int[][], or double[][]
 * 
 * <p>The following string notation is incompatible with Java
 * <pre>
 * 1.2630135948105083*^17
 * 3.5849905564258352*^-18
 * </pre> */
public enum MathematicaFormat {
  ;
  private static final String EXPONENT_JAVA = "E";
  private static final String EXPONENT_MATH = "*^";

  /** @param tensor
   * @return strings parsed by Mathematica as given tensor */
  public static Stream<String> of(Tensor tensor) {
    String string = tensor.toString() //
        .replace(EXPONENT_JAVA, EXPONENT_MATH) //
        .replace("}, {", "},\n{"); // <- introduce new line
    return Stream.of(string.split("\n"));
  }

  /** @param stream of strings of Mathematica encoded tensor
   * @return tensor */
  public static Tensor parse(Stream<String> stream) {
    return Tensors.fromString(stream //
        .map(string -> string.replace(EXPONENT_MATH, EXPONENT_JAVA)) //
        .map(MathematicaFormat::join) //
        .collect(Collectors.joining("")));
  }

  // helper function
  private static String join(String string) {
    if (string.endsWith("\\"))
      return string.substring(0, string.length() - 1);
    return string;
  }
}
