// code by jph
package ch.ethz.idsc.tensor.red;

import java.io.Serializable;

import ch.ethz.idsc.tensor.RationalScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Scalars;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.TensorRuntimeException;
import ch.ethz.idsc.tensor.sca.Power;
import ch.ethz.idsc.tensor.sca.ScalarUnaryOperator;

/** p-Norm for vectors as well as schatten norm
 * 
 * implementation consistent with Mathematica
 * 
 * Important: For the special cases
 * <ul>
 * <li>p==1,
 * <li>p==2, or
 * <li>p==Infinity
 * </ul>
 * rather use {@link Norm} */
public class VectorNorm implements VectorNormInterface, Serializable {
  /** Hint: for enhanced precision, use p as instance of {@link RationalScalar} if possible
   * 
   * @param p exponent
   * @return */
  public static VectorNormInterface with(Scalar p) {
    return new VectorNorm(p);
  }

  /** @param p exponent
   * @return */
  public static VectorNormInterface with(Number p) {
    return with(RealScalar.of(p));
  }

  // ---
  private final ScalarUnaryOperator p_power;
  private final Scalar p_reciprocal;

  VectorNorm(Scalar p) {
    if (Scalars.lessThan(p, RealScalar.ONE))
      throw TensorRuntimeException.of(p);
    p_power = Power.function(p);
    p_reciprocal = p.reciprocal();
  }

  @Override // from VectorNormInterface
  public Scalar ofVector(Tensor vector) {
    return Power.of(vector.stream() //
        .map(Scalar.class::cast) //
        .map(Scalar::abs) //
        .map(p_power) //
        .reduce(Scalar::add).get(), //
        p_reciprocal);
  }
}
