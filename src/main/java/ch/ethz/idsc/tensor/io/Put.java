// code by jph
package ch.ethz.idsc.tensor.io;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Collectors;

import ch.ethz.idsc.tensor.Tensor;

/** export of tensor to file, or a string compatible with Mathematica.
 * The output is similar to Object::toString and readable in any text editor.
 * 
 * <p>The file format is intended for data exchange between
 * Mathematica and the tensor library.
 * 
 * <p>file is readable in Mathematica where the file is
 * imported using Mathematica::Get.
 * 
 * <p>the format does not specify or require any particular
 * file extension. Mathematica also does not define an extension
 * for this format.
 * 
 * <p>inspired by
 * <a href="https://reference.wolfram.com/language/ref/Put.html">Put</a> */
public enum Put {
  ;
  /** @param file destination of write
   * @param tensor
   * @throws IOException */
  public static void of(File file, Tensor tensor) throws IOException {
    of(file.toPath(), tensor);
  }

  /** @param path destination of write
   * @param tensor
   * @throws IOException */
  public static void of(Path path, Tensor tensor) throws IOException {
    Files.write(path, (Iterable<String>) MathematicaFormat.of(tensor)::iterator);
  }

  /** @param tensor
   * @return string expression of tensor compatible with Mathematica
   * @see Pretty#of(Tensor) */
  public static String string(Tensor tensor) {
    return MathematicaFormat.of(tensor).collect(Collectors.joining("\n"));
  }
}
