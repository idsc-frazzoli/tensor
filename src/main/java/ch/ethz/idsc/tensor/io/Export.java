// code by jph
package ch.ethz.idsc.tensor.io;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import javax.imageio.ImageIO;

import ch.ethz.idsc.tensor.Tensor;

/** supported file formats are: CSV, PNG, TENSOR
 * 
 * <p>inspired by
 * <a href="https://reference.wolfram.com/language/ref/Export.html">Export</a> */
public enum Export {
  ;
  // ---
  /** @param file
   * @param tensor
   * @throws IOException */
  public static void of(File file, Tensor tensor) throws IOException {
    Filename filename = new Filename(file);
    if (filename.hasExtension("csv"))
      Files.write(file.toPath(), (Iterable<String>) CsvFormat.of(tensor)::iterator);
    else //
    if (filename.hasExtension("png"))
      ImageIO.write(ImageFormat.of(tensor), "png", file);
    else //
    if (filename.hasExtension("tensor"))
      Files.write(file.toPath(), ObjectFormat.of(tensor));
    else //
      throw new RuntimeException(file.toString());
  }
}
