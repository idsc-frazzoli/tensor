// code by jph
package ch.ethz.idsc.tensor.opt;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.pdf.DiscreteUniformDistribution;
import ch.ethz.idsc.tensor.pdf.Distribution;
import ch.ethz.idsc.tensor.pdf.RandomVariate;
import ch.ethz.idsc.tensor.pdf.UniformDistribution;
import junit.framework.TestCase;

enum StaticHelper {
  ;
  static void checkMatch(Interpolation interpolation) {
    Distribution distribution = UniformDistribution.of(0, 2);
    for (int count = 0; count < 20; ++count) {
      Scalar scalar = RandomVariate.of(distribution);
      TestCase.assertEquals( //
          interpolation.get(Tensors.of(scalar)), //
          interpolation.at(scalar));
    }
  }

  static void checkMatchExact(Interpolation interpolation) {
    Distribution distribution = DiscreteUniformDistribution.of(0, 3);
    for (int count = 0; count < 20; ++count) {
      Scalar scalar = RandomVariate.of(distribution);
      TestCase.assertEquals( //
          interpolation.get(Tensors.of(scalar)), //
          interpolation.at(scalar));
    }
  }

  static void getScalarFail(Interpolation interpolation) {
    try {
      interpolation.get(RealScalar.of(1.4));
      TestCase.assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
    try {
      interpolation.get(RealScalar.ONE);
      TestCase.assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }
}
