// code by jph
package ch.ethz.idsc.tensor.opt;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.ZeroScalar;
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
      return ZeroScalar.get();
    return i == j ? RealScalar.ONE : Power.of(2, i - j + 1);
  }

  final Tensor c; // cost
  final Tensor m; // matrix
  final Tensor b; // vector

  public KleeMintyCube(int n) {
    c = Tensors.vector(i -> Power.of(2, n - i - 1), n);
    m = Tensors.matrix(KleeMintyCube::coefficient, n, n);
    b = Tensors.vector(i -> Power.of(5, i + 1), n);
  }

  public void show() {
    System.out.println("c=" + c);
    System.out.println(Pretty.of(m));
    System.out.println("b=" + b);
  }
}
