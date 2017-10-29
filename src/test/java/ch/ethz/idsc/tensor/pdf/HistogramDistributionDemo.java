// code by jph
package ch.ethz.idsc.tensor.pdf;

import ch.ethz.idsc.tensor.RationalScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.sca.N;
import ch.ethz.idsc.tensor.sca.Round;

enum HistogramDistributionDemo {
  ;
  public static void main(String[] args) {
    Distribution gndtruth = NormalDistribution.standard();
    // gndtruth = UniformDistribution.unit();
    // gndtruth = PoissonDistribution.of(RealScalar.of(3));
    Tensor samples = RandomVariate.of(gndtruth, 10000);
    for (BinningMethod binningMethod : BinningMethod.values()) {
      Scalar width = binningMethod.apply(samples);
      System.out.println("width = " + width.map(Round._4));
      Distribution distribution = HistogramDistribution.of(samples, width);
      System.out.println("mean = " + Expectation.mean(distribution).map(Round._4));
      System.out.println("variance = " + Expectation.variance(distribution).map(Round._4));
      InverseCDF inverseCDF = InverseCDF.of(distribution);
      Scalar q50 = inverseCDF.quantile(RationalScalar.of(1, 2));
      System.out.println("q50 = " + q50);
      CDF cdf = CDF.of(distribution);
      Scalar p = cdf.p_lessThan(RealScalar.of(0));
      System.out.println("P[X<0] = " + p + " = " + N.DOUBLE.apply(p));
      PDF pdf = PDF.of(distribution);
      Scalar p0 = pdf.at(RealScalar.ZERO);
      System.out.println("P[0<=X<w] = " + p0 + " = " + N.DOUBLE.apply(p0));
      System.out.println("---");
    }
  }
}
