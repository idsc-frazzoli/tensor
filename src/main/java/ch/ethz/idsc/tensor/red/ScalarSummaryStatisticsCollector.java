// code by jph
package ch.ethz.idsc.tensor.red;

import java.util.EnumSet;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

import ch.ethz.idsc.tensor.Scalar;

/* package */ enum ScalarSummaryStatisticsCollector implements //
    Collector<Scalar, ScalarSummaryStatistics, ScalarSummaryStatistics> {
  INSTANCE;
  // ---
  @Override // from Collector
  public Supplier<ScalarSummaryStatistics> supplier() {
    return ScalarSummaryStatistics::new;
  }

  @Override // from Collector
  public BiConsumer<ScalarSummaryStatistics, Scalar> accumulator() {
    return ScalarSummaryStatistics::accept;
  }

  @Override // from Collector
  public BinaryOperator<ScalarSummaryStatistics> combiner() {
    return ScalarSummaryStatistics::combine;
  }

  @Override // from Collector
  public Function<ScalarSummaryStatistics, ScalarSummaryStatistics> finisher() {
    return Function.identity();
  }

  @Override // from Collector
  public Set<Characteristics> characteristics() {
    return EnumSet.of(Characteristics.CONCURRENT, Characteristics.IDENTITY_FINISH);
  }
}
