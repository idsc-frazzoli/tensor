// code by jph
package ch.ethz.idsc.tensor.opt;

import java.util.Optional;

import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.alg.Normalize;
import ch.ethz.idsc.tensor.pdf.NormalDistribution;
import ch.ethz.idsc.tensor.pdf.RandomVariate;
import ch.ethz.idsc.tensor.red.Norm;
import ch.ethz.idsc.tensor.sca.Chop;

/** The algorithm is also known as the <em>Von Mises iteration</em>.
 * 
 * https://en.wikipedia.org/wiki/Power_iteration */
public enum PowerIteration {
  ;
  /** max iterations for each dimension */
  private static final int FACTOR = 15;
  private static final TensorUnaryOperator NORMALIZE = Normalize.with(Norm._2);
  private static final Chop CHOP = Chop._15;

  /** @param matrix square
   * @return Eigenvector to the largest eigenvalue (with high probability) */
  public static Optional<Tensor> of(Tensor matrix) {
    return of(matrix, RandomVariate.of(NormalDistribution.standard(), matrix.length()));
  }

  /** @param matrix square
   * @param vector seed
   * @return Eigenvector to the largest eigenvalue normalized to unit length */
  public static Optional<Tensor> of(Tensor matrix, Tensor vector) {
    int max = matrix.length() * FACTOR;
    for (int iteration = 0; iteration < max; ++iteration) {
      final Tensor prev = vector;
      vector = NORMALIZE.apply(matrix.dot(vector));
      if (CHOP.allZero(prev.subtract(vector)) || //
          CHOP.allZero(prev.add(vector)))
        return Optional.of(vector);
    }
    return Optional.empty();
  }
}
