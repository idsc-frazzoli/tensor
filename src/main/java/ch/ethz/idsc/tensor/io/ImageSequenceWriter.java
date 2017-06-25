// code by jph
package ch.ethz.idsc.tensor.io;

import java.awt.image.BufferedImage;
import java.io.File;

import ch.ethz.idsc.tensor.Tensor;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/ListAnimate.html">ListAnimate</a> */
/* package */ interface ImageSequenceWriter extends AutoCloseable {
  // EXPERIMENTAL - API not finalized
  static ImageSequenceWriter of(File file, int period) throws Exception {
    Filename filename = new Filename(file);
    if (filename.hasExtension("gif"))
      return GifSequenceWriter.of(file, period);
    throw new RuntimeException(file.toString());
  }

  /** @param bufferedImage to append to the sequence
   * @throws Exception */
  void append(BufferedImage bufferedImage) throws Exception;

  /** @param tensor to append to the sequence
   * @throws Exception */
  void append(Tensor tensor) throws Exception;
}
