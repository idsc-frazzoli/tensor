// code by jph
package ch.ethz.idsc.tensor.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.util.zip.DataFormatException;
import java.util.zip.GZIPInputStream;

import javax.imageio.ImageIO;

import ch.ethz.idsc.tensor.Tensor;

/** supported file formats are: CSV, JPG, PNG, TENSOR
 * 
 * <p>Do not use Import when exchanging {@link Tensor}s with
 * Mathematica. For that purpose use {@link Put} and {@link Get}.
 * 
 * <p>In order to import content from jar files use {@link ResourceData}.
 * 
 * <p>See also the documentation of {@link CsvFormat} regarding the decimal
 * format for numeric import using the tensor library.
 * 
 * <p>inspired by
 * <a href="https://reference.wolfram.com/language/ref/Import.html">Import</a> */
public enum Import {
  ;
  /** supported extensions are
   * <ul>
   * <li>bmp for {@link ImageFormat}
   * <li>csv for {@link CsvFormat}
   * <li>csv.gz for compressed {@link CsvFormat}
   * <li>jpg for {@link ImageFormat}
   * <li>png for {@link ImageFormat}
   * </ul>
   * 
   * @param file source
   * @return file content as {@link Tensor}
   * @throws IOException
   * @see Get */
  public static Tensor of(File file) throws IOException {
    Filename filename = new Filename(file);
    if (filename.has(Extension.CSV))
      // gjoel found that {@link Files#lines(Path)} was unsuitable on Windows
      try (InputStream inputStream = new FileInputStream(file)) {
        return StaticHelper.csv(inputStream);
      }
    if (filename.has(Extension.CSV_GZ))
      try (InputStream inputStream = new GZIPInputStream(new FileInputStream(file))) {
        return StaticHelper.csv(inputStream);
      }
    if (filename.has(Extension.BMP) || //
        filename.has(Extension.JPG) || //
        filename.has(Extension.PNG))
      return ImageFormat.from(ImageIO.read(file));
    throw new RuntimeException(file.toString());
  }

  /** import function for Java objects that implement {@link Serializable}
   * and were stored with {@link Export#object(File, Serializable)}.
   * 
   * @param file
   * @return object prior to serialization
   * @throws IOException
   * @throws ClassNotFoundException
   * @throws DataFormatException */
  public static <T> T object(File file) //
      throws IOException, ClassNotFoundException, DataFormatException {
    return ObjectFormat.parse(Files.readAllBytes(file.toPath()));
  }
}
