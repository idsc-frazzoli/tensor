// code by jph
package ch.ethz.idsc.tensor.pdf;

import ch.ethz.idsc.tensor.ComplexScalar;
import ch.ethz.idsc.tensor.DoubleScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.alg.Subdivide;
import ch.ethz.idsc.tensor.sca.Chop;
import junit.framework.TestCase;

public class ErfTest extends TestCase {
  public void testMathematica() {
    Tensor xs = Subdivide.of(-2, 2., 20);
    double[] values = new double[] { //
        -0.9953222650189527, -0.9890905016357308, -0.976348383344644, //
        -0.9522851197626488, -0.9103139782296354, -0.8427007929497149, //
        -0.7421009647076604, -0.6038560908479258, -0.42839235504666845, //
        -0.22270258921047853, 0., 0.22270258921047864, //
        0.42839235504666884, 0.603856090847926, 0.7421009647076606, //
        0.8427007929497149, 0.9103139782296354, 0.9522851197626488, //
        0.976348383344644, 0.9890905016357308, 0.9953222650189527 };
    int index = 0;
    for (Tensor x : xs) {
      Scalar res = Erf.FUNCTION.apply(x.Get());
      Scalar erf = DoubleScalar.of(values[index]);
      assertTrue(Chop._07.close(res, erf));
      ++index;
    }
  }

  public void testLimits() {
    assertEquals(Erf.FUNCTION.apply(DoubleScalar.POSITIVE_INFINITY), RealScalar.ONE);
    assertEquals(Erf.FUNCTION.apply(DoubleScalar.NEGATIVE_INFINITY), RealScalar.ONE.negate());
  }

  public void testMathematica2() {
    Tensor xs = Subdivide.of(1.5, 5., 20);
    double[] values = new double[] { //
        0.9661051464753108, 0.9821544715266071, 0.9911110300560857, //
        0.9958138460777998, 0.9981371537020182, 0.9992170617821089, //
        0.9996893396573608, 0.9998836690515897, 0.9999589021219005, //
        0.9999863057291097, 0.9999956972205363, 0.9999987254478377, //
        0.999999644137007, 0.9999999063619849, 0.9999999767832678, //
        0.9999999945765992, 0.9999999988065282, 0.9999999997526137, //
        0.999999999951703, 0.9999999999911201, 0.9999999999984626 //
    };
    assertTrue(Chop._07.close(Erf.of(xs), Tensors.vectorDouble(values)));
  }

  public void testComplexFail() {
    Scalar scalar = ComplexScalar.of(1.2, 3.4);
    try {
      Erf.FUNCTION.apply(scalar);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }
}
