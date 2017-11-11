// code by jph
package ch.ethz.idsc.tensor.usr;

import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.pdf.Distribution;
import ch.ethz.idsc.tensor.pdf.HistogramDistribution;
import ch.ethz.idsc.tensor.pdf.PoissonBinomialDistribution;
import ch.ethz.idsc.tensor.pdf.RandomVariate;
import ch.ethz.idsc.tensor.pdf.UniformDistribution;
import ch.ethz.idsc.tensor.red.Mean;
import ch.ethz.idsc.tensor.red.Variance;
import ch.ethz.idsc.tensor.utl.Stopwatch;

enum PoissonBinomialDemo {
  ;
  public static void main(String[] args) {
    Tensor p_vector = RandomVariate.of(UniformDistribution.unit(), 1000);
    Distribution distribution = PoissonBinomialDistribution.of(p_vector);
    Tensor samples;
    {
      Stopwatch stopwatch = Stopwatch.started();
      samples = RandomVariate.of(distribution, 1000);
      double seconds = stopwatch.display_seconds();
      System.out.println("sec  pbin = " + seconds);
    }
    Distribution histogram = HistogramDistribution.of(samples);
    System.out.println("mean hist = " + Mean.of(histogram));
    System.out.println("mean pbin = " + Mean.of(distribution));
    System.out.println("var  hist = " + Variance.of(histogram));
    System.out.println("var  pbin = " + Variance.of(distribution));
    {
      Stopwatch stopwatch = Stopwatch.started();
      RandomVariate.of(histogram, 1000);
      double seconds = stopwatch.display_seconds();
      System.out.println("sec  hist = " + seconds);
    }
  }
}
