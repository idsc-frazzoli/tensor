// code by jph
package ch.ethz.idsc.tensor.pdf;

import ch.ethz.idsc.tensor.Scalar;

// TODO implement
/* package */ class StudentTDistribution implements Distribution {
  // ---
  @SuppressWarnings("unused")
  private final Scalar v;

  private StudentTDistribution(Scalar v) {
    this.v = v;
  }
}
