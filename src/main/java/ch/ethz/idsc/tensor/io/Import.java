// code by jph
package ch.ethz.idsc.tensor.io;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.zip.DataFormatException;

import javax.imageio.ImageIO;

import ch.ethz.idsc.tensor.Tensor;

/** supported file formats are: CSV, MATHEMATICA, PNG, TENSOR
 * 
 * <p>inspired by
 * <a href="https://reference.wolfram.com/language/ref/Import.html">Import</a> */
public enum Import {
  ;
  // ---
  /** @param file
   * @return
   * @throws ClassNotFoundException
   * @throws DataFormatException
   * @throws IOException */
  public static Tensor of(File file) //
      throws ClassNotFoundException, DataFormatException, IOException {
    Filename filename = new Filename(file);
    if (filename.hasExtension("csv"))
      return CsvFormat.parse(Files.lines(file.toPath()));
    if (filename.hasExtension("mathematica"))
      return MathematicaFormat.parse(Files.lines(file.toPath()));
    if (filename.hasExtension("png"))
      return ImageFormat.from(ImageIO.read(file));
    if (filename.hasExtension("tensor"))
      return ObjectFormat.parse(Files.readAllBytes(file.toPath()));
    throw new RuntimeException();
  }
}
