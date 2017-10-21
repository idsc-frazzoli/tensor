// code by jph
package ch.ethz.idsc.tensor.sca;

import java.math.MathContext;
import java.math.RoundingMode;

import ch.ethz.idsc.tensor.DecimalScalar;
import ch.ethz.idsc.tensor.DoubleScalar;
import ch.ethz.idsc.tensor.RationalScalar;
import ch.ethz.idsc.tensor.Tensor;

/** provides the decimal representation of scalars that implement {@link NInterface}.
 * Supported types include {@link RationalScalar}, and {@link DecimalScalar}.
 * 
 * <p>In Mathematica, the N operator may be used with a parameter that specifies the precision.
 * If the parameter is omitted the result is in machine precision, i.e. 64-bit double.
 * <pre>
 * Sqrt[N[2]] == 1.4142135623730951`
 * Sqrt[N[2, 16]] == 1.41421356237309504880168872420969807857`16.30102999566398
 * </pre>
 * 
 * <p>The tensor library uses the following notation:
 * <pre>
 * Sqrt.of(N.DOUBLE.of(2)) == 1.4142135623730951
 * Sqrt.of(N.DECIMAL128.of(2)) == 1.414213562373095048801688724209698`34
 * </pre>
 * 
 * <p>inspired by
 * <a href="https://reference.wolfram.com/language/ref/N.html">N</a> */
public abstract class N implements ScalarUnaryOperator {
  /** conversion to {@link DoubleScalar} */
  public static final N DOUBLE = NDouble.INSTANCE;
  /** conversion to {@link DecimalScalar} with higher than machine precision */
  public static final N DECIMAL128 = new NDecimal(MathContext.DECIMAL128);
  /** conversion to {@link DecimalScalar} equivalent to machine precision */
  public static final N DECIMAL64 = new NDecimal(MathContext.DECIMAL64);
  /** conversion to {@link DecimalScalar} with precision equivalent to 32-bit float */
  public static final N DECIMAL32 = new NDecimal(MathContext.DECIMAL32);

  /** creates an instance of N that supplies {@link DecimalScalar}s with precision
   * specified by mathContext.
   * 
   * @param precision is approximately the number of correct digits in the decimal encoding
   * @return conversion to given precision in context with RoundingMode.HALF_EVEN */
  public static N in(int precision) {
    return new NDecimal(new MathContext(precision, RoundingMode.HALF_EVEN));
  }

  /** @param tensor
   * @return tensor with all scalars replaced with their decimal numerical */
  @SuppressWarnings("unchecked")
  public <T extends Tensor> T of(T tensor) {
    return (T) tensor.map(this);
  }
}
