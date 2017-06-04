// code by jph
package ch.ethz.idsc.tensor.alg;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.red.Norm;
import ch.ethz.idsc.tensor.sca.Chop;
import junit.framework.TestCase;

public class NormalizeTest extends TestCase {
  public void testVector1() {
    Tensor vector = Tensors.vector(3, 3, 3, 3);
    Tensor n = Normalize.of(vector);
    assertEquals(n.toString(), "{1/2, 1/2, 1/2, 1/2}");
  }

  public void testVector2() {
    Tensor vector = Tensors.vector(3, 2, 1);
    Tensor n = Normalize.of(vector);
    Scalar res = Chop.function.apply(Norm._2.of(n).subtract(RealScalar.ONE));
    assertEquals(res, res.zero());
  }

  public void testEmpty() {
    try {
      Normalize.of(Tensors.empty());
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }

  public void testZeros() {
    try {
      Normalize.of(Array.zeros(10));
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }

  public void testNorm1() {
    Tensor v = Tensors.vector(1, 1, 1);
    Tensor n = Normalize.of(v, Norm._1);
    assertEquals(n, Tensors.fromString("{1/3, 1/3, 1/3}"));
  }

  public void testNormInf() {
    Tensor d = Tensors.vector(1, 1, 1).multiply(RealScalar.of(2));
    Tensor n = Normalize.of(d, Norm.Infinity);
    assertEquals(n, Tensors.vector(1, 1, 1));
  }

  public void testFail1() {
    try {
      Normalize.of(Tensors.vector(0, 0, 0, 0), Norm._1);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }

  public void testOk1() {
    Tensor v = Tensors.vector(0, 0, 0, 0);
    Tensor n = Normalize.unlessZero(v, Norm._1);
    assertEquals(v, n);
  }
}
