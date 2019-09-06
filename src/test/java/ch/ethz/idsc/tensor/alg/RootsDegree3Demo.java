// code by jph
package ch.ethz.idsc.tensor.alg;

import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.io.Pretty;
import ch.ethz.idsc.tensor.pdf.Distribution;
import ch.ethz.idsc.tensor.pdf.NormalDistribution;
import ch.ethz.idsc.tensor.pdf.RandomVariate;
import ch.ethz.idsc.tensor.red.Norm;
import ch.ethz.idsc.tensor.sca.ScalarUnaryOperator;

/* package */ enum RootsDegree3Demo {
  ;
  public static void main(String[] args) {
    Distribution distribution = NormalDistribution.standard();
    Tensor errors = Tensors.empty();
    for (int count = 0; count < 100; ++count) {
      Tensor coeffs = RandomVariate.of(distribution, 4);
      Tensor r0 = RootsDegree3Full.of(coeffs);
      Tensor r1 = RootsDegree3.of(coeffs);
      ScalarUnaryOperator scalarUnaryOperator = Series.of(coeffs);
      errors.append(Tensors.of( //
          Norm.INFINITY.ofVector(r0.map(scalarUnaryOperator)), //
          Norm.INFINITY.ofVector(r1.map(scalarUnaryOperator))));
    }
    Tensor total = errors.stream().reduce(Tensor::add).get();
    System.out.println(Pretty.of(errors));
    System.out.println(total);
  }
}
