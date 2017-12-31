// code by jph
package ch.ethz.idsc.tensor.opt;

import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.alg.Subdivide;
import ch.ethz.idsc.tensor.io.Put;
import ch.ethz.idsc.tensor.utl.UserHome;

enum DeBoorDemo {
  ;
  static void cubic() throws Exception {
    Tensor d = Tensors.vector(0, 0, 1, 3).unmodifiable();
    Tensor t = Tensors.vector(0, 0, 0, 1, 2, 3).unmodifiable();
    Tensor r = Tensors.empty();
    for (Tensor _x : Subdivide.of(0, 1, 10)) {
      Scalar x = _x.Get();
      Scalar y = (Scalar) DeBoor.of(3, d, t, x);
      r.append(Tensors.of(x, y));
    }
    Put.of(UserHome.file("deboor.put"), r);
  }

  public static void main(String[] args) throws Exception {
    cubic();
  }
}
