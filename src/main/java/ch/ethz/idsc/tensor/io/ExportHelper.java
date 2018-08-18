// code by jph
package ch.ethz.idsc.tensor.io;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.stream.Stream;

import javax.imageio.ImageIO;

import ch.ethz.idsc.tensor.Tensor;

/* package */ enum ExportHelper {
  ;
  /** @param extension
   * @param tensor
   * @param outputStream
   * @throws IOException */
  static void of(Extension extension, Tensor tensor, OutputStream outputStream) throws IOException {
    switch (extension) {
    case BMP:
    case JPG:
      ImageIO.write(ImageFormat.bgr(tensor), extension.name(), outputStream);
      break;
    case CSV:
      lines(CsvFormat.of(tensor), outputStream);
      break;
    case M:
      lines(MatlabExport.of(tensor), outputStream);
      break;
    case GIF:
    case PNG:
      ImageIO.write(ImageFormat.of(tensor), extension.name(), outputStream);
      break;
    default:
      throw new UnsupportedOperationException(extension.name());
    }
  }

  private static void lines(Stream<String> stream, OutputStream outputStream) {
    try (PrintWriter printWriter = new PrintWriter(outputStream)) {
      stream.sequential().forEach(printWriter::println);
    }
  }
}
