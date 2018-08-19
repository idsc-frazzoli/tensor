// code by jph
package ch.ethz.idsc.tensor.qty;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.TensorRuntimeException;
import ch.ethz.idsc.tensor.io.CsvFormat;
import ch.ethz.idsc.tensor.io.ObjectFormat;
import ch.ethz.idsc.tensor.sca.ArcTanInterface;
import ch.ethz.idsc.tensor.sca.ArgInterface;
import ch.ethz.idsc.tensor.sca.ComplexEmbedding;
import ch.ethz.idsc.tensor.sca.PowerInterface;
import ch.ethz.idsc.tensor.sca.RoundingInterface;
import ch.ethz.idsc.tensor.sca.SignInterface;
import ch.ethz.idsc.tensor.sca.SqrtInterface;

/** {@link Quantity} represents a magnitude and unit.
 * <pre>
 * Mathematica::Quantity[8, "Kilograms"^2*"Meters"]
 * Tensor::Quantity.of(8, "kg^2*m")
 * </pre>
 * 
 * <p>The implementation is consistent with Mathematica:
 * The NumberQ relations for {@link Quantity} evaluate to
 * <pre>
 * NumberQ[Quantity[3, "Meters"]] == False
 * MachineNumberQ[Quantity[3.123, "Meters"]] == False
 * </pre>
 * 
 * <p>The sum of two quantities is well defined whenever the
 * units are identical. Two quantities with different units
 * are added if one of the values equals to zero. In that case
 * the result carries the unit of the non-zero input quantity.
 * 
 * <p>For export and import of tensors with scalars of type
 * {@link Quantity} use {@link ObjectFormat} and {@link CsvFormat}.
 * 
 * <p>Two quantities are comparable only if they have the same unit.
 * Otherwise an exception is thrown.
 * 
 * <p>Different units should mapped to a common unit system
 * before carrying out operations.
 * <pre>
 * Scalar a = Quantity.of(200, "g");
 * Scalar b = Quantity.of(1, "kg");
 * Total.of(Tensors.of(a, b).map(UnitSystem.SI())) == 6/5[kg]
 * </pre>
 * whereas <code>a.add(b)</code> throws an Exception.
 * 
 * <p>inspired by
 * <a href="https://reference.wolfram.com/language/ref/Quantity.html">Quantity</a> */
public interface Quantity extends Scalar, //
    ArcTanInterface, ArgInterface, ComplexEmbedding, PowerInterface, //
    RoundingInterface, SignInterface, SqrtInterface, Comparable<Scalar> {
  static final char UNIT_OPENING_BRACKET = '[';
  static final char UNIT_CLOSING_BRACKET = ']';

  /** Hint: function does not check parameters for null, although
   * null as input is likely to cause problems subsequently.
   * 
   * @param value
   * @param unit for instance Unit.of("m*s^-1")
   * @return
   * @throws Exception if value is instance of {@code Quantity} */
  static Scalar of(Scalar value, Unit unit) {
    if (value instanceof Quantity)
      throw TensorRuntimeException.of(value);
    return QuantityImpl.of(value, unit);
  }

  /** Hint: function does not check parameters for null, although
   * null as input is likely to cause problems subsequently.
   * 
   * @param value
   * @param string for instance "m*s^-2"
   * @return
   * @throws Exception if value is instance of {@code Quantity}
   * @throws Exception if string does not represent unit */
  static Scalar of(Scalar value, String string) {
    if (value instanceof Quantity)
      throw TensorRuntimeException.of(value);
    return QuantityImpl.of(value, Unit.of(string));
  }

  /** creates quantity with number encoded as {@link RealScalar}
   * 
   * Hint: function does not check parameters for null, although
   * null as input is likely to cause problems subsequently.
   * 
   * @param number non-null
   * @param unit
   * @return
   * @throws Exception if parameter number equals null */
  static Scalar of(Number number, Unit unit) {
    return QuantityImpl.of(RealScalar.of(number), unit);
  }

  /** creates quantity with number encoded as {@link RealScalar}
   * 
   * @param number
   * @param string for instance "kg^3*m*s^-2"
   * @return
   * @throws Exception if string does not represent unit
   * @throws Exception if either parameter equals null */
  static Scalar of(Number number, String string) {
    return QuantityImpl.of(RealScalar.of(number), Unit.of(string));
  }

  /** Quote from Mathematica::QuantityMagnitude
   * "gives the amount of the specified quantity"
   * "gives the magnitude value of a Quantity"
   * 
   * @return value of quantity without unit */
  Scalar value();

  /** @return unit of quantity without value */
  Unit unit();
}
