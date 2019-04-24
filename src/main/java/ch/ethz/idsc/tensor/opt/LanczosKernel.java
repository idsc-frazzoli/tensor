// code by jph
package ch.ethz.idsc.tensor.opt;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.sca.ScalarUnaryOperator;
import ch.ethz.idsc.tensor.sca.Sign;
import ch.ethz.idsc.tensor.sca.Sinc;

/** The Lanczos kernel is the function t -> sinc[t] Sinc[t/semi]
 * 
 * The implementation only evaluates the kernel in [-semi, semi] */
/* package */ class LanczosKernel implements ScalarUnaryOperator {
  static final LanczosKernel _3 = new LanczosKernel(3);
  // ---
  /* package */ final int semi;
  private final Scalar bound;

  /** @param semi positive */
  public LanczosKernel(int semi) {
    this.semi = semi;
    bound = Sign.requirePositive(RealScalar.of(semi));
  }

  @Override
  public Scalar apply(Scalar scalar) { // scalar is in the interval [-semi, semi]
    Scalar _scalar = scalar.multiply(Pi.VALUE);
    return Sinc.FUNCTION.apply(_scalar).multiply(Sinc.FUNCTION.apply(_scalar.divide(bound)));
  }
}
