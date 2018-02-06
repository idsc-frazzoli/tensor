// code by jph
package ch.ethz.idsc.tensor.red;

import java.util.IntSummaryStatistics;
import java.util.Objects;
import java.util.function.Consumer;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;

/** API inspired by {@link IntSummaryStatistics} */
public class ScalarSummaryStatistics implements Consumer<Scalar> {
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

  public void combine(ScalarSummaryStatistics other) {
    sum = sum.add(other.sum);
    min = Min.of(min, other.min);
    max = Max.of(max, other.max);
    count += other.count;
  }

  public Scalar getSum() {
    return sum;
  }

  public Scalar getMin() {
    return min;
  }

  public Scalar getMax() {
    return max;
  }

  public Scalar getAverage() {
    return count > 0 ? getSum().divide(RealScalar.of(getCount())) : RealScalar.ZERO;
  }

  public long getCount() {
    return count;
  }
}
