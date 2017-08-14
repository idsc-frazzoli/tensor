// code by jph
package ch.ethz.idsc.tensor.io;

import java.awt.image.BufferedImage;
import java.io.File;

import ch.ethz.idsc.tensor.Tensor;

/** export sequence of images to an animation file.
 * currently supported is the export to animated gif files.
 * 
 * <p>in Mathematica, animated gif sequences are created by Mathematica::Export */
public interface AnimationWriter extends AutoCloseable {
  /** @param file
   * @param period between frames in milliseconds
   * @return
   * @throws Exception */
  public static AnimationWriter of(File file, int period) throws Exception {
    Filename filename = new Filename(file);
    if (filename.hasExtension("gif"))
      return new GifAnimationWriter(file, period);
    throw new RuntimeException(file.toString());
  }

  /** @param bufferedImage to append to the image sequence of the animation
   * @throws Exception */
  void append(BufferedImage bufferedImage) throws Exception;

  /** @param tensor to append to the image sequence of the animation
   * @throws Exception */
  void append(Tensor tensor) throws Exception;
}
