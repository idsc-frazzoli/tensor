// code by jph
package ch.ethz.idsc.tensor.sca;

import java.math.BigInteger;
import java.util.function.Function;

import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.TensorRuntimeException;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/Sqrt.html">Sqrt</a> */
public enum Sqrt implements Function<Scalar, Scalar> {
  function;
  // ---
  @Override
  public Scalar apply(Scalar scalar) {
    if (scalar instanceof SqrtInterface)
      return ((SqrtInterface) scalar).sqrt();
    throw TensorRuntimeException.of(scalar);
  }

  /** @param tensor
   * @return tensor with all scalars replaced with their square root */
  public static Tensor of(Tensor tensor) {
    return tensor.map(Sqrt.function);
  }

  /** https://gist.github.com/JochemKuijpers/cd1ad9ec23d6d90959c549de5892d6cb
   * 
   * @param value
   * @return approximation to sqrt of value, exact root if input value is square number */
  public static BigInteger approximation(BigInteger value) {
    BigInteger a = BigInteger.ONE;
    BigInteger b = value.shiftRight(5).add(BigInteger.valueOf(8));
    while (0 <= b.compareTo(a)) {
      BigInteger mid = a.add(b).shiftRight(1);
      if (0 < mid.multiply(mid).compareTo(value))
        b = mid.subtract(BigInteger.ONE);
      else
        a = mid.add(BigInteger.ONE);
    }
    return a.subtract(BigInteger.ONE);
  }

  /** @param value
   * @return exact root of value
   * @throws Exception if value is not a square number */
  public static BigInteger exact(BigInteger value) {
    BigInteger root = approximation(value);
    if (root.multiply(root).equals(value))
      return root;
    throw new IllegalArgumentException(value.toString()); // value is not square
  }
}
