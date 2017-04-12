// code by jph
package ch.ethz.idsc.tensor.io;

import java.io.Serializable;
import java.util.zip.DataFormatException;

/** ObjectFormat is the serialization of objects in deflated form.
 * 
 * <p>The motivation to compress the byte arrays stems from the fact that
 * Java native serialization is not space efficient.
 * Compression factors of up to 10 are expected. */
public enum ObjectFormat {
  ;
  // ---
  /** @param object
   * @return deflated serialization of object
   * @throws Exception */
  public static <T extends Serializable> byte[] of(T object) throws Exception {
    return Compression.deflate(Serialization.of(object));
  }

  /** @param bytes containing the deflated serialization of object
   * @return object prior to serialization
   * @throws DataFormatException */
  public static <T extends Serializable> T parse(byte[] bytes) throws Exception {
    return Serialization.parse(Compression.inflate(bytes));
  }
}
