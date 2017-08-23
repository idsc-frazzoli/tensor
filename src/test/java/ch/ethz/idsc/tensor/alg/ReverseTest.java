// code by jph
package ch.ethz.idsc.tensor.alg;

import java.util.Random;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.mat.IdentityMatrix;
import junit.framework.TestCase;

public class ReverseTest extends TestCase {
  public void testRev() {
    Tensor tensor = Tensors.vector(3, 2, 6, 5);
    Tensor rev = Reverse.of(tensor);
    Tensor res = Tensors.vector(5, 6, 2, 3);
    assertEquals(rev, res);
  }

  public void testReverse() {
    Random r = new Random();
    int n = 5;
    Tensor m = Array.of(index -> RealScalar.of(r.nextInt(100)), n, n, n, n);
    Tensor v = Reverse.of(IdentityMatrix.of(n));
    Tensor t1 = BasisTransform.ofForm(m, v);
    Tensor t2 = StaticHelper.nestRank(m, Reverse::of);
    Tensor t3 = Reverse.all(m);
    assertEquals(t1, t2);
    assertEquals(t1, t3);
  }

  public void testEmpty() {
    assertEquals(Tensors.empty(), Reverse.of(Tensors.empty()));
  }

  public void testFail() {
    try {
      Reverse.of(RealScalar.ONE);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }
}
