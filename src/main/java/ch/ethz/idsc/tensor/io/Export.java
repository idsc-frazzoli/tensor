// code by jph
package ch.ethz.idsc.tensor.io;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.nio.file.Files;
import java.util.zip.GZIPOutputStream;

import javax.imageio.ImageIO;

import ch.ethz.idsc.tensor.Tensor;

/** supported file formats are: BMP, CSV, CSV.GZ, JPG, M, PNG
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
    if (filename.has(Extension.BMP))
      ImageIO.write(ImageFormat.bgr(tensor), "bmp", file);
    else //
    if (filename.has(Extension.CSV))
      Files.write(file.toPath(), (Iterable<String>) CsvFormat.of(tensor)::iterator);
    else //
    if (filename.has(Extension.CSV_GZ))
      try (PrintWriter printWriter = new PrintWriter(new GZIPOutputStream(new FileOutputStream(file)))) {
        CsvFormat.of(tensor).forEach(printWriter::println);
      }
    else //
    if (filename.has(Extension.JPG))
      ImageIO.write(ImageFormat.bgr(tensor), "jpg", file);
    else //
    if (filename.has(Extension.M))
      Files.write(file.toPath(), (Iterable<String>) MatlabExport.of(tensor)::iterator);
    else //
    if (filename.has(Extension.PNG))
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
