// code by jph
package ch.ethz.idsc.tensor.io;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import ch.ethz.idsc.tensor.Tensor;

public enum ObjectFormat {
  ;
  /** encodes {@link Serializable} input {@link Object} as array of bytes.
   * 
   * <p>since {@link Tensor}s implement {@link Serializable},
   * function can be used to encode tensor as byte array.
   * 
   * @param object
   * @return */
  public static <T extends Serializable> byte[] of(T object) {
    byte[] bytes = null;
    try {
      ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
      ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
      objectOutputStream.writeObject(object);
      objectOutputStream.flush();
      bytes = byteArrayOutputStream.toByteArray();
      objectOutputStream.close();
    } catch (Exception exception) {
      exception.printStackTrace();
    }
    return bytes;
  }

  /** decodes {@link Serializable} object from array of bytes
   * 
   * @param bytes
   * @return {@link Serializable} object encoded in input bytes */
  public static <T extends Serializable> T from(byte[] bytes) {
    try {
      ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
      ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
      @SuppressWarnings("unchecked")
      T tensor = (T) objectInputStream.readObject();
      objectInputStream.close();
      return tensor;
    } catch (Exception exception) {
      exception.printStackTrace();
    }
    return null;
  }
}
