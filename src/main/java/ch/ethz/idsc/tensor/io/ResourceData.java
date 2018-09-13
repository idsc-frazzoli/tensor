// code by jph
package ch.ethz.idsc.tensor.io;

import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.util.Properties;

import javax.imageio.ImageIO;

import ch.ethz.idsc.tensor.Tensor;

/** access to resource data in jar files, for instance,
 * the content included in the tensor library.
 * 
 * <p>Tensor resources provided by the tensor library include
 * <pre>
 * /colorscheme/classic.csv
 * /number/primes.vector
 * </pre>
 * 
 * <p>Properties provided by the tensor library include
 * <pre>
 * /unit/si.properties
 * </pre>
 * 
 * <p>inspired by
 * <a href="https://reference.wolfram.com/language/ref/ResourceData.html">ResourceData</a> */
public enum ResourceData {
  ;
  /** Example use:
   * Interpolation interpolation = LinearInterpolation.of(ResourceData.of("/colorscheme/classic.csv"));
   * 
   * @param string as path to resource
   * @return imported tensor, or null if resource could not be loaded */
  public static Tensor of(String string) {
    try (InputStream inputStream = ResourceData.class.getResourceAsStream(string)) {
      return ImportHelper.of(new Filename(string), inputStream);
    } catch (Exception exception) {
      // ---
    }
    return null;
  }

  /** @param string as path to resource
   * @return imported object, or null if resource could not be loaded */
  public static <T> T object(String string) {
    try (InputStream inputStream = ResourceData.class.getResourceAsStream(string)) {
      return ImportHelper.object(inputStream);
    } catch (Exception exception) {
      // ---
    }
    return null;
  }

  /** @param string as path to resource
   * @return imported properties, or null if resource could not be loaded */
  public static Properties properties(String string) {
    try (InputStream inputStream = ResourceData.class.getResourceAsStream(string)) {
      return ImportHelper.properties(inputStream);
    } catch (Exception exception) {
      // ---
    }
    return null;
  }

  /** Hint: function bypasses conversion of image to tensor. When the
   * image is needed as a {@link Tensor}, rather use {@link #of(String)}
   * 
   * @param string as path to resource
   * @return imported image, or null if resource could not be loaded */
  public static BufferedImage bufferedImage(String string) {
    try (InputStream inputStream = ResourceData.class.getResourceAsStream(string)) {
      return ImageIO.read(inputStream);
    } catch (Exception exception) {
      // ---
    }
    return null;
  }
}
