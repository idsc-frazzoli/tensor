// code by jph
package ch.ethz.idsc.tensor.red;

import java.util.Arrays;
import java.util.IntSummaryStatistics;

import ch.ethz.idsc.tensor.GaussScalar;
import ch.ethz.idsc.tensor.RationalScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.qty.Quantity;
import junit.framework.TestCase;

public class ScalarSummaryStatisticsTest extends TestCase {
  public void testMembers() {
    ScalarSummaryStatistics scalarSummaryStatistics = Tensors.vector(1, 4, 2, 8, 3, 10) //
        .stream().parallel().map(Scalar.class::cast).collect(ScalarSummaryStatistics.collector());
    assertEquals(scalarSummaryStatistics.getSum(), RealScalar.of(28));
    assertEquals(scalarSummaryStatistics.getMin(), RealScalar.of(1));
    assertEquals(scalarSummaryStatistics.getMax(), RealScalar.of(10));
    assertEquals(scalarSummaryStatistics.getAverage(), RationalScalar.of(14, 3));
    assertEquals(scalarSummaryStatistics.getCount(), 6);
  }

  public void testQuantity() {
    ScalarSummaryStatistics stats = Tensors.fromString("{3[s],11[s],6[s],4[s]}").stream() //
        .parallel().map(Scalar.class::cast).collect(ScalarSummaryStatistics.collector());
    assertEquals(stats.getSum(), Quantity.of(24, "s"));
    assertEquals(stats.getMin(), Quantity.of(3, "s"));
    assertEquals(stats.getMax(), Quantity.of(11, "s"));
    assertEquals(stats.getAverage(), Quantity.of(6, "s"));
    assertEquals(stats.getCount(), 4);
  }

  public void testCollector() {
    ScalarSummaryStatistics stats = Tensors.vector(1, 4, 2, 8, 3, 10).stream() //
        .parallel().map(Scalar.class::cast).collect(ScalarSummaryStatistics.collector());
    assertEquals(stats.getSum(), RealScalar.of(28));
    assertEquals(stats.getMin(), RealScalar.of(1));
    assertEquals(stats.getMax(), RealScalar.of(10));
    assertEquals(stats.getAverage(), RationalScalar.of(14, 3));
    assertEquals(stats.getCount(), 6);
  }

  public void testEmpty() {
    ScalarSummaryStatistics stats = Tensors.empty().stream() //
        .parallel().map(Scalar.class::cast).collect(ScalarSummaryStatistics.collector());
    assertEquals(stats.getSum(), null);
    assertEquals(stats.getMin(), null);
    assertEquals(stats.getMax(), null);
    assertEquals(stats.getAverage(), null);
    assertEquals(stats.getCount(), 0);
  }

  public void testEmptyCombine() {
    IntSummaryStatistics iss1 = new IntSummaryStatistics();
    IntSummaryStatistics iss2 = new IntSummaryStatistics();
    iss1.combine(iss2);
    ScalarSummaryStatistics sss1 = new ScalarSummaryStatistics();
    ScalarSummaryStatistics sss2 = new ScalarSummaryStatistics();
    sss1.combine(sss2);
  }

  public void testSemiCombine() {
    IntSummaryStatistics iss1 = new IntSummaryStatistics();
    IntSummaryStatistics iss2 = Arrays.asList(3, 2).stream() //
        .mapToInt(Integer::intValue) //
        .summaryStatistics();
    iss1.combine(iss2);
    ScalarSummaryStatistics sss1 = new ScalarSummaryStatistics();
    ScalarSummaryStatistics sss2 = Tensors.vector(1, 4, 2, 8, 3, 10).stream() //
        .parallel().map(Scalar.class::cast).collect(ScalarSummaryStatistics.collector());
    sss1.combine(sss2);
  }

  public void testGaussian() {
    Tensor vector = Tensors.of( //
        GaussScalar.of(2, 7), //
        GaussScalar.of(3, 7), //
        GaussScalar.of(5, 7), //
        GaussScalar.of(1, 7));
    ScalarSummaryStatistics sss1 = //
        vector.stream().parallel().map(Scalar.class::cast).collect(ScalarSummaryStatistics.collector());
    Scalar sum = sss1.getSum();
    assertEquals(sum, GaussScalar.of(4, 7));
    assertEquals(sss1.getCount(), 4);
    try {
      sss1.getAverage();
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }
}
