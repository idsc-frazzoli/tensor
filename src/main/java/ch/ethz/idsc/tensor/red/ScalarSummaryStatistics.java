// code by jph
package ch.ethz.idsc.tensor.red;

import java.util.IntSummaryStatistics;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.stream.Collector;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;

/** ScalarSummaryStatistics tracks the sum, minimum, and maximum in a single
 * pass over a stream of {@link Scalar}s. The stream may be processed in
 * parallel.
 * 
 * <p>The scalars are required to be comparable. For instance, complex numbers
 * do not have a natural ordering, therefore the minimum is not well defined.
 * 
 * <p>inspired by {@link IntSummaryStatistics} */
public class ScalarSummaryStatistics implements Consumer<Scalar> {
  /** Example:
   * <pre>
   * ScalarSummaryStatistics scalarSummaryStatistics = Tensors.vector(1, 4, 2, 8, 3, 10)
   * .stream().parallel().map(Scalar.class::cast).collect(ScalarSummaryStatistics.collector());
   * scalarSummaryStatistics.getMin() == 1
   * scalarSummaryStatistics.getMax() == 10
   * scalarSummaryStatistics.getSum() == 28
   * </pre>
   * 
   * @return */
  public static Collector<Scalar, ScalarSummaryStatistics, ScalarSummaryStatistics> collector() {
    return ScalarSummaryStatisticsCollector.INSTANCE;
  }

  // ---
  private Scalar sum = null;
  private Scalar min = null;
  private Scalar max = null;
  private long count = 0;

  @Override // from Consumer
  public void accept(Scalar scalar) {
    if (Objects.isNull(sum)) {
      sum = scalar;
      min = scalar;
      max = scalar;
    } else {
      sum = sum.add(scalar);
      min = Min.of(min, scalar);
      max = Max.of(max, scalar);
    }
    ++count;
  }

  public ScalarSummaryStatistics combine(ScalarSummaryStatistics other) {
    if (0 == count) {
      sum = other.sum;
      min = other.min;
      max = other.max;
    } else //
    if (0 < other.count) {
      sum = sum.add(other.sum);
      min = Min.of(min, other.min);
      max = Max.of(max, other.max);
    }
    count += other.count;
    return this;
  }

  /** @return sum of scalars in stream or null if stream is empty */
  public Scalar getSum() {
    return sum;
  }

  /** @return min of scalars in stream or null if stream is empty */
  public Scalar getMin() {
    return min;
  }

  /** @return max of scalars in stream or null if stream is empty */
  public Scalar getMax() {
    return max;
  }

  /** @return average of scalars in stream or null if stream is empty
   * @throws Exception if scalar type does not support division by {@link RealScalar} */
  public Scalar getAverage() {
    return 0 < count ? getSum().divide(RealScalar.of(getCount())) : null;
  }

  /** @return number of scalars in stream */
  public long getCount() {
    return count;
  }
}
