// code by jph
package ch.ethz.idsc.tensor.red;

import ch.ethz.idsc.tensor.RationalScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.qty.Quantity;
import junit.framework.TestCase;

public class ScalarSummaryStatisticsTest extends TestCase {
  public void testSimple() {
    ScalarSummaryStatistics stats = Tensors.vector(1, 4, 2, 8, 3, 10).stream() //
        .parallel() //
        .map(Scalar.class::cast) //
        .collect( //
            ScalarSummaryStatistics::new, //
            ScalarSummaryStatistics::accept, //
            ScalarSummaryStatistics::combine);
    assertEquals(stats.getSum(), RealScalar.of(28));
    assertEquals(stats.getMin(), RealScalar.of(1));
    assertEquals(stats.getMax(), RealScalar.of(10));
    assertEquals(stats.getAverage(), RationalScalar.of(14, 3));
    assertEquals(stats.getCount(), 6);
  }

  public void testQuantity() {
    ScalarSummaryStatistics stats = Tensors.fromString("{3[s],11[s],6[s],4[s]}").stream() //
        .parallel() //
        .map(Scalar.class::cast) //
        .collect( //
            ScalarSummaryStatistics::new, //
            ScalarSummaryStatistics::accept, //
            ScalarSummaryStatistics::combine);
    assertEquals(stats.getSum(), Quantity.of(24, "s"));
    assertEquals(stats.getMin(), Quantity.of(3, "s"));
    assertEquals(stats.getMax(), Quantity.of(11, "s"));
    assertEquals(stats.getAverage(), Quantity.of(6, "s"));
    assertEquals(stats.getCount(), 4);
  }
}
