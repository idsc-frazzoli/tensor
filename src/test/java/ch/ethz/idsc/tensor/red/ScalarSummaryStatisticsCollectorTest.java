// code by jph
package ch.ethz.idsc.tensor.red;

import junit.framework.TestCase;

public class ScalarSummaryStatisticsCollectorTest extends TestCase {
  public void testFinisher() {
    assertNull(ScalarSummaryStatisticsCollector.INSTANCE.finisher().apply(null));
  }
}
