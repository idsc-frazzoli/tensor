// code by jph
package ch.ethz.idsc.tensor.pdf;

import ch.ethz.idsc.tensor.Scalar;

// TODO calculate pdf and cdf of standard normal distribution 
enum StandardNormalDistribution implements Distribution, PDF, CDF {
  INSTANCE;
  @Override
  public Scalar at(Scalar x) {
    return null; // use lookup tables
  }

  @Override
  public Scalar p_lessThan(Scalar x) {
    return null; // use lookup tables
  }

  @Override
  public Scalar p_lessEquals(Scalar x) {
    return null; // use lookup tables
  }
}
