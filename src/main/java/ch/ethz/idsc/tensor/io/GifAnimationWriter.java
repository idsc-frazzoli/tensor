// code by jph
package ch.ethz.idsc.tensor.io;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import ch.ethz.idsc.tensor.Tensor;

/** Example:
 * <pre>
 * try (AnimationWriter animationWriter = new GifAnimationWriter(file, 100, TimeUnit.MILLISECONDS)) {
 * animationWriter.append(bufferedImage);
 * ...
 * }
 * </pre>
 * 
 * in Mathematica, animated gif sequences are created by Mathematica::Export */
public class GifAnimationWriter implements AnimationWriter {
  private final AnimatedGifWriter animatedGifWriter;

  /** @param file typically with extension "gif"
   * @param period
   * @param timeUnit
   * @return
   * @throws IOException */
  public GifAnimationWriter(File file, int period, TimeUnit timeUnit) throws IOException {
    animatedGifWriter = AnimatedGifWriter.of(file, (int) TimeUnit.MILLISECONDS.convert(period, timeUnit));
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
