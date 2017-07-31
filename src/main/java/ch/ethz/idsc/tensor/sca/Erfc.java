// code by jph
package ch.ethz.idsc.tensor.sca;

import ch.ethz.idsc.tensor.DoubleScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Scalars;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.TensorRuntimeException;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.alg.Multinomial;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/Erfc.html">Erfc</a> */
public enum Erfc implements ScalarUnaryOperator {
  FUNCTION;
  // ---
  private static final Tensor coeffs = Tensors.vector( //
      1, //
      -1.1283791670955126, 0, // x
      +0.3761263890318375, 0, // x^3
      -0.11283791670955126, 0, // x^5
      +0.02686617064513125, 0, // x^7
      -0.005223977625442187, 0, // x^9
      +0.0008548327023450852, 0, // x^11
      -0.00012055332981789664, 0, // x^13
      +0.000014925650358406252 // x^15
  );

  @Override
  public Scalar apply(Scalar scalar) {
    if (Scalars.lessThan(scalar.abs(), DoubleScalar.of(.7))) // error < 10^-9
      return Multinomial.horner(coeffs, scalar);
    // TODO implement
    throw TensorRuntimeException.of(scalar);
  }
}
