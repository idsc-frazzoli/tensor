// code by jph
package ch.ethz.idsc.tensor.io;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.util.zip.GZIPOutputStream;

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
  /** See the documentation of {@link CsvFormat}, {@link ImageFormat}, and {@link MatlabExport}
   * for information on how tensors are encoded in the respective format.
   * 
   * If the extension of the given file is not used in the tensor library, an exception
   * is thrown, and the file will not be created.
   * 
   * @param file destination
   * @param tensor
   * @throws IOException */
  public static void of(File file, Tensor tensor) throws IOException {
    Filename filename = new Filename(file);
    Extension extension = filename.extension();
    try (FileOutputStream fileOutputStream = new FileOutputStream(file)) {
      if (extension.equals(Extension.GZ))
        try (GZIPOutputStream gzipOutputStream = new GZIPOutputStream(fileOutputStream)) {
          ExportHelper.of(filename.truncate().extension(), tensor, gzipOutputStream);
        }
      else
        ExportHelper.of(extension, tensor, fileOutputStream);
    }
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
