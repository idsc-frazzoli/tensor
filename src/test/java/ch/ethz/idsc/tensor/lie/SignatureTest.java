// code by jph
package ch.ethz.idsc.tensor.lie;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensors;
import junit.framework.TestCase;

public class SignatureTest extends TestCase {
  public void testTwo() {
    Scalar neg = RealScalar.ONE.negate();
    assertEquals(Signature.of(Tensors.vector(0, 1)), RealScalar.ONE);
    assertEquals(Signature.of(Tensors.vector(1, 0)), neg);
  }

  public void testThree() {
    Scalar neg = RealScalar.ONE.negate();
    assertEquals(Signature.of(Tensors.vector(0, 1, 2)), RealScalar.ONE);
    assertEquals(Signature.of(Tensors.vector(0, 2, 1)), neg);
    assertEquals(Signature.of(Tensors.vector(1, 0, 2)), neg);
    assertEquals(Signature.of(Tensors.vector(1, 2, 0)), RealScalar.ONE);
    assertEquals(Signature.of(Tensors.vector(2, 0, 1)), RealScalar.ONE);
    assertEquals(Signature.of(Tensors.vector(2, 1, 0)), neg);
  }
}
