// code by jph
package ch.ethz.idsc.tensor.opt;

import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;

/** This program executes the Hungarian Algorithm in order to solve a bipartite
 * matching problem. An entry [i][j] of the Scalar[n][m]-input-array represents
 * the cost of matching worker i to job j. An entry [i] of the int[n]-output-
 * array stores the best job j that was assigned to worker i. If there is no
 * job for a worker, i.e. j>i, the entry in the output-array will read -1.
 * 
 * The resulting matching will have minimum cost and therefore is an optimum.
 * All entries in the output array are unique.
 * 
 * https://en.wikipedia.org/wiki/Hungarian_algorithm */
public interface HungarianAlgorithm {
  static HungarianAlgorithm of(Tensor matrix) {
    return new HungarianAlgorithmImpl(matrix);
  }

  /** value of element in array returned by matching */
  static final int UNASSIGNED = -1;

  /** @return array of length equal to the rows of matrix */
  int[] matching();

  /** @return total cost of minimal solution */
  Scalar minimum();

  /** @return number of iterations after initial guess */
  int iterations();
}
