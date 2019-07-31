// code by jph
package ch.ethz.idsc.tensor.num;

import java.util.function.Function;

import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/ExtendedGCD.html">ExtendedGCD</a> */
public interface ExtendedGCD {
  static Function<Tensor, ExtendedGCD> function(Scalar one) {
    return new ExtendedGCDWrap(one)::function;
  }

  /** @return greatest common divider */
  Scalar gcd();

  /** @return vector of factors so that vector . factors() == gcd() */
  Tensor factors();
}
