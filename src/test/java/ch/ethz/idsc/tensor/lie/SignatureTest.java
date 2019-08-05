// code by jph
package ch.ethz.idsc.tensor.lie;

import java.util.Map;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.alg.Range;
import ch.ethz.idsc.tensor.red.Tally;
import junit.framework.TestCase;

public class SignatureTest extends TestCase {
  public void testEmpty() {
    assertEquals(Signature.of(Tensors.empty()), RealScalar.ONE);
  }

  public void testSingle() {
    assertEquals(Signature.of(Tensors.vector(0)), RealScalar.ONE);
  }

  public void testTwo() {
    assertEquals(Signature.of(Tensors.vector(0, 1)), RealScalar.ONE);
    assertEquals(Signature.of(Tensors.vector(1, 0)), RealScalar.ONE.negate());
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

  public void testFour() {
    for (int length = 2; length < 6; ++length) {
      Map<Scalar, Long> map = Tally.of(Permutations.of(Range.of(0, length)).stream().map(Signature::of));
      assertEquals(map.get(RealScalar.of(1)), map.get(RealScalar.of(-1)));
    }
  }

  public void testFail() {
    try {
      Signature.of(Tensors.vector(0, 1, 0));
      fail();
    } catch (Exception exception) {
      // ---
    }
    try {
      Signature.of(Tensors.vector(0, -1));
      fail();
    } catch (Exception exception) {
      // ---
    }
    try {
      Signature.of(Tensors.vector(0, 1, 3));
      fail();
    } catch (Exception exception) {
      // ---
    }
  }
}
