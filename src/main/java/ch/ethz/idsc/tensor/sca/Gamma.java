// code by jph
package ch.ethz.idsc.tensor.sca;

import ch.ethz.idsc.tensor.ComplexScalar;
import ch.ethz.idsc.tensor.DoubleScalar;
import ch.ethz.idsc.tensor.NumberQ;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Scalars;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.TensorRuntimeException;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.alg.Series;

/** Euler gamma function
 * <pre>
 * Gamma[x + 1] == x Gamma[x]
 * </pre>
 * 
 * <p>implementation also works for {@link ComplexScalar}s
 *
 * <p>the function is not defined at non-positive integers
 * Gamma[0], Gamma[-1], Gamma[-2] == ComplexInfinity
 * For input of this type, an Exception is thrown.
 * 
 * <p>the implementation throws an exception for input with
 * real part of large magnitude.
 * 
 * <p>typically the implementation gives precision within ~1E^-8
 * of the magnitude of the input.
 * 
 * <p>inspired by
 * <a href="https://reference.wolfram.com/language/ref/Gamma.html">Gamma</a> */
public enum Gamma implements ScalarUnaryOperator {
  FUNCTION;
  // ---
  /** EulerGamma is the negative of the derivative D[Gamma[x]] at x == 1 */
  public static final Scalar EULER = DoubleScalar.of(0.577215664901532860606512090082);
  static final Scalar NEGATIVE_THREE = RealScalar.of(-3);
  /** series around x == 3 */
  static final ScalarUnaryOperator SERIES = Series.of(Tensors.vector(2, //
      1.84556867019693427878697581983519513792, //
      1.24646499595134652897125503275406212275, //
      0.57499416892061222754655452970695120715, //
      0.23007494075411406301847573747551082789, //
      0.07371504661602386878317788712652790098, //
      0.02204110936751696733001055930504372387, //
      0.00544875407582030941674350686546740396, //
      0.00135522086023943520078280051169290847, //
      0.00026478566304549637629167030275881622, //
      0.00006120306281920072864297933044333221, //
      8.50557917488135478967485300400316E-6, //
      2.40617724013144186681535254891994E-6, //
      8.80239099064809680158914901585E-8, //
      1.14222764534215837758667044838E-7, //
      -1.63147521008274372795419896867E-8, // <- negative coefficient
      8.62349738997827269892882654395E-9 //
  ));
  static final Scalar LO = DoubleScalar.of(2.5);
  static final Scalar HI = DoubleScalar.of(3.5);
  private static final Mod MOD = Mod.function(RealScalar.ONE, LO);
  private static final Scalar HALF_P = DoubleScalar.of(+0.5);
  private static final Scalar HALF_N = DoubleScalar.of(-0.5);

  @Override
  public Scalar apply(Scalar scalar) {
    Scalar round = Round.FUNCTION.apply(Real.of(scalar));
    if (scalar.equals(round)) { // ..., -2, -1, 0, 1, 2, ...
      scalar = round;
      if (Scalars.lessEquals(scalar, RealScalar.ZERO)) // ..., -2, -1, 0
        throw TensorRuntimeException.of(scalar);
    }
    return evaluate(scalar);
  }

  private static Scalar evaluate(Scalar scalar) {
    Scalar real = Real.FUNCTION.apply(scalar);
    Scalar hxi = Imag.FUNCTION.apply(scalar);
    Scalar hxr = MOD.apply(real);
    Scalar hx = ComplexScalar.of(hxr, hxi);
    Scalar value = SERIES.apply(hx.add(NEGATIVE_THREE));
    while (Scalars.lessThan(Real.of(real.subtract(hx)), HALF_N) && NumberQ.of(value) && Scalars.nonZero(value)) {
      hx = hx.subtract(RealScalar.ONE);
      value = value.divide(hx);
    }
    while (Scalars.lessThan(HALF_P, Real.of(real.subtract(hx))) && NumberQ.of(value)) {
      value = value.multiply(hx);
      hx = hx.add(RealScalar.ONE);
    }
    return value;
  }

  /** @param tensor
   * @return tensor with all scalars replaced with their gamma evaluation */
  @SuppressWarnings("unchecked")
  public static <T extends Tensor> T of(T tensor) {
    return (T) tensor.map(FUNCTION);
  }
}
