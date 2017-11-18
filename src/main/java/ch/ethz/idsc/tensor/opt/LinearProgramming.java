// code by jph
package ch.ethz.idsc.tensor.opt;

import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.TensorRuntimeException;
import ch.ethz.idsc.tensor.alg.Array;
import ch.ethz.idsc.tensor.alg.Join;
import ch.ethz.idsc.tensor.mat.IdentityMatrix;
import ch.ethz.idsc.tensor.sca.Sign;

/** linear programming solution for small scale problems.
 * The implementation has only been tested on a few cases.
 * 
 * <p>implementation uses traditional simplex algorithm that performs poorly on Klee-Minty cube
 * 
 * <p>syntax mostly compatible to MATLAB::linprog
 * 
 * <p>inspired by
 * <a href="https://reference.wolfram.com/language/ref/LinearProgramming.html">LinearProgramming</a> */
public enum LinearProgramming {
  ;
  /** @param c
   * @param m
   * @param b
   * @param simplexPivot used for decent
   * @return x >= 0 that minimizes c.x subject to m.x == b */
  public static Tensor minEquals(Tensor c, Tensor m, Tensor b, SimplexPivot simplexPivot) {
    Tensor x = SimplexMethod.of(c.unmodifiable(), m.unmodifiable(), b.unmodifiable(), simplexPivot);
    if (!isFeasible(m, x, b))
      throw TensorRuntimeException.of(c, m, x, b);
    return x;
  }

  /** @param c
   * @param m
   * @param b
   * @return x >= 0 that minimizes c.x subject to m.x == b */
  public static Tensor minEquals(Tensor c, Tensor m, Tensor b) {
    return minEquals(c, m, b, SimplexPivot.NONBASIC_GRADIENT);
  }

  /** @param c
   * @param m
   * @param b
   * @return x >= 0 that maximizes c.x subject to m.x == b */
  public static Tensor maxEquals(Tensor c, Tensor m, Tensor b) {
    return minEquals(c.negate(), m, b);
  }

  /** implementation transforms problem into slack form and invokes minEquals()
   * 
   * @param c
   * @param m
   * @param b
   * @return x >= 0 that minimizes c.x subject to m.x <= b */
  public static Tensor minLessEquals(Tensor c, Tensor m, Tensor b) {
    Tensor ceq = Join.of(c, Array.zeros(m.length()));
    // Tensor D = DiagonalMatrix.of(b.map(UnitStep.function));
    // IdentityMatrix.of(m.length())
    Tensor meq = Join.of(1, m, IdentityMatrix.of(m.length()));
    Tensor xeq = minEquals(ceq, meq, b);
    Tensor x = xeq.extract(0, c.length());
    if (!isFeasible(m, x, b))
      throw TensorRuntimeException.of(c, m, x, b);
    return x;
  }

  /** @param c
   * @param m
   * @param b
   * @return x >= 0 that maximizes c.x subject to m.x <= b */
  public static Tensor maxLessEquals(Tensor c, Tensor m, Tensor b) {
    return minLessEquals(c.negate(), m, b);
  }

  /** @param m
   * @param x
   * @param b
   * @return true if x >= 0 and m.x <= b */
  public static boolean isFeasible(Tensor m, Tensor x, Tensor b) {
    return isNonNegative(x) && isNonNegative(b.subtract(m.dot(x)));
  }

  /** @param vector
   * @return true if all entries in vector are non-negative */
  /* package */ static boolean isNonNegative(Tensor vector) {
    return vector.stream() // all vector_i >= 0
        .map(Scalar.class::cast).allMatch(Sign::isPositiveOrZero);
  }
}
