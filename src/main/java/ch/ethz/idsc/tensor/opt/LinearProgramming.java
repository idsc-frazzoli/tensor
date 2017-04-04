// code by jph
package ch.ethz.idsc.tensor.opt;

import java.util.Arrays;
import java.util.stream.IntStream;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.TensorRuntimeException;
import ch.ethz.idsc.tensor.alg.Array;
import ch.ethz.idsc.tensor.alg.Join;
import ch.ethz.idsc.tensor.alg.MapThread;
import ch.ethz.idsc.tensor.mat.IdentityMatrix;

/** linear optimization
 * 
 * <p>see also MATLAB::linprog
 * 
 * <p>inspired by
 * <a href="https://reference.wolfram.com/language/ref/LinearProgramming.html">LinearProgramming</a> */
public enum LinearProgramming {
  ;
  // ---
  /** @param c
   * @param m
   * @param b
   * @return x >= 0 that minimizes c.x subject to m.x == b */
  public static Tensor minEquals(Tensor c, Tensor m, Tensor b) {
    return TableauImpl.of(c.unmodifiable(), m.unmodifiable(), b.unmodifiable());
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
    Tensor meq = MapThread.of(Join::of, Arrays.asList(m, IdentityMatrix.of(m.length())), 1);
    Tensor xeq = minEquals(ceq, meq, b);
    Tensor x = xeq.extract(0, c.length());
    if (!isFeasible(m, x, b))
      throw TensorRuntimeException.of(x);
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
   * @return true if m.x <= b */
  public static boolean isFeasible(Tensor m, Tensor x, Tensor b) {
    Tensor delta = m.dot(x).subtract(b);
    return !IntStream.range(0, delta.length()) //
        .filter(i -> 0 < ((RealScalar) delta.Get(i)).signInt()) //
        .findAny().isPresent();
  }
}
