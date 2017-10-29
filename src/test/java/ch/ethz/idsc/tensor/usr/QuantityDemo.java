// code by jph
package ch.ethz.idsc.tensor.usr;

import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.io.Pretty;
import ch.ethz.idsc.tensor.mat.CholeskyDecomposition;
import ch.ethz.idsc.tensor.qty.Quantity;
import ch.ethz.idsc.tensor.qty.Unit;
import ch.ethz.idsc.tensor.qty.UnitConvert;

enum QuantityDemo {
  ;
  private static void _cholesky() {
    Tensor matrix = Tensors.fromString( //
        "{{60[m^2], 30[m*rad], 20[kg*m]}, {30[m*rad], 20[rad^2], 15[kg*rad]}, {20[kg*m], 15[kg*rad], 12[kg^2]}}");
    CholeskyDecomposition cd = CholeskyDecomposition.of(matrix);
    System.out.println(cd.diagonal());
    System.out.println(Pretty.of(cd.getL()));
    System.out.println(cd.det().divide(Quantity.of(20, "m^2*rad")));
  }

  private static void _converter() {
    Scalar mass = Quantity.of(300, "g"); // in gram
    Scalar a = Quantity.of(981, "cm*s^-2"); // in centi-meters per seconds square
    Scalar force = mass.multiply(a);
    System.out.println(force);
    Scalar force_N = UnitConvert.SI().to(Unit.of("N")).apply(force);
    System.out.println(force_N);
  }

  public static void main(String[] args) {
    _cholesky();
    _converter();
  }
}
