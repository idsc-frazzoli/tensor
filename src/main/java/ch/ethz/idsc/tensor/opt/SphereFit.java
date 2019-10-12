// code by jph
package ch.ethz.idsc.tensor.opt;

import java.io.Serializable;
import java.util.Optional;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Unprotect;
import ch.ethz.idsc.tensor.mat.LinearSolve;
import ch.ethz.idsc.tensor.mat.MatrixRank;
import ch.ethz.idsc.tensor.mat.PseudoInverse;
import ch.ethz.idsc.tensor.mat.SingularValueDecomposition;
import ch.ethz.idsc.tensor.red.Norm2Squared;
import ch.ethz.idsc.tensor.sca.Sqrt;

/** reference: "Circle fitting by linear and non-linear least squares", by J. D. Coope */
public class SphereFit implements Serializable {
  private static final Scalar TWO = RealScalar.of(2);

  /** @param points encoded as matrix
   * @return optional with instance of SphereFit containing the center and radius
   * of the fitted sphere, or empty if points are numerically co-linear
   * @throws Exception if points is empty, or not a matrix */
  public static Optional<SphereFit> of(Tensor points) {
    Tensor A = Tensor.of(points.stream() //
        .map(point -> point.multiply(TWO).append(RealScalar.ONE)));
    Tensor b = Tensor.of(points.stream().map(Norm2Squared::ofVector));
    int rows = A.length();
    int cols = Unprotect.dimension1(A);
    if (rows < cols)
      return Optional.empty();
    SingularValueDecomposition svd = SingularValueDecomposition.of(A);
    if (MatrixRank.of(svd) < cols)
      return Optional.empty();
    Tensor x = rows == cols //
        ? LinearSolve.of(A, b)
        : PseudoInverse.of(svd).dot(b);
    Tensor center = x.extract(0, cols - 1);
    return Optional.of(new SphereFit( //
        center, //
        Sqrt.FUNCTION.apply(x.Get(cols - 1).add(Norm2Squared.ofVector(center)))));
  }

  // ---
  private final Tensor center;
  private final Scalar radius;

  private SphereFit(Tensor center, Scalar radius) {
    this.center = center;
    this.radius = radius;
  }

  /** @return center of fitted sphere */
  public Tensor center() {
    return center;
  }

  /** @return radius of fitted sphere */
  public Scalar radius() {
    return radius;
  }

  @Override // from Object
  public String toString() {
    return String.format("%s[center=%s, radius=%s]", getClass().getSimpleName(), center(), radius());
  }
}
