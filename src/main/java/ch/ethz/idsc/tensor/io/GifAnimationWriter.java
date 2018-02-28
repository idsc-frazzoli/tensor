// code by jph
package ch.ethz.idsc.tensor.io;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import ch.ethz.idsc.tensor.Tensor;

/** wraps {@link AnimatedGifWriter} as {@link AnimationWriter} */
/* package */ class GifAnimationWriter implements AnimationWriter {
  private final AnimatedGifWriter animatedGifWriter;

  public GifAnimationWriter(File file, int period) throws IOException {
    animatedGifWriter = AnimatedGifWriter.of(file, period);
  }

  @Override // from AnimationWriter
  public void append(BufferedImage bufferedImage) throws Exception {
    animatedGifWriter.append(bufferedImage);
  }

  @Override // from AnimationWriter
  public void append(Tensor tensor) throws Exception {
    append(ImageFormat.of(tensor));
  }

  @Override // from AutoCloseable
  public void close() throws Exception {
    animatedGifWriter.close();
  }
}
