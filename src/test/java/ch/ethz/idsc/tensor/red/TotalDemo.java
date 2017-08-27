// code by jph
package ch.ethz.idsc.tensor.red;

import java.io.IOException;

import ch.ethz.idsc.tensor.Parallelize;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.alg.Transpose;
import ch.ethz.idsc.tensor.io.Put;
import ch.ethz.idsc.tensor.pdf.Distribution;
import ch.ethz.idsc.tensor.pdf.NormalDistribution;
import ch.ethz.idsc.tensor.pdf.RandomVariate;
import ch.ethz.idsc.tensor.utl.Stopwatch;
import ch.ethz.idsc.tensor.utl.UserHome;

enum TotalDemo {
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
    Tensor timing = Tensors.empty();
    for (int dim = 0; dim < 100; ++dim) {
      System.out.println(dim);
      Stopwatch stopwatch = Stopwatch.stopped();
      final int trials = 50;
      for (int count = 0; count < trials; ++count) {
        Tensor a = RandomVariate.of(distribution, dim, dim);
        stopwatch.start();
        Total.of(a);
        stopwatch.stop();
      }
      timing.append(Tensors.vector(stopwatch.display_nanoSeconds() / trials));
    }
    Put.of(UserHome.file("timing_total_ser.txt"), Transpose.of(timing));
  }
}
