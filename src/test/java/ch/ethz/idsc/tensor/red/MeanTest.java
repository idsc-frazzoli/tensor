// code by jph
package ch.ethz.idsc.tensor.red;

import java.util.Optional;
import java.util.Random;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.alg.Array;
import ch.ethz.idsc.tensor.sca.Chop;
import junit.framework.TestCase;

public class MeanTest extends TestCase {
  public void testSome() {
    assertEquals(Mean.of(Tensors.vector(3, 5)), RealScalar.of(4));
    assertEquals(Mean.of(Tensors.vector(3., 5., 0, 0)), RealScalar.of(2));
  }

  public void testLimitTheorem() {
    Random rnd = new Random();
    Tensor tensor = Array.of(l -> RealScalar.of(100 + 100 * rnd.nextGaussian()), 10000);
    Scalar mean1 = Mean.of(tensor).Get();
    Scalar mean2 = Total.of(tensor.multiply(RealScalar.of(tensor.length()).invert())).Get();
    // possibly use error relative to magnitude
    assertEquals(mean1.subtract(mean2).map(Chop.function), RealScalar.ZERO);
  }

  public void testEmpty1() {
    try {
      Mean.of(Tensors.empty());
      assertTrue(false);
    } catch (Exception exception) {
      // ---
      assertTrue(exception instanceof ArithmeticException);
    }
  }

  public void testEmpty2() {
    Optional<Tensor> optional = Mean.optional(Tensors.empty());
    assertFalse(optional.isPresent());
    Scalar s = optional.orElse(RealScalar.ZERO).Get();
    assertEquals(s, RealScalar.ZERO);
  }

  public void testEmpty3() {
    Tensor s = Tensors.of(Tensors.empty());
    assertEquals(Mean.of(s), Tensors.empty());
  }
}
