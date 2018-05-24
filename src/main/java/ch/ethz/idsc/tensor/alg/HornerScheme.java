// code by jph
package ch.ethz.idsc.tensor.alg;

import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.sca.ScalarUnaryOperator;

/** https://en.wikipedia.org/wiki/Horner%27s_method */
/* package */ class HornerScheme implements ScalarUnaryOperator {
  private final Tensor reversed;

  /* package */ HornerScheme(Tensor coeffs) {
    reversed = Reverse.of(coeffs);
  }

  @Override
  public Scalar apply(Scalar scalar) {
    Scalar total = scalar.zero();
    for (Tensor entry : reversed)
      total = total.multiply(scalar).add(entry);
    return total;
  }
}
