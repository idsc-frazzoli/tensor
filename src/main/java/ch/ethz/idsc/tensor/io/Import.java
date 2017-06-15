// code by jph
package ch.ethz.idsc.tensor.io;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.util.zip.DataFormatException;

import javax.imageio.ImageIO;

import ch.ethz.idsc.tensor.Tensor;

/** supported file formats are: CSV, PNG, TENSOR
 * 
 * <p>Do not use Import when exchanging {@link Tensor}s with
 * Mathematica. For that purpose use {@link Put} and {@link Get}.
 * 
 * <p>inspired by
 * <a href="https://reference.wolfram.com/language/ref/Import.html">Import</a> */
public enum Import {
  ;
  /** @param file source
   * @return file content as {@link Tensor}
   * @throws ClassNotFoundException
   * @throws DataFormatException
   * @throws IOException */
  public static Tensor of(File file) //
      throws ClassNotFoundException, DataFormatException, IOException {
    Filename filename = new Filename(file);
    if (filename.hasExtension("csv"))
      return CsvFormat.parse(Files.lines(file.toPath()));
    if (filename.hasExtension("png"))
      return ImageFormat.from(ImageIO.read(file));
    if (filename.hasExtension("tensor"))
      return ObjectFormat.parse(Files.readAllBytes(file.toPath()));
    throw new RuntimeException();
  }

  /** import function for Java objects that implement {@link Serializable}
   * and were stored with {@link Export#object(File, Serializable)}.
   * 
   * @param file
   * @return object prior to serialization
   * @throws ClassNotFoundException
   * @throws DataFormatException
   * @throws IOException */
  public static <T extends Serializable> T object(File file) //
      throws ClassNotFoundException, DataFormatException, IOException {
    return ObjectFormat.parse(Files.readAllBytes(file.toPath()));
  }
}
