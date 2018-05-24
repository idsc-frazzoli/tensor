// code by jph
package ch.ethz.idsc.tensor.pdf;

import ch.ethz.idsc.tensor.DoubleScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.alg.Series;
import ch.ethz.idsc.tensor.sca.Clip;
import ch.ethz.idsc.tensor.sca.ScalarUnaryOperator;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/InverseErf.html">InverseErf</a> */
public enum InverseErf implements ScalarUnaryOperator {
  FUNCTION;
  // ---
  private static final Scalar ONE_NEGATE = RealScalar.ONE.negate();
  private static final ScalarUnaryOperator SERIES = Series.of(Tensors.vectorDouble( //
      0, 0.8842319013499945, //
      0, 0.5279697289942278, //
      0, -12.748788070175877, //
      0, 258.2768094143423, //
      0, -2890.6286909317223, //
      0, 20024.326421214653, //
      0, -91108.40107133288, //
      0, 281783.43543644866, //
      0, -602011.9089060738, //
      0, 888264.0571198871, //
      0, -888278.4601369379, //
      0, 574502.9894417486, //
      0, -216741.80070122686, //
      0, 36211.62603110378 //
  ));

  @Override
  public Scalar apply(Scalar scalar) {
    if (scalar.equals(ONE_NEGATE))
      return DoubleScalar.NEGATIVE_INFINITY;
    if (scalar.equals(RealScalar.ONE))
      return DoubleScalar.POSITIVE_INFINITY;
    return SERIES.apply(Clip.absoluteOne().requireInside(scalar));
  }

  /** @param tensor
   * @return tensor with all scalar entries replaced by the evaluation under InverseErf */
  @SuppressWarnings("unchecked")
  public static <T extends Tensor> T of(T tensor) {
    return (T) tensor.map(FUNCTION);
  }
}
