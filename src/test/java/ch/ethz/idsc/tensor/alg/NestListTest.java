// code by jph
package ch.ethz.idsc.tensor.alg;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.sca.Cos;
import junit.framework.TestCase;

public class NestListTest extends TestCase {
  public void testLength() {
    Tensor list = NestList.of(Cos::of, RealScalar.ONE, 4);
    assertEquals(list.length(), 5);
  }

  private static Tensor _clear(Tensor a) {
    a.set(RealScalar.ZERO, 0);
    a.append(RealScalar.ONE);
    return Tensors.vector(1, 2, 3);
  }

  public void testClear() {
    Tensor t = Tensors.vector(1, 2, 3);
    Tensor x = Tensors.vector(1, 2, 3);
    Tensor list = NestList.of(NestListTest::_clear, x, 3);
    assertEquals(list, Tensors.of(t, t, t, t));
  }

  public void testReferences() {
    Tensor vector = Tensors.vector(1, 2, 3);
    Tensor list = NestList.of(f -> f, vector, 0);
    vector.set(RealScalar.ZERO, 0);
    assertEquals(list, Tensors.fromString("{{1,2,3}}"));
  }
}
