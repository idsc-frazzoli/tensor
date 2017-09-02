// code by jph
package ch.ethz.idsc.tensor.sca;

import java.math.MathContext;

import ch.ethz.idsc.tensor.DecimalScalar;
import ch.ethz.idsc.tensor.DoubleScalar;
import ch.ethz.idsc.tensor.Scalar;

/** interface may be implemented by {@link Scalar}
 * to support the conversion to numeric precision */
public interface NInterface { /* optional interface */
  /** @return numerical approximation of this scalar as a {@link DoubleScalar}
   * for instance 1/3 is converted to 1.0/3.0 == 0.3333... */
  Scalar n();

  /** @param mathContext
   * @return this instance in the given context as a {@link DecimalScalar} */
  Scalar n(MathContext mathContext);
}
