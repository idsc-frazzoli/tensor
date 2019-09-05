// code by jph
package ch.ethz.idsc.tensor.alg;

import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;

/* package */ enum RootsDemo {
  ;
  public static void main(String[] args) {
    // Tensor coeffs = Tensors.vector(1.5583019232667707, 0.08338030361650195, 0.5438230916311243, 1.1822223716596811);
    // Tensor roots = RootsCubic.of(coeffs);
    // System.out.println(roots);
    Tensor coeffs = CoefficientList.of(Tensors.vector(2, 3, 4));
    System.out.println(coeffs);
    Tensor roots = Roots.of(coeffs);
    System.out.println(roots);
  }
}
