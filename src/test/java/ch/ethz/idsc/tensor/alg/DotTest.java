// code by jph
package ch.ethz.idsc.tensor.alg;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.mat.IdentityMatrix;
import ch.ethz.idsc.tensor.sca.Increment;
import junit.framework.TestCase;

public class DotTest extends TestCase {
  public void testDot1() {
    Tensor m1 = Tensors.matrix((i, j) -> RealScalar.of(2 + i - 3 * j), 3, 4);
    Tensor m2 = Tensors.matrix((i, j) -> RealScalar.of(8 + 2 * i + 9 * j), 4, 2);
    Tensor m3 = Tensors.matrix((i, j) -> RealScalar.of(-3 - 7 * i + j), 2, 7);
    Tensor m4 = Transpose.of( //
        Tensors.matrix((i, j) -> RealScalar.of(100 - 7 * i + j), 2, 7));
    Tensor d1 = Dot.of(m1, m2, m3, m4);
    assertEquals(d1, m1.dot(m2).dot(m3).dot(m4));
  }

  public void testDot2() {
    Tensor m1 = Tensors.matrix((i, j) -> RealScalar.of(2 + i - 3 * j), 3, 4);
    Tensor m2 = Tensors.vector(i -> RealScalar.of(8 + 2 * i), 4);
    Tensor m3 = Tensors.vector(i -> RealScalar.of(2 + 2 * i), 3);
    Tensor d1 = Dot.of(m1, m2, m3);
    assertEquals(d1, m1.dot(m2).dot(m3));
  }

  public void testCopy() {
    Tensor in = Array.zeros(2);
    Tensor re = Dot.of(in);
    re.set(Increment.ONE, Tensor.ALL);
    assertFalse(in.equals(re));
    assertEquals(in, Array.zeros(2));
  }

  public void testIdentity() {
    Tensor m = IdentityMatrix.of(7);
    Tensor d = Dot.of(m, m, m, m);
    assertEquals(m, d);
  }
}
