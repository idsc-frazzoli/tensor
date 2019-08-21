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
  public void write(BufferedImage bufferedImage) throws Exception {
    animatedGifWriter.write(bufferedImage);
  }

  @Override // from AnimationWriter
  public void write(Tensor tensor) throws Exception {
    write(ImageFormat.of(tensor));
  }

  @Override // from AutoCloseable
  public void close() throws IOException {
    animatedGifWriter.close();
  }
}
