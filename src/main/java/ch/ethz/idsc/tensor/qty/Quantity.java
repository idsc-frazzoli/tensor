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
import ch.ethz.idsc.tensor.sca.TrigonometryInterface;

/** {@link Quantity} represents a magnitude and unit.
 * The class is intended for testing and demonstration.
 * <pre>
 * Mathematica::Quantity[8, "Kilograms"^2*"Meters"]
 * Tensor::Quantity.of(8, "[kg^2*m]")
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
    TrigonometryInterface, Comparable<Scalar> {
  /** @param string for example "9.81[m*s^-2]"
   * @return */
  static Scalar fromString(String string) {
    int index = string.indexOf(Unit.OPENING_BRACKET);
    if (0 <= index) {
      Scalar value = Scalars.fromString(string.substring(0, index));
      Unit unit = Unit.of(string.substring(index));
      return QuantityImpl.of(value, unit);
    }
    return Scalars.fromString(string);
  }

  /** @param value
   * @param string for instance "[m*s^-2]"
   * @return */
  static Scalar of(Scalar value, String string) {
    if (value instanceof Quantity)
      throw TensorRuntimeException.of(value);
    return QuantityImpl.of(value, Unit.of(string));
  }

  /** @param number
   * @param string for instance "[kg^3*m*s^-2]"
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
