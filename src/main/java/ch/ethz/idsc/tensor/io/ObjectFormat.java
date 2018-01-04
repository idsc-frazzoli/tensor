// code by jph
package ch.ethz.idsc.tensor.io;

import java.io.IOException;
import java.io.Serializable;
import java.util.zip.DataFormatException;

/** ObjectFormat is the serialization of objects in deflated form.
 * 
 * <p>The motivation to compress the byte arrays stems from the fact that
 * Java native serialization is not space efficient.
 * Compression factors of up to 10 are expected.
 * 
 * <p>In order to store an object to a file, use
 * {@link Export#object(java.io.File, Serializable)}, or
 * <code>Files.write(path, ObjectFormat.of(object));</code>
 * 
 * <p>In order to read an object from a file, use
 * {@link Import#object(java.io.File)}, or
 * <code>ObjectFormat.parse(Files.readAllBytes(path));</code> */
public enum ObjectFormat {
  ;
  /** @param object
   * @return deflated serialization of object as byte array
   * @throws IOException */
  public static byte[] of(Object object) throws IOException {
    return Compression.deflate(Serialization.of(object));
  }

  /** @param bytes containing the deflated serialization of object
   * @return object prior to serialization
   * @throws ClassNotFoundException
   * @throws DataFormatException
   * @throws IOException */
  public static <T> T parse(byte[] bytes) //
      throws ClassNotFoundException, DataFormatException, IOException {
    return Serialization.parse(Compression.inflate(bytes));
  }
}
