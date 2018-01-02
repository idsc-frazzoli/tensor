// code by jph
package ch.ethz.idsc.tensor.opt;

import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.alg.Subdivide;
import ch.ethz.idsc.tensor.io.Put;
import ch.ethz.idsc.tensor.utl.UserHome;

enum BSplineFunctionDemo {
  ;
  public static void main(String[] args) throws Exception {
    for (int p = 0; p <= 3; ++p) {
      BSplineFunction bsf = BSplineFunction.of(p, Tensors.vector(5, 2, 4, 3, 4, 2));
      Tensor r = Tensors.empty();
      for (Tensor _x : Subdivide.of(0, 5, 5 * 10)) {
        Scalar x = _x.Get();
        Scalar y = (Scalar) bsf.apply(x);
        r.append(Tensors.of(x, y));
      }
      Put.of(UserHome.file("deboor" + p + ".put"), r);
    }
  }
}
