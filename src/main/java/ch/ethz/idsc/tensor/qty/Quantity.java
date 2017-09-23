// code by jph
package ch.ethz.idsc.tensor.qty;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Scalars;
import ch.ethz.idsc.tensor.TensorRuntimeException;
import ch.ethz.idsc.tensor.io.CsvFormat;
import ch.ethz.idsc.tensor.io.ObjectFormat;
import ch.ethz.idsc.tensor.sca.ArcTanInterface;
import ch.ethz.idsc.tensor.sca.ChopInterface;
import ch.ethz.idsc.tensor.sca.ComplexEmbedding;
import ch.ethz.idsc.tensor.sca.NInterface;
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
 * The implementation is consistent with Mathematica:
 * The NumberQ relations for {@link Quantity} evaluate to
 * <pre>
 * NumberQ[Quantity[3, "Meters"]] == False
 * ExactNumberQ[Quantity[3, "Meters"]] == False
 * MachineNumberQ[Quantity[3.123, "Meters"]] == False
 * </pre>
 * 
 * The convention of equality: "0[unit] == 0 evaluates to true"
 * is used in
 * {@link #plus(Scalar)}
 * {@link #compareTo(Scalar)}
 * {@link #equals(Object)}
 * 
 * In particular, the rule allows to up-cast any
 * {@link Scalar#zero()} to a zero with any unit,
 * for instance 0 == 0[m^2] == 0[rad*s] == 0
 * 
 * <p>For export and import of tensors with scalars of type
 * {@link Quantity} use {@link ObjectFormat} and {@link CsvFormat}.
 * 
 * <p>inspired by
 * <a href="https://reference.wolfram.com/language/ref/Quantity.html">Quantity</a> */
public interface Quantity extends Scalar, //
    ArcTanInterface, ChopInterface, ComplexEmbedding, NInterface, //
    PowerInterface, RoundingInterface, SignInterface, SqrtInterface, //
    Comparable<Scalar> {
  static final char UNIT_OPENING_BRACKET = '[';
  static final char UNIT_CLOSING_BRACKET = ']';

  /** @param string for example "9.81[m*s^-2]"
   * @return */
  static Scalar fromString(String string) {
    final int index = string.indexOf(UNIT_OPENING_BRACKET);
    if (0 < index) {
      final int last = string.indexOf(UNIT_CLOSING_BRACKET);
      if (index < last && string.substring(last + 1).trim().isEmpty())
        return QuantityImpl.of( //
            Scalars.fromString(string.substring(0, index)), //
            Unit.of(string.substring(index + 1, last)));
      throw new RuntimeException(string);
    }
    return Scalars.fromString(string);
  }

  /** @param value
   * @param string for instance "m*s^-2"
   * @return */
  static Scalar of(Scalar value, String string) {
    return of(value, Unit.of(string));
  }

  /** @param value
   * @param unit for instance Unit.of("m*s^-1")
   * @return */
  static Scalar of(Scalar value, Unit unit) {
    if (value instanceof Quantity)
      throw TensorRuntimeException.of(value);
    return QuantityImpl.of(value, unit);
  }

  /** creates quantity with number encoded as {@link RealScalar}
   * 
   * @param number
   * @param string for instance "kg^3*m*s^-2"
   * @return */
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
