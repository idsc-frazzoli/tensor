// code by jph
package ch.ethz.idsc.tensor.lie;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Scalars;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.TensorRuntimeException;
import ch.ethz.idsc.tensor.alg.Range;

/** Hint: the tensor library implementation of Signature is not as general as
 * Mathematica::Signature. For instance, Mathematica::Signature may also return 0.
 * 
 * <pre>
 * Tensor::Signature[{0, 1, 2}] == +1
 * Tensor::Signature[{1, 0, 2}] == -1
 * 
 * Tensor::Signature[{0, 0, 2}] throws an Exception
 * </pre>
 * 
 * <p>inspired by
 * <a href="https://reference.wolfram.com/language/ref/Signature.html">Signature</a> */
public enum Signature {
  ;
  private static final Scalar[] SIGN = new Scalar[] { //
      RealScalar.ONE, //
      RealScalar.ONE.negate() };

  /** @param permutation of {@link Range} with integer entries, for instance {2, 0, 1}
   * @return either +1 or -1
   * @throws Exception if given permutation is not valid */
  public static Scalar of(Tensor permutation) {
    int[] array = permutation.stream() //
        .map(Scalar.class::cast) //
        .mapToInt(Scalars::intValueExact) //
        .distinct() //
        .toArray();
    if (array.length != permutation.length())
      throw TensorRuntimeException.of(permutation);
    int transpositions = 0;
    for (int index = 0; index < array.length; ++index)
      while (array[index] != index) {
        int value = array[index];
        array[index] = array[value];
        array[value] = value;
        ++transpositions;
      }
    return SIGN[transpositions % 2];
  }
}
