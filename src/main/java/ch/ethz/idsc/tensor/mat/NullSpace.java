// code by jph
package ch.ethz.idsc.tensor.mat;

import java.util.stream.IntStream;

import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.ZeroScalar;
import ch.ethz.idsc.tensor.alg.Join;
import ch.ethz.idsc.tensor.alg.Transpose;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/NullSpace.html">NullSpace</a> */
public enum NullSpace {
  ;
  /** @param m square matrix
   * @return (cols - rank()) x cols matrix */
  public static Tensor of(Tensor m) {
    // TODO the algo right now only works for square matrix input...
    int n = m.length();
    Tensor lhs = RowReduce.of(Join.of(1, m, IdentityMatrix.of(n)));
    int c0 = 0;
    for (int j = 0; c0 < n && j < n; ++j)
      if (!lhs.Get(c0, j).equals(ZeroScalar.get()))
        ++c0;
    return Tensor.of(lhs.extract(c0, n).flatten(0).map(r -> r.extract(n, n + n)));
  }

  /** @param m
   * @return (cols - rank()) x cols matrix */
  public static Tensor usingSvd(Tensor m) {
    return of(SingularValueDecomposition.of(m));
  }

  public static Tensor of(SingularValueDecomposition svd) {
    double w_threshold = svd.getThreshold();
    Tensor vt = Transpose.of(svd.getV());
    return Tensor.of(IntStream.range(0, svd.getW().length()).boxed() //
        .filter(index -> svd.getW().Get(index).abs().number().doubleValue() < w_threshold) //
        .map(vt::get));
  }
}
