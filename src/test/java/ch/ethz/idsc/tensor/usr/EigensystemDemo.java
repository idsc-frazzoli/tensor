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
import ch.ethz.idsc.tensor.mat.Eigensystem;
import ch.ethz.idsc.tensor.mat.Inverse;
import ch.ethz.idsc.tensor.pdf.Distribution;
import ch.ethz.idsc.tensor.pdf.NormalDistribution;
import ch.ethz.idsc.tensor.pdf.RandomVariate;

/* package */ enum EigensystemDemo {
  ;
  public static void main(String[] args) throws IOException {
    Distribution distribution;
    distribution = NormalDistribution.of(1, 4);
    // distribution = PoissonDistribution.of(RealScalar.ONE);
    {
      int n = 100;
      Tensor a = RandomVariate.of(distribution, n, n);
      Tensor b = RandomVariate.of(distribution, n, n);
      Inverse.of(a);
      a.dot(b);
      Parallelize.dot(a, b);
      a = a.add(Transpose.of(a));
      Eigensystem.ofSymmetric(a);
    }
    Tensor timings = Tensors.empty();
    for (int dim = 1; dim <= 25; ++dim) {
      System.out.println(dim);
      Timing timing = Timing.stopped();
      final int trials = 70 - dim;
      for (int count = 0; count < trials; ++count) {
        Tensor a = RandomVariate.of(distribution, dim, dim);
        a = a.add(Transpose.of(a));
        timing.start();
        Eigensystem.ofSymmetric(a);
        timing.stop();
      }
      timings.append(Tensors.vector(timing.nanoSeconds() / trials));
    }
    Put.of(HomeDirectory.file("timing_eigen_ser.txt"), Transpose.of(timings));
  }
}
