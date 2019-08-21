// code by jph
package ch.ethz.idsc.tensor.io;

import java.util.concurrent.TimeUnit;

import junit.framework.TestCase;

public class GifAnimationWriterTest extends TestCase {
  public void testSimple() {
    assertEquals(TimeUnit.MILLISECONDS.convert(1, TimeUnit.SECONDS), 1000L);
    assertEquals(TimeUnit.MILLISECONDS.convert(1000_000, TimeUnit.MICROSECONDS), 1000L);
  }
}
