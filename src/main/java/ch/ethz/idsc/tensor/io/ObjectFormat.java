// code by jph
package ch.ethz.idsc.tensor.io;

import java.io.IOException;
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
   * @throws IOException */
  public static <T extends Serializable> byte[] of(T object) throws IOException {
    return Compression.deflate(Serialization.of(object));
  }

  /** @param bytes containing the deflated serialization of object
   * @return object prior to serialization
   * @throws ClassNotFoundException
   * @throws DataFormatException
   * @throws IOException */
  public static <T extends Serializable> T parse(byte[] bytes) //
      throws ClassNotFoundException, DataFormatException, IOException {
    return Serialization.parse(Compression.inflate(bytes));
  }
}
