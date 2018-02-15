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
  static final LanczosKernel _3 = new LanczosKernel(3);
  // ---
  final int semi;
  private final Scalar bound;
  private final Clip clip;

  public LanczosKernel(int semi) {
    this.semi = semi;
    bound = RealScalar.of(semi);
    clip = Clip.function(bound.negate(), bound);
  }

  @Override
  public Scalar apply(Scalar scalar) {
    return clip.isInside(scalar) ? inside(scalar) : RealScalar.ZERO;
  }

  /** @param scalar inside clip
   * @return */
  /* package */ Scalar inside(Scalar scalar) {
    Scalar _scalar = scalar.multiply(PI);
    return Sinc.FUNCTION.apply(_scalar).multiply(Sinc.FUNCTION.apply(_scalar.divide(bound)));
  }
}
