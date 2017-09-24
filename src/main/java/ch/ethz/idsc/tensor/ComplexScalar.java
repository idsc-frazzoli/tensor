// code by jph
package ch.ethz.idsc.tensor;

import ch.ethz.idsc.tensor.qty.Quantity;
import ch.ethz.idsc.tensor.sca.ArcTanInterface;
import ch.ethz.idsc.tensor.sca.ArgInterface;
import ch.ethz.idsc.tensor.sca.ComplexEmbedding;
import ch.ethz.idsc.tensor.sca.Cos;
import ch.ethz.idsc.tensor.sca.ExpInterface;
import ch.ethz.idsc.tensor.sca.LogInterface;
import ch.ethz.idsc.tensor.sca.PowerInterface;
import ch.ethz.idsc.tensor.sca.RoundingInterface;
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
  /** suffix that is appended to imaginary part of {@link ComplexScalar} in function toString() */
  static final String I_SYMBOL = "I";

  /** @param re
   * @param im
   * @return scalar with re as real part and im as imaginary part
   * @throws Exception if re or im are {@link ComplexScalar} */
  static Scalar of(Scalar re, Scalar im) {
    if (re instanceof ComplexScalar || im instanceof ComplexScalar)
      throw TensorRuntimeException.of(re, im);
    if (re instanceof Quantity || im instanceof Quantity)
      throw TensorRuntimeException.of(re, im);
    return ComplexScalarImpl.of(re, im);
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
    if (abs instanceof ComplexScalar || arg instanceof ComplexScalar)
      throw TensorRuntimeException.of(abs, arg);
    return abs.multiply(of(Cos.FUNCTION.apply(arg), Sin.FUNCTION.apply(arg)));
  }
}
