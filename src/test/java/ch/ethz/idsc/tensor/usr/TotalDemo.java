// code by jph
package ch.ethz.idsc.tensor.usr;

import java.io.IOException;

import ch.ethz.idsc.tensor.Parallelize;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.alg.Transpose;
import ch.ethz.idsc.tensor.io.HomeDirectory;
import ch.ethz.idsc.tensor.io.Put;
import ch.ethz.idsc.tensor.io.Timing;
import ch.ethz.idsc.tensor.pdf.Distribution;
import ch.ethz.idsc.tensor.pdf.NormalDistribution;
import ch.ethz.idsc.tensor.pdf.RandomVariate;
import ch.ethz.idsc.tensor.red.Total;

/* package */ enum TotalDemo {
  ;
  public static void main(String[] args) throws IOException {
    Distribution distribution;
    distribution = NormalDistribution.of(1, 4);
    // distribution = PoissonDistribution.of(RealScalar.ONE);
    {
      int n = 100;
      Tensor a = RandomVariate.of(distribution, n, n);
      Tensor b = RandomVariate.of(distribution, n, n);
      Total.of(a);
      a.dot(b);
      Parallelize.dot(a, b);
    }
    Tensor timings = Tensors.empty();
    for (int dim = 0; dim < 100; ++dim) {
      System.out.println(dim);
      Timing timing = Timing.stopped();
      final int trials = 50;
      for (int count = 0; count < trials; ++count) {
        Tensor a = RandomVariate.of(distribution, dim, dim);
        timing.start();
        Total.of(a);
        timing.stop();
      }
      timings.append(Tensors.vector(timing.nanoSeconds() / trials));
    }
    Put.of(HomeDirectory.file("timing_total_ser.txt"), Transpose.of(timings));
  }
}
