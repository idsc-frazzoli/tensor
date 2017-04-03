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

/** linear optimization */
public enum LinearProgramming {
  ;
  // ---
  /** @param A
   * @param b
   * @param c
   * @return x >= 0 that minimizes c.x subject to A.x == b */
  public static Tensor ofEquals(Tensor A, Tensor b, Tensor c) {
    return SimplexImpl.of(A.unmodifiable(), b.unmodifiable(), c.unmodifiable());
  }

  /** @param A
   * @param b
   * @param c
   * @return x >= 0 that minimizes c.x subject to A.x <= b */
  public static Tensor ofLessEquals(Tensor A, Tensor b, Tensor c) {
    Tensor Aeq = MapThread.of(Join::of, Arrays.asList(A, IdentityMatrix.of(A.length())), 1);
    Tensor ceq = Join.of(c, Array.zeros(A.length()));
    Tensor xeq = ofEquals(Aeq, b, ceq);
    Tensor x = xeq.extract(0, c.length());
    assertFeasible(A.dot(x).subtract(b));
    return x;
  }

  // helper function
  private static void assertFeasible(Tensor diff) {
    if (IntStream.range(0, diff.length()) //
        .filter(i -> ((RealScalar) diff.Get(i)).signInt() > 0) //
        .findAny().isPresent())
      throw TensorRuntimeException.of(diff);
  }
}
