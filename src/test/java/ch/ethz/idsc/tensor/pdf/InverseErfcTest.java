// code by jph
package ch.ethz.idsc.tensor.pdf;

import ch.ethz.idsc.tensor.DoubleScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.sca.Chop;
import junit.framework.TestCase;

public class InverseErfcTest extends TestCase {
  public static final Chop CHOP_04 = Chop.below(1.4e-04);

  public void testInverseCDFAtMean() {
    InverseCDF icdf = (InverseCDF) NormalDistribution.of(2, 8);
    Scalar phi = icdf.quantile(RealScalar.of(0.5));
    assertEquals(phi, RealScalar.of(2));
  }

  public void testVector() {
    Tensor expected = Tensors.vector( //
        0.9061938024368229, -0.17914345462129164, -0.4769362762044699);
    Tensor actual = InverseErfc.of(Tensors.vector(0.2, 1.2, 1.5));
    assertTrue(CHOP_04.close(expected, actual));
  }

  public void testCorners() {
    assertEquals(InverseErfc.of(RealScalar.of(0)), DoubleScalar.POSITIVE_INFINITY);
    assertEquals(InverseErfc.of(RealScalar.of(2)), DoubleScalar.NEGATIVE_INFINITY);
  }

  public void testFail() {
    InverseCDF icdf = (InverseCDF) NormalDistribution.of(2, 8);
    try {
      icdf.quantile(RealScalar.of(1.5));
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }
}
