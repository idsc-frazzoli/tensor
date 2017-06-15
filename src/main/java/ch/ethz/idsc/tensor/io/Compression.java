// code adapted from 
// http://qupera.blogspot.ch/2013/02/howto-compress-and-uncompress-java-byte.html
package ch.ethz.idsc.tensor.io;

import java.io.ByteArrayOutputStream;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

/** class makes use of compression utilities provided by java.util.zip to compress byte arrays
 * {@link Deflater} and {@link Inflater} */
public enum Compression {
  ;
  private static final int SIZE = 4096;

  /** compression
   * 
   * @param data
   * @return byte array that is typically smaller than the input data */
  public static byte[] deflate(byte[] data) {
    Deflater deflater = new Deflater();
    deflater.setInput(data);
    deflater.finish();
    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    byte[] buffer = new byte[SIZE];
    while (!deflater.finished()) {
      int length = deflater.deflate(buffer);
      byteArrayOutputStream.write(buffer, 0, length);
    }
    deflater.end();
    return byteArrayOutputStream.toByteArray();
  }

  /** decompression
   * 
   * @param data
   * @return byte array that is typically larger than the input data
   * @throws DataFormatException */
  public static byte[] inflate(byte[] data) throws DataFormatException {
    return inflate(data, 0, data.length);
  }

  // helper function
  /* package */ static byte[] inflate(byte[] data, int off, int len) throws DataFormatException {
    Inflater inflater = new Inflater();
    inflater.setInput(data, off, len);
    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    byte[] buffer = new byte[SIZE];
    while (true) {
      int length = inflater.inflate(buffer);
      byteArrayOutputStream.write(buffer, 0, length);
      if (inflater.finished())
        break;
      else if (length == 0)
        throw new DataFormatException();
    }
    inflater.end();
    return byteArrayOutputStream.toByteArray();
  }
}
