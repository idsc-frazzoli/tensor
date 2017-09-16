// code by jph
package ch.ethz.idsc.tensor.usr;

import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.io.Pretty;
import ch.ethz.idsc.tensor.mat.CholeskyDecomposition;
import ch.ethz.idsc.tensor.qty.Quantity;

enum QuantityDemo {
  ;
  private static void _cholesky() {
    Tensor matrix = Tensors.fromString( //
        "{{60[m^2], 30[m*rad], 20[kg*m]}, {30[m*rad], 20[rad^2], 15[kg*rad]}, {20[kg*m], 15[kg*rad], 12[kg^2]}}", //
        Quantity::fromString);
    CholeskyDecomposition cd = CholeskyDecomposition.of(matrix);
    System.out.println(cd.diagonal());
    System.out.println(Pretty.of(cd.getL()));
    System.out.println(cd.det().divide(Quantity.of(20, "m^2*rad")));
  }

  public static void main(String[] args) {
    _cholesky();
  }
}
