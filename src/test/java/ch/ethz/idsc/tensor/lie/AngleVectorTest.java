// code by jph
package ch.ethz.idsc.tensor.lie;

import ch.ethz.idsc.tensor.ExactTensorQ;
import ch.ethz.idsc.tensor.RationalScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.opt.Pi;
import ch.ethz.idsc.tensor.pdf.Distribution;
import ch.ethz.idsc.tensor.pdf.NormalDistribution;
import ch.ethz.idsc.tensor.pdf.RandomVariate;
import ch.ethz.idsc.tensor.pdf.UniformDistribution;
import ch.ethz.idsc.tensor.red.Norm;
import ch.ethz.idsc.tensor.sca.ArcTan;
import ch.ethz.idsc.tensor.sca.Chop;
import junit.framework.TestCase;

public class AngleVectorTest extends TestCase {
  public void testNorm() {
    Distribution distribution = UniformDistribution.of(Pi.VALUE.negate(), Pi.VALUE);
    for (int index = 0; index < 10; ++index) {
      Scalar angle = RandomVariate.of(distribution).negate(); // prevent angle == -pi
      Tensor vector = AngleVector.of(angle);
      Chop._14.requireClose(Norm._2.ofVector(vector), RealScalar.ONE);
      Scalar check = ArcTan.of(vector.Get(0), vector.Get(1));
      Chop._14.requireClose(angle, check);
    }
  }

  public void testMatrix() {
    Scalar angle = RealScalar.ONE;
    Tensor vector = AngleVector.of(angle);
    Tensor matrix = RotationMatrix.of(angle);
    assertEquals(vector, matrix.get(Tensor.ALL, 0));
  }

  public void testRotation() {
    ExactTensorQ.require(AngleVector.rotation(RationalScalar.of(-2, 2)));
    assertEquals(AngleVector.rotation(RationalScalar.of(-2, 2)), Tensors.vector(+1, 0));
    assertEquals(AngleVector.rotation(RationalScalar.of(0, 2)), Tensors.vector(+1, 0));
    assertEquals(AngleVector.rotation(RationalScalar.of(1, 2)), Tensors.vector(-1, 0));
    assertFalse(ExactTensorQ.of(AngleVector.rotation(RealScalar.of(-2.0))));
  }

  public void testRotationOfEquivalence() {
    Distribution distribution = NormalDistribution.standard();
    for (int count = 0; count < 50; ++count) {
      Scalar fraction = RandomVariate.of(distribution);
      Chop._12.requireClose(AngleVector.rotation(fraction), AngleVector.of(fraction.multiply(Pi.TWO)));
    }
  }

  public void testNullFail() {
    try {
      AngleVector.of(null);
      fail();
    } catch (Exception exception) {
      // ---
    }
  }
}
