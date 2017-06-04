// code by jph
package ch.ethz.idsc.tensor.io;

import java.awt.image.BufferedImage;
import java.io.File;

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

  void append(BufferedImage bufferedImage) throws Exception;
}
