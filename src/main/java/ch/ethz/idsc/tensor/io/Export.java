// code by jph
package ch.ethz.idsc.tensor.io;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import javax.imageio.ImageIO;

import ch.ethz.idsc.tensor.Tensor;

/** supported file formats are: CSV, PNG, TENSOR
 * 
 * <p>Do not use Export when exchanging {@link Tensor}s with
 * Mathematica. For that purpose use {@link Put} and {@link Get}.
 * 
 * <p>inspired by
 * <a href="https://reference.wolfram.com/language/ref/Export.html">Export</a> */
public enum Export {
  ;
  // ---
  /** See the documentation of
   * {@link CsvFormat}, {@link ImageFormat}, {@link ObjectFormat}
   * to find how tensors are encoded in the respective format.
   * 
   * @param file destination
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
