// code by jph
package ch.ethz.idsc.tensor.io;

import java.io.File;
import java.io.InputStream;
import java.util.Properties;

import javax.imageio.ImageIO;

import ch.ethz.idsc.tensor.Scalars;
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
   * @param string
   * @return imported tensor, or null if resource could not be loaded */
  public static Tensor of(String string) {
    try (InputStream inputStream = ResourceData.class.getResourceAsStream(string)) {
      Filename filename = new Filename(new File(string)); // to determine file extension
      if (filename.has(Extension.CSV))
        return StaticHelper.csv(inputStream);
      if (filename.has(Extension.BMP) || //
          filename.has(Extension.JPG) || //
          filename.has(Extension.PNG))
        return ImageFormat.from(ImageIO.read(inputStream));
      if (filename.has(Extension.VECTOR))
        return Tensor.of(StaticHelper.lines(inputStream).map(Scalars::fromString));
    } catch (Exception exception) {
      // ---
    }
    return null;
  }

  /** @param string
   * @return imported properties, or null if resource could not be loaded */
  public static Properties properties(String string) {
    try (InputStream inputStream = ResourceData.class.getResourceAsStream(string)) {
      Properties properties = new Properties();
      properties.load(inputStream);
      return properties;
    } catch (Exception exception) {
      // ---
    }
    return null;
  }

  /** @param string
   * @return imported object, or null if resource could not be loaded */
  public static <T> T object(String string) {
    try (InputStream inputStream = ResourceData.class.getResourceAsStream(string)) {
      int length = inputStream.available();
      byte[] bytes = new byte[length];
      inputStream.read(bytes);
      return ObjectFormat.parse(bytes);
    } catch (Exception exception) {
      // ---
    }
    return null;
  }
}
