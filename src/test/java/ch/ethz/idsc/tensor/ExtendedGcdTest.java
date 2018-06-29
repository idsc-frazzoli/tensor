// code by jph
package ch.ethz.idsc.tensor;

import java.util.Random;

import junit.framework.TestCase;

public class ExtendedGcdTest extends TestCase {
  public void testConsistent() {
    assertTrue(new ExtendedGcd(0, 0).isConsistent());
    assertTrue(new ExtendedGcd(3, 7).isConsistent());
    assertTrue(new ExtendedGcd(5, 6).isConsistent());
    assertTrue(new ExtendedGcd(-2345, 4566).isConsistent());
    assertTrue(new ExtendedGcd(2345, 9837).isConsistent());
    assertTrue(new ExtendedGcd(3856, -29832).isConsistent());
  }

  public void testRandom() {
    Random random = new Random();
    for (int count = 0; count < 100; ++count)
      assertTrue(new ExtendedGcd(random.nextLong(), random.nextLong()).isConsistent());
  }
}
