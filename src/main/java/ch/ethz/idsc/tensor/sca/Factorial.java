// code by jph
package ch.ethz.idsc.tensor.sca;

import ch.ethz.idsc.tensor.RationalScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Scalars;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.TensorRuntimeException;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.alg.Last;

/** the tensor library defines factorial only for non-negative integers
 * 
 * Mathematica::Factorial also works on decimals, for instance
 * 3.21! == 7.85918
 * 
 * Mathematica::FunctionExpand[x!] == Gamma[1 + x]
 * 
 * <p>inspired by
 * <a href="https://reference.wolfram.com/language/ref/Factorial.html">Factorial</a> */
public enum Factorial implements ScalarUnaryOperator {
  FUNCTION;
  // ---
  private static Tensor MEMO = Tensors.vector(1); // initialize value for 0!

  /** @param scalar non-negative integer
   * @throws Exception if scalar is not a non-negative integer */
  @Override
  public Scalar apply(Scalar scalar) {
    int index = Scalars.intValueExact(scalar);
    if (index < 0)
      throw TensorRuntimeException.of(scalar);
    if (MEMO.length() <= index)
      synchronized (FUNCTION) {
        while (MEMO.length() <= index)
          MEMO.append(Last.of(MEMO).multiply(RationalScalar.of(MEMO.length(), 1)));
      }
    return MEMO.Get(index);
  }

  /** @param tensor
   * @return tensor with all scalars replaced with their factorial */
  @SuppressWarnings("unchecked")
  public static <T extends Tensor> T of(T tensor) {
    return (T) tensor.map(FUNCTION);
  }
}
