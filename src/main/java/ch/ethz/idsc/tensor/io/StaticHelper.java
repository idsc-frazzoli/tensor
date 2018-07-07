// code by jph
package ch.ethz.idsc.tensor.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.stream.Stream;

import javax.imageio.ImageIO;

import ch.ethz.idsc.tensor.Scalars;
import ch.ethz.idsc.tensor.Tensor;

/* package */ enum StaticHelper {
  ;
  /** @param extension
   * @param inputStream
   * @return tensor parsed from given input stream
   * @throws IOException */
  static Tensor parse(Extension extension, InputStream inputStream) throws IOException {
    switch (extension) {
    case CSV:
      // gjoel found that {@link Files#lines(Path)} was unsuitable on Windows
      return CsvFormat.parse(lines(inputStream));
    case BMP:
    case JPG:
    case PNG:
      return ImageFormat.from(ImageIO.read(inputStream));
    case VECTOR:
      return Tensor.of(lines(inputStream).map(Scalars::fromString));
    default:
      throw new RuntimeException();
    }
  }

  /** @param inputStream
   * @return lines in given inputStream as stream of strings */
  private static Stream<String> lines(InputStream inputStream) {
    return new BufferedReader(new InputStreamReader(inputStream)).lines();
  }

  /***************************************************/
  /** @param extension
   * @param tensor
   * @param outputStream
   * @throws IOException */
  static void write(Extension extension, Tensor tensor, OutputStream outputStream) throws IOException {
    switch (extension) {
    case BMP:
    case JPG:
      ImageIO.write(ImageFormat.bgr(tensor), extension.name(), outputStream);
      break;
    case CSV:
      print(CsvFormat.of(tensor), outputStream);
      break;
    case M:
      print(MatlabExport.of(tensor), outputStream);
      break;
    case PNG:
      ImageIO.write(ImageFormat.of(tensor), extension.name(), outputStream);
      break;
    default:
      throw new RuntimeException();
    }
  }

  /** @param stream
   * @param outputStream */
  private static void print(Stream<String> stream, OutputStream outputStream) {
    try (PrintWriter printWriter = new PrintWriter(outputStream)) {
      stream.sequential().forEach(printWriter::println);
    }
  }
}
