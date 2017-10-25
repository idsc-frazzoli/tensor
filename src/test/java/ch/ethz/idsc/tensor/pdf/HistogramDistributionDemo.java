// code by jph
package ch.ethz.idsc.tensor.pdf;

import ch.ethz.idsc.tensor.RationalScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.sca.N;

enum HistogramDistributionDemo {
  ;
  public static void main(String[] args) {
    Tensor samples = RandomVariate.of(NormalDistribution.standard(), 100000);
    Distribution distribution = HistogramDistribution.of(samples);
    System.out.println("mean = " + Expectation.mean(distribution));
    System.out.println("variance = " + Expectation.variance(distribution));
    InverseCDF inverseCDF = InverseCDF.of(distribution);
    Scalar q50 = inverseCDF.quantile(RationalScalar.of(1, 2));
    System.out.println("q50 = " + q50);
    CDF cdf = CDF.of(distribution);
    Scalar p = cdf.p_lessThan(RealScalar.of(0));
    System.out.println("P[X<0] = " + p + " = " + N.DOUBLE.apply(p));
    System.out.println("width = " + ((HistogramDistribution) distribution).width());
    PDF pdf = PDF.of(distribution);
    Scalar p0 = pdf.at(RealScalar.ZERO);
    System.out.println("P[0<=X<w] = " + p0);
  }
}
