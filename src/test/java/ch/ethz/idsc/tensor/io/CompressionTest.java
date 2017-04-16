// code by jph
package ch.ethz.idsc.tensor.io;

import java.util.Arrays;
import java.util.Random;

import junit.framework.TestCase;

public class CompressionTest extends TestCase {
  private static byte[] createBytes(int length) {
    Random rnd = new Random();
    byte[] bytes = new byte[length];
    for (int count = 0; count < bytes.length; ++count)
      bytes[count] = (byte) rnd.nextInt(2);
    return bytes;
  }

  public void testInflate() {
    byte[] bytes = createBytes(1000);
    try {
      byte[] comp = Compression.deflate(bytes);
      byte[] deco = Compression.inflate(comp);
      assertTrue(Arrays.equals(bytes, deco));
    } catch (Exception exception) {
      exception.printStackTrace();
    }
  }

  public void testInflateEmpty() {
    byte[] bytes = createBytes(0);
    try {
      byte[] comp = Compression.deflate(bytes);
      byte[] deco = Compression.inflate(comp);
      assertTrue(Arrays.equals(bytes, deco));
    } catch (Exception exception) {
      exception.printStackTrace();
    }
  }

  public void testInflateEmpty2() {
    try {
      Compression.inflate(new byte[0]);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }

  public void testInflateCurrupt() {
    byte[] bytes = createBytes(1000);
    try {
      byte[] comp = Compression.deflate(bytes);
      comp[comp.length - 6] = (byte) (comp[comp.length - 6] - 23);
      comp[comp.length - 5] = (byte) (comp[comp.length - 5] - 23);
      comp[comp.length - 4] = (byte) (comp[comp.length - 4] - 23);
      comp[comp.length - 3] = (byte) (comp[comp.length - 3] - 23);
      Compression.inflate(comp);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }

  public void testInflateIncomplete() {
    byte[] bytes = createBytes(1000);
    try {
      byte[] comp = Compression.deflate(bytes);
      Compression.inflate(comp, 0, comp.length - 3);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }
}
