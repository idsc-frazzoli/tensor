// code by jph
package ch.ethz.idsc.tensor.usr;

import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Scalars;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.alg.Series;
import ch.ethz.idsc.tensor.sca.ScalarUnaryOperator;

/** https://en.wikipedia.org/wiki/Newton%27s_method */
/* package */ class NewtonScalarMethod implements ScalarUnaryOperator {
  public static ScalarUnaryOperator polynomial(Tensor coeffs) {
    return new NewtonScalarMethod(Series.of(coeffs), Series.of(Multinomial.derivative(coeffs)));
  }

  // ---
  private final ScalarUnaryOperator function;
  private final ScalarUnaryOperator iteration;

  public NewtonScalarMethod(ScalarUnaryOperator function, ScalarUnaryOperator derivative) {
    this.function = function;
    iteration = z -> z.subtract(function.apply(z).divide(derivative.apply(z)));
  }

  @Override // from ScalarUnaryOperator
  public Scalar apply(Scalar prev_root) {
    Scalar prev_error = function.apply(prev_root).abs();
    while (true) {
      Scalar next_root = iteration.apply(prev_root);
      Scalar next_error = function.apply(next_root).abs();
      if (Scalars.lessEquals(prev_error, next_error))
        return prev_root;
      prev_error = next_error;
      prev_root = next_root;
    }
  }
}
