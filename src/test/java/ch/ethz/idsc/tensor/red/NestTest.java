// code by jph
package ch.ethz.idsc.tensor.red;

import ch.ethz.idsc.tensor.ComplexScalar;
import ch.ethz.idsc.tensor.ExactScalarQ;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.alg.Array;
import ch.ethz.idsc.tensor.alg.Series;
import ch.ethz.idsc.tensor.sca.Chop;
import ch.ethz.idsc.tensor.sca.Cos;
import ch.ethz.idsc.tensor.sca.Gamma;
import ch.ethz.idsc.tensor.sca.Increment;
import ch.ethz.idsc.tensor.sca.Power;
import junit.framework.TestCase;

public class NestTest extends TestCase {
  public void testPolynomial() {
    Tensor actual = Nest.of( //
        scalar -> Power.of(scalar.add(RealScalar.ONE), RealScalar.of(2)), RealScalar.of(1), 3);
    assertTrue(ExactScalarQ.of(actual));
    assertEquals(RealScalar.of(676), actual);
  }

  public void testSeries() {
    Tensor actual = Nest.of(Series.of(Tensors.vector(1, 2, 1)), RealScalar.ONE, 3);
    assertTrue(ExactScalarQ.of(actual));
    assertEquals(RealScalar.of(676), actual);
  }

  public void testGamma() {
    Scalar expected = ComplexScalar.of(0.024484718696096586, -0.3838080212320521);
    Scalar actual = Nest.of(Gamma.FUNCTION, ComplexScalar.of(.3, .9), 3);
    assertTrue(Chop._08.close(expected, actual));
  }

  public void testCopy() {
    Tensor in = Array.zeros(2);
    Tensor re = Nest.of(null, in, 0);
    re.set(Increment.ONE, Tensor.ALL);
    assertFalse(in.equals(re));
    assertEquals(in, Array.zeros(2));
  }

  public void testFail() {
    try {
      Nest.of(Cos.FUNCTION, RealScalar.of(.3), -1);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }
}
