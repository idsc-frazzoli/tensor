// code by jph
package ch.ethz.idsc.tensor.alg;

import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.pdf.Distribution;
import ch.ethz.idsc.tensor.pdf.RandomVariate;
import ch.ethz.idsc.tensor.pdf.UniformDistribution;
import ch.ethz.idsc.tensor.utl.Stopwatch;

enum FoldListDemo {
  ;
  public static void main(String[] args) {
    Distribution distribution = UniformDistribution.unit();
    for (int count = 0; count < 20; ++count) {
      Tensor tensor = RandomVariate.of(distribution, 10000000);
      {
        Stopwatch stopwatch = Stopwatch.started();
        FoldListTry.of(Tensor::add, tensor);
        System.out.println("new " + stopwatch.display_nanoSeconds());
      }
      {
        Stopwatch stopwatch = Stopwatch.started();
        Accumulate.of(tensor);
        System.out.println("old " + stopwatch.display_nanoSeconds());
      }
    }
  }
}
