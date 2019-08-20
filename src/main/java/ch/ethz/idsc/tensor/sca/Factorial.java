// code by jph
package ch.ethz.idsc.tensor.sca;

import ch.ethz.idsc.tensor.Integers;
import ch.ethz.idsc.tensor.RationalScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Scalars;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.alg.Last;

/** the tensor library defines factorial only for non-negative integers.
 * For input with decimal or complex numbers use {@link Gamma}.
 * 
 * Mathematica::FunctionExpand[x!] == Gamma[1 + x]
 * 
 * <p>inspired by
 * <a href="https://reference.wolfram.com/language/ref/Factorial.html">Factorial</a> */
public enum Factorial implements ScalarUnaryOperator {
  FUNCTION;
  // ---
  private static final Tensor MEMO = Tensors.vector(1); // initialize value for 0!

  /** @param scalar non-negative integer
   * @return factorial of given scalar
   * @throws Exception if scalar is not a non-negative integer */
  @Override
  public Scalar apply(Scalar scalar) {
    return of(Scalars.intValueExact(scalar));
  }

  /** @param index non-negative
   * @return factorial of index
   * @throws Exception if index is negative */
  public static Scalar of(int index) {
    if (MEMO.length() <= Integers.requirePositiveOrZero(index))
      synchronized (FUNCTION) {
        Scalar x = (Scalar) Last.of(MEMO);
        while (MEMO.length() <= index)
          MEMO.append(x = x.multiply(RationalScalar.of(MEMO.length(), 1)));
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
