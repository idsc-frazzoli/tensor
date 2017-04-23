// code by jph
package ch.ethz.idsc.tensor.sca;

import java.util.function.Function;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;

/** The logistic function 1 / (1 + Exp[-z]) is a solution to the differential equation y' == y * (1 - y)
 * 
 * <p>inspired by
 * <a href="https://reference.wolfram.com/language/ref/LogisticSigmoid.html">LogisticSigmoid</a> */
public enum LogisticSigmoid implements Function<Scalar, Scalar> {
  function;
  // ---
  @Override
  public Scalar apply(Scalar scalar) {
    return RealScalar.ONE.add(Exp.function.apply(scalar.negate())).invert();
  }

  /** @param tensor
   * @return tensor with all scalars replaced with their logistic sigmoid evaluation */
  public static Tensor of(Tensor tensor) {
    return tensor.map(LogisticSigmoid.function);
  }
}
