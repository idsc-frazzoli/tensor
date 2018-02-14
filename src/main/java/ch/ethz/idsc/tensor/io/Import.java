// code by jph
package ch.ethz.idsc.tensor.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.util.zip.DataFormatException;

import javax.imageio.ImageIO;

import ch.ethz.idsc.tensor.Tensor;

/** supported file formats are: CSV, JPG, PNG, TENSOR
 * 
 * <p>Do not use Import when exchanging {@link Tensor}s with
 * Mathematica. For that purpose use {@link Put} and {@link Get}.
 * 
 * <p>In order to import content from jar files use {@link ResourceData}.
 * 
 * <p>inspired by
 * <a href="https://reference.wolfram.com/language/ref/Import.html">Import</a> */
public enum Import {
  ;
  /** supported extensions are
   * <ul>
   * <li>csv for {@link CsvFormat}
   * <li>jpg for {@link ImageFormat}
   * <li>png for {@link ImageFormat}
   * <li>tensor for {@link ObjectFormat}
   * </ul>
   * 
   * <p>Important: the import of jpg image files is not thoroughly verified.
   * 
   * 
   * 
   * @param file source
   * @return file content as {@link Tensor}
   * @throws ClassNotFoundException
   * @throws DataFormatException
   * @throws IOException
   * @see Get */
  public static Tensor of(File file) throws ClassNotFoundException, DataFormatException, IOException {
    Filename filename = new Filename(file);
    if (filename.hasExtension("csv"))
      /** gjoel found that {@link Files#lines(Path)} was unsuitable on Windows */
      try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
        return CsvFormat.parse(bufferedReader.lines());
      }
    if (filename.hasExtension("jpg") || //
        filename.hasExtension("png"))
      return ImageFormat.from(ImageIO.read(file));
    if (filename.hasExtension("tensor"))
      return object(file);
    throw new RuntimeException(file.toString());
  }

  /** import function for Java objects that implement {@link Serializable}
   * and were stored with {@link Export#object(File, Serializable)}.
   * 
   * @param file
   * @return object prior to serialization
   * @throws ClassNotFoundException
   * @throws DataFormatException
   * @throws IOException */
  public static <T> T object(File file) //
      throws ClassNotFoundException, DataFormatException, IOException {
    return ObjectFormat.parse(Files.readAllBytes(file.toPath()));
  }
}
