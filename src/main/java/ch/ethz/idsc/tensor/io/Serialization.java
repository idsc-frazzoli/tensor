// code by jph
package ch.ethz.idsc.tensor.io;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public enum Serialization {
  ;
  /** encodes {@link Serializable} input {@link Object} as array of bytes.
   * 
   * <p>In order to store the object <b>uncompressed</b> to a file, use
   * <code>Files.write(Paths.get("filePath"), bytes)</code>
   * 
   * @param object
   * @return serialization of object
   * @throws IOException
   * @see ObjectFormat
   * @see Export */
  public static byte[] of(Object object) throws IOException {
    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream)) {
      objectOutputStream.writeObject(object);
      objectOutputStream.flush();
      return byteArrayOutputStream.toByteArray();
    }
  }

  /** decodes {@link Serializable} object from array of bytes,
   * deserialization of object
   * 
   * In order to retrieve the object from an <b>uncompressed</b> file, use
   * <code>Files.readAllBytes(Paths.get("filePath"))</code>
   * 
   * @param bytes
   * @return {@link Serializable} object encoded in input bytes
   * @throws ClassNotFoundException
   * @throws IOException
   * @see ObjectFormat
   * @see Import */
  @SuppressWarnings("unchecked")
  public static <T> T parse(byte[] bytes) throws ClassNotFoundException, IOException {
    ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
    try (ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream)) {
      return (T) objectInputStream.readObject();
    }
  }

  /** function also known as "deepcopy"
   * 
   * @param object that implements {@link Serializable}
   * @return new instance of T with identical content as given object
   * @throws ClassNotFoundException
   * @throws IOException */
  public static <T> T copy(T object) throws ClassNotFoundException, IOException {
    return parse(of(object));
  }
}
