// code by jph
package ch.ethz.idsc.tensor.opt;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.io.Pretty;
import ch.ethz.idsc.tensor.sca.Power;

/** Quote from Wikipedia:
 * 
 * <p>The Klee-Minty cube or Klee-Minty polytope (named after Victor Klee and George J. Minty)
 * is a unit hypercube of variable dimension whose corners have been perturbed.
 * Klee and Minty demonstrated that George Dantzig's simplex algorithm has poor worst-case
 * performance when initialized at one corner of their "squashed cube".
 * 
 * <p>In particular, many optimization algorithms for linear optimization
 * exhibit poor performance when applied to the Klee-Minty cube.
 * In 1973 Klee and Minty showed that Dantzig's simplex algorithm
 * was not a polynomial-time algorithm when applied to their cube.
 * Later, modifications of the Klee-Minty cube have shown poor behavior
 * both for other basis-exchange pivoting algorithms and also
 * for interior-point algorithms.
 * 
 * <p>https://en.wikipedia.org/wiki/Klee%E2%80%93Minty_cube */
/* package */ class KleeMintyCube {
  private static Scalar coefficient(int i, int j) {
    if (i < j)
      return RealScalar.ZERO;
    return i == j ? RealScalar.ONE : Power.of(2, i - j + 1);
  }

  public final Tensor c; // cost
  public final Tensor m; // matrix
  public final Tensor b; // vector of rhs
  public final Tensor x; // solution

  public KleeMintyCube(int n) {
    c = Tensors.vector(i -> Power.of(2, n - i - 1), n).unmodifiable();
    m = Tensors.matrix(KleeMintyCube::coefficient, n, n).unmodifiable();
    b = Tensors.vector(i -> Power.of(5, i + 1), n).unmodifiable();
    x = Tensors.vector(i -> i < n - 1 ? RealScalar.ZERO : Power.of(5, n), n).unmodifiable();
  }

  public void show() {
    System.out.println("c=" + c);
    System.out.println("m=" + Pretty.of(m));
    System.out.println("b=" + b);
    System.out.println("x=" + x);
  }
}
