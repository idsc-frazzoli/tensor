// code by jph
package ch.ethz.idsc.tensor.usr;

import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.alg.Accumulate;
import ch.ethz.idsc.tensor.io.Timing;
import ch.ethz.idsc.tensor.pdf.Distribution;
import ch.ethz.idsc.tensor.pdf.RandomVariate;
import ch.ethz.idsc.tensor.pdf.UniformDistribution;

/* package */ enum FoldListDemo {
  ;
  public static void main(String[] args) {
    Distribution distribution = UniformDistribution.unit();
    for (int count = 0; count < 20; ++count) {
      Tensor tensor = RandomVariate.of(distribution, 10000000);
      {
        Timing timing = Timing.started();
        FoldListTry.of(Tensor::add, tensor);
        System.out.println("new " + timing.nanoSeconds());
      }
      {
        Timing timing = Timing.started();
        Accumulate.of(tensor);
        System.out.println("old " + timing.nanoSeconds());
      }
    }
  }
}
