// code by jph
package ch.ethz.idsc.tensor.opt;

import ch.ethz.idsc.tensor.DoubleScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.sca.Clip;
import ch.ethz.idsc.tensor.sca.ScalarUnaryOperator;
import ch.ethz.idsc.tensor.sca.Sinc;

/* package */ class LanczosKernel implements ScalarUnaryOperator {
  private static final Scalar PI = DoubleScalar.of(Math.PI);
  // ---
  private final Scalar bound;
  private final Clip clip;

  public LanczosKernel(int size) {
    bound = RealScalar.of(size);
    clip = Clip.function(bound.negate(), bound);
  }

  @Override
  public Scalar apply(Scalar scalar) {
    if (clip.isInside(scalar)) {
      Scalar _scalar = scalar.multiply(PI);
      return Sinc.FUNCTION.apply(_scalar).multiply(Sinc.FUNCTION.apply(_scalar.divide(bound)));
    }
    // LONGTERM <- ideally implementations should not reach this point
    // System.out.println("don't evaluate here");
    return RealScalar.ZERO;
  }
}
