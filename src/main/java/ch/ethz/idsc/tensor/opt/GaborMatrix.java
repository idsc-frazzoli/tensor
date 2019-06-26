// code by jph
package ch.ethz.idsc.tensor.opt;

import java.util.Collections;
import java.util.List;

import ch.ethz.idsc.tensor.RationalScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.alg.Array;
import ch.ethz.idsc.tensor.red.Norm2Squared;
import ch.ethz.idsc.tensor.sca.AbsSquared;
import ch.ethz.idsc.tensor.sca.Cos;
import ch.ethz.idsc.tensor.sca.Exp;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/GaborMatrix.html">GaborMatrix</a> */
public enum GaborMatrix {
  ;
  /** only approximately consistent with Mathematica
   * 
   * @param r
   * @param k vector
   * @param phi
   * @return */
  public static Tensor of(int r, Tensor k, Scalar phi) {
    Scalar sigmas = AbsSquared.FUNCTION.apply(RationalScalar.of(r, 2));
    Scalar factor = sigmas.add(sigmas).negate();
    int m = 2 * r + 1;
    Scalar center = RealScalar.of(-r);
    Tensor offset = k.map(scalar -> center);
    List<Integer> dimensions = Collections.nCopies(k.length(), m);
    Tensor matrix = Array.of(list -> Norm2Squared.ofVector(Tensors.vector(list).add(offset)), dimensions) //
        .divide(factor).map(Exp.FUNCTION);
    Tensor weight = Array.of(list -> k.dot(Tensors.vector(list).add(offset)).subtract(phi), dimensions) //
        .map(Cos.FUNCTION);
    return weight.pmul(matrix);
  }
}
