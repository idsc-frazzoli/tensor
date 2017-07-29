// code by jph
package ch.ethz.idsc.tensor.usr;

import ch.ethz.idsc.tensor.RationalScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.io.Pretty;
import ch.ethz.idsc.tensor.pdf.BinomialDistribution;
import ch.ethz.idsc.tensor.pdf.CDF;
import ch.ethz.idsc.tensor.pdf.Distribution;
import ch.ethz.idsc.tensor.pdf.GeometricDistribution;
import ch.ethz.idsc.tensor.pdf.NormalDistribution;
import ch.ethz.idsc.tensor.pdf.PDF;
import ch.ethz.idsc.tensor.pdf.RandomVariate;
import ch.ethz.idsc.tensor.sca.Round;

enum PDFDemo {
  ;
  public static void main(String[] args) {
    { // random variate from a distribution
      Distribution distribution = NormalDistribution.of(RealScalar.of(-2), RealScalar.of(3));
      System.out.println(distribution.getClass().getSimpleName());
      Tensor array = RandomVariate.of(distribution, 2, 3);
      System.out.println(Pretty.of(array.map(Round._4)));
    }
    { // probability
      Distribution distribution = BinomialDistribution.of(20, RationalScalar.of(1, 3));
      System.out.println(distribution.getClass().getSimpleName());
      PDF pdf = PDF.of(distribution);
      System.out.println("P(X=14) = " + pdf.at(RealScalar.of(14)));
    }
    { // cumulative density
      Distribution distribution = GeometricDistribution.of(RationalScalar.of(1, 8));
      System.out.println(distribution.getClass().getSimpleName());
      CDF cdf = CDF.of(distribution);
      System.out.println("P(X<14) = " + cdf.p_lessThan(RealScalar.of(14)));
    }
  }
}
