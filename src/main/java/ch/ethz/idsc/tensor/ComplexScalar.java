// code by jph
package ch.ethz.idsc.tensor;

import java.util.Objects;

import ch.ethz.idsc.tensor.qty.Quantity;
import ch.ethz.idsc.tensor.sca.ArcTanInterface;
import ch.ethz.idsc.tensor.sca.ArgInterface;
import ch.ethz.idsc.tensor.sca.ComplexEmbedding;
import ch.ethz.idsc.tensor.sca.Cos;
import ch.ethz.idsc.tensor.sca.ExpInterface;
import ch.ethz.idsc.tensor.sca.LogInterface;
import ch.ethz.idsc.tensor.sca.PowerInterface;
import ch.ethz.idsc.tensor.sca.RoundingInterface;
import ch.ethz.idsc.tensor.sca.Sign;
import ch.ethz.idsc.tensor.sca.Sin;
import ch.ethz.idsc.tensor.sca.SqrtInterface;
import ch.ethz.idsc.tensor.sca.TrigonometryInterface;

/** complex number
 * 
 * <p>function {@link #number()} is not supported
 * 
 * <p>interface {@link Comparable} is not implemented */
public interface ComplexScalar extends Scalar, //
    ArcTanInterface, ArgInterface, ComplexEmbedding, ExpInterface, LogInterface, //
    PowerInterface, RoundingInterface, SqrtInterface, TrigonometryInterface {
  /** complex number I == 0+1*I */
  static final Scalar I = of(0, 1);

  /** @param re
   * @param im
   * @return scalar with re as real part and im as imaginary part
   * @throws Exception if re or im are {@link ComplexScalar} */
  static Scalar of(Scalar re, Scalar im) {
    if (re instanceof ComplexScalar || im instanceof ComplexScalar)
      throw TensorRuntimeException.of(re, im);
    if (re instanceof Quantity || im instanceof Quantity)
      throw TensorRuntimeException.of(re, im);
    return ComplexScalarImpl.of(Objects.requireNonNull(re), im);
  }

  /** @param re
   * @param im
   * @return scalar with re as real part and im as imaginary part */
  static Scalar of(Number re, Number im) {
    return ComplexScalarImpl.of(RealScalar.of(re), RealScalar.of(im));
  }

  /** @param abs radius, may be instance of {@link Quantity}
   * @param arg angle
   * @return complex scalar with polar coordinates abs and arg */
  static Scalar fromPolar(Scalar abs, Scalar arg) {
    return Sign.requirePositiveOrZero(abs).multiply(unit(arg));
  }

  /** @param abs radius
   * @param arg angle
   * @return complex scalar with polar coordinates abs and arg */
  static Scalar fromPolar(Number abs, Number arg) {
    return fromPolar(RealScalar.of(abs), RealScalar.of(arg));
  }

  /** @param arg
   * @return complex number on unit circle with given argument */
  static Scalar unit(Scalar arg) {
    if (arg instanceof ComplexScalar)
      throw TensorRuntimeException.of(arg);
    return ComplexScalarImpl.of(Cos.FUNCTION.apply(arg), Sin.FUNCTION.apply(arg));
  }
}
