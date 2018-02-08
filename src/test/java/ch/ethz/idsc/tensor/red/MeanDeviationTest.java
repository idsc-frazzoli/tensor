// code by jph
package ch.ethz.idsc.tensor.red;

import ch.ethz.idsc.tensor.RationalScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.lie.LieAlgebras;
import ch.ethz.idsc.tensor.mat.HilbertMatrix;
import ch.ethz.idsc.tensor.pdf.RandomVariate;
import ch.ethz.idsc.tensor.pdf.UniformDistribution;
import ch.ethz.idsc.tensor.sca.Chop;
import ch.ethz.idsc.tensor.sca.Clip;
import ch.ethz.idsc.tensor.sca.N;
import junit.framework.TestCase;

public class MeanDeviationTest extends TestCase {
  public void testMathematica1() {
    Scalar value = MeanDeviation.ofVector(Tensors.fromString("{1, 2, 3, 7}"));
    assertEquals(value, RationalScalar.of(15, 8));
  }

  public void testMathematica2() {
    Scalar value = MeanDeviation.ofVector(Tensors.fromString("{1, 2, 3, 7/11}"));
    assertEquals(value, RationalScalar.of(37, 44));
    assertTrue(Chop._14.close(N.DOUBLE.apply(value), RealScalar.of(0.84090909090909090909090909091)));
  }

  public void testArray() {
    Scalar value = MeanDeviation.ofVector(RandomVariate.of(UniformDistribution.unit(), 10000));
    Clip clip = Clip.function(0.23, 0.27);
    clip.requireInside(value);
  }

  public void testFail() {
    try {
      MeanDeviation.ofVector(RealScalar.ONE);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
    try {
      MeanDeviation.ofVector(Tensors.empty());
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
    try {
      MeanDeviation.ofVector(HilbertMatrix.of(3, 4));
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
    try {
      MeanDeviation.ofVector(LieAlgebras.sl3());
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }
}
