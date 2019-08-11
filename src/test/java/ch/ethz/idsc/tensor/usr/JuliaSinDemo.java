// code by jph
package ch.ethz.idsc.tensor.usr;

import java.io.IOException;

import ch.ethz.idsc.tensor.ComplexScalar;
import ch.ethz.idsc.tensor.DoubleScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Scalars;
import ch.ethz.idsc.tensor.img.ColorDataGradients;
import ch.ethz.idsc.tensor.sca.Arg;
import ch.ethz.idsc.tensor.sca.Clips;
import ch.ethz.idsc.tensor.sca.Imag;
import ch.ethz.idsc.tensor.sca.Sin;

/** inspired by document by Paul Bourke */
/* package */ class JuliaSinDemo extends BivariateEvaluation {
  private static final Scalar MAX = RealScalar.of(50);
  private static final int MAX_ITER = 10;
  // ---
  private final Scalar c;

  public JuliaSinDemo(Scalar c) {
    super( //
        Clips.interval(-2.3, +2.3), //
        Clips.interval(-2.3, +2.3));
    this.c = c;
  }

  @Override
  protected Scalar function(Scalar re, Scalar im) {
    Scalar z = ComplexScalar.of(re, im);
    for (int count = 0; count < MAX_ITER; ++count) {
      z = Sin.FUNCTION.apply(z).multiply(c);
      if (Scalars.lessThan(MAX, Imag.FUNCTION.apply(z).abs()))
        return DoubleScalar.INDETERMINATE;
    }
    return Arg.FUNCTION.apply(z);
  }

  public static void main(String[] args) throws IOException {
    StaticHelper.export( //
        new JuliaSinDemo(ComplexScalar.of(1.1, 0.5)), //
        Arg.class, ColorDataGradients.HUE);
  }
}
