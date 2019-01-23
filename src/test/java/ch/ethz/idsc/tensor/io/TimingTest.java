// code by jph
package ch.ethz.idsc.tensor.io;

import java.io.Serializable;

import junit.framework.TestCase;

public class TimingTest extends TestCase {
  public void testSimple() {
    Timing timing = Timing.stopped();
    assertEquals(timing.nanoSeconds(), 0);
    assertEquals(timing.seconds(), 0.0);
    try {
      timing.stop();
      fail();
    } catch (Exception exception) {
      // ---
    }
    timing.start();
    Math.sin(1);
    assertTrue(0 < timing.nanoSeconds());
    timing.stop();
    assertTrue(0 < timing.seconds());
    assertEquals(timing.seconds(), timing.nanoSeconds() * 1e-9);
  }

  public void testNonSerializable() {
    Timing timing = Timing.started();
    assertFalse(timing instanceof Serializable);
  }

  public void testStartedFail() {
    Timing timing = Timing.started();
    try {
      timing.start();
      fail();
    } catch (Exception exception) {
      // ---
    }
    assertTrue(0 < timing.nanoSeconds());
  }

  public void testStopFail() {
    Timing timing = Timing.started();
    Math.sin(1);
    assertTrue(0 < timing.nanoSeconds());
    timing.stop();
    try {
      timing.stop();
      fail();
    } catch (Exception exception) {
      // ---
    }
  }
}
