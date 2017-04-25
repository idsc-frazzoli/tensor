// code by jph
package ch.ethz.idsc.tensor.io;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import ch.ethz.idsc.tensor.Tensor;

/** export of tensor to file.
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
  // ---
  /** @param file
   * @param tensor
   * @throws IOException */
  public static void of(File file, Tensor tensor) throws IOException {
    of(file.toPath(), tensor);
  }

  /** @param path
   * @param tensor
   * @throws IOException */
  public static void of(Path path, Tensor tensor) throws IOException {
    Files.write(path, (Iterable<String>) MathematicaFormat.of(tensor)::iterator);
  }
}
