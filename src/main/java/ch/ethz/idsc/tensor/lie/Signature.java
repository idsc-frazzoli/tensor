// code by jph
package ch.ethz.idsc.tensor.lie;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.alg.Ordering;
import ch.ethz.idsc.tensor.alg.Range;

/** Examples:
 * <pre>
 * Signature[{0, 1, 2}] == +1
 * Signature[{1, 0, 2}] == -1
 * Signature[{0, 0, 2}] == 0
 * </pre>
 * 
 * <p>The implementation in the tensor library only operates on vectors (unlike Mathematica):
 * <pre>
 * Tensor::Signature[{{0, 0}, {0, 0}}] throws an Exception
 * </pre>
 * 
 * <p>inspired by
 * <a href="https://reference.wolfram.com/language/ref/Signature.html">Signature</a>
 * 
 * @see Ordering */
public enum Signature {
  ;
  private static final Scalar[] SIGN = new Scalar[] { //
      RealScalar.ONE, //
      RealScalar.ONE.negate() };

  /** @param vector of {@link Range} with integer entries, for instance {2, 0, 1}
   * @return either +1 or -1
   * @throws Exception if given permutation is not valid */
  public static Scalar of(Tensor vector) {
    long count = vector.stream().map(Scalar.class::cast).distinct().count();
    return vector.length() == count //
        ? of(Ordering.INCREASING.of(vector))
        : RealScalar.ZERO;
  }

  /** Careful:
   * function assumes that given ordering is a permutation of range [0, 1, ..., n - 1].
   * for other input an infinite loop might occur!
   * 
   * @param ordering
   * @return */
  /* package */ static Scalar of(Integer[] ordering) {
    int transpositions = 0;
    for (int index = 0; index < ordering.length; ++index)
      while (ordering[index] != index) {
        int value = ordering[index];
        ordering[index] = ordering[value];
        ordering[value] = value;
        ++transpositions;
      }
    return SIGN[transpositions % 2];
  }
}
