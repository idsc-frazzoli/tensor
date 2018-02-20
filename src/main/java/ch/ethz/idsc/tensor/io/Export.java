// code by jph
package ch.ethz.idsc.tensor.io;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;

import javax.imageio.ImageIO;

import ch.ethz.idsc.tensor.Tensor;

/** supported file formats are: CSV, JPG, M, PNG, TENSOR
 * 
 * <p>Do not use Export when exchanging {@link Tensor}s with
 * Mathematica. For that purpose use {@link Put} and {@link Get}.
 * 
 * <p>inspired by
 * <a href="https://reference.wolfram.com/language/ref/Export.html">Export</a> */
public enum Export {
  ;
  /** See the documentation of {@link CsvFormat}, {@link ImageFormat},
   * {@link MatlabExport}
   * for information on how tensors are encoded in the respective format.
   * 
   * @param file destination
   * @param tensor
   * @throws IOException */
  public static void of(File file, Tensor tensor) throws IOException {
    Filename filename = new Filename(file);
    if (filename.hasExtension("csv"))
      Files.write(file.toPath(), (Iterable<String>) CsvFormat.of(tensor)::iterator);
    else //
    if (filename.hasExtension("jpg"))
      ImageIO.write(ImageFormat.jpg(tensor), "jpg", file);
    else //
    if (filename.hasExtension("m"))
      Files.write(file.toPath(), (Iterable<String>) MatlabExport.of(tensor)::iterator);
    else //
    if (filename.hasExtension("png"))
      ImageIO.write(ImageFormat.of(tensor), "png", file);
    else //
      throw new RuntimeException(file.toString());
  }

  /** export function for Java objects that implement {@link Serializable}.
   * To retrieve the object, use {@link Import#object(File)}.
   * 
   * @param file
   * @param object implements {@link Serializable}
   * @throws IOException */
  public static void object(File file, Object object) throws IOException {
    Files.write(file.toPath(), ObjectFormat.of(object));
  }
}
