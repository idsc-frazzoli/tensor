// code by jph
package ch.ethz.idsc.tensor.alg;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.red.KroneckerDelta;
import junit.framework.TestCase;

public class KroneckerDeltaTest extends TestCase {
  public void testKroneckerDelta() {
    final Scalar one = RealScalar.ONE;
    assertEquals(KroneckerDelta.of(), one);
    assertEquals(KroneckerDelta.of(1), one);
    assertEquals(KroneckerDelta.of(1, 2), RealScalar.ZERO);
    assertEquals(KroneckerDelta.of(3, 3, 3), one);
    assertEquals(KroneckerDelta.of(3, 3, 1), RealScalar.ZERO);
  }

  public void testFunction() {
    Tensor vector = Tensors.vector(0, 3, 255, 0, 255, -43, 3, 0, 255, 0, 225);
    Tensor res = vector.map(KroneckerDelta.function(RealScalar.of(255)));
    assertEquals(res, Tensors.fromString("{0, 0, 1, 0, 1, 0, 0, 0, 1, 0, 0}"));
  }

  public void testStream() {
    assertEquals(KroneckerDelta.of(Tensors.vector(3, 1, 3).stream()), RealScalar.ZERO);
    assertEquals(KroneckerDelta.of(Tensors.vector(3, 3, 3).stream()), RealScalar.ONE);
  }

  public void testStream2() {
    assertEquals(KroneckerDelta.of("abc", "cde"), RealScalar.ZERO);
    assertEquals(KroneckerDelta.of("abc", "abc"), RealScalar.ONE);
    assertEquals(KroneckerDelta.of("abc"), RealScalar.ONE);
    assertEquals(KroneckerDelta.of(), RealScalar.ONE);
  }
}
