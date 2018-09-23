// code by jph
package ch.ethz.idsc.tensor;

import java.io.IOException;

import ch.ethz.idsc.tensor.alg.Transpose;
import ch.ethz.idsc.tensor.io.Put;
import ch.ethz.idsc.tensor.pdf.Distribution;
import ch.ethz.idsc.tensor.pdf.NormalDistribution;
import ch.ethz.idsc.tensor.pdf.RandomVariate;
import ch.ethz.idsc.tensor.sca.Chop;
import ch.ethz.idsc.tensor.utl.Stopwatch;
import ch.ethz.idsc.tensor.utl.UserHome;

enum DotMatVecDemo {
  ;
  private static void timeSingle() {
    Tensor a = Tensors.matrixDouble(new double[][] { { 1.0 } });
    Tensor b = Tensors.vector(1.0);
    Stopwatch s_ser = Stopwatch.started();
    a.dot(b);
    s_ser.stop();
    System.out.println(s_ser.display_seconds());
  }

  public static void main(String[] args) throws IOException {
    Distribution distribution = NormalDistribution.of(1, 4);
    {
      int n = 100;
      Tensor a = RandomVariate.of(distribution, n, n);
      Tensor b = RandomVariate.of(distribution, n, n);
      a.dot(b);
      Parallelize.dot(a, b);
    }
    Tensor timing = Tensors.empty();
    for (int dim = 0; dim < 100; ++dim) {
      System.out.println(dim);
      Stopwatch s_ser = Stopwatch.stopped();
      Stopwatch s_par = Stopwatch.stopped();
      final int trials = 100;
      for (int count = 0; count < trials; ++count) {
        Tensor a = RandomVariate.of(distribution, dim, dim);
        Tensor b = RandomVariate.of(distribution, dim);
        s_ser.start();
        Tensor cs = a.dot(b);
        s_ser.stop();
        s_par.start();
        Tensor cp = Parallelize.dot(a, b);
        s_par.stop();
        if (!Chop._12.close(cs, cp))
          throw TensorRuntimeException.of(cs);
      }
      timing.append(Tensors.vector(s_ser.display_nanoSeconds() / trials, s_par.display_nanoSeconds() / trials));
    }
    Put.of(UserHome.file("timing_matvec.txt"), Transpose.of(timing));
    timeSingle();
  }
}
