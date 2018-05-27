// code by jph
package ch.ethz.idsc.tensor.alg;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import junit.framework.TestCase;

public class AccumulateTest extends TestCase {
  public void testEmpty() {
    Tensor d = Accumulate.of(Tensors.empty());
    assertEquals(d, Tensors.empty());
  }

  public void testSimple() {
    Tensor c = Tensors.vector(2, 3, 1, 0);
    Tensor actual = Accumulate.of(c);
    Tensor expected = Tensors.vector(2, 5, 6, 6);
    assertEquals(expected, actual);
  }

  public void testProd() {
    Tensor c = Tensors.vector(2, 3, -1, 0);
    Tensor actual = Accumulate.prod(c);
    Tensor expected = Tensors.vector(2, 6, -6, 0);
    assertEquals(expected, actual);
  }

  public void testSimple2() {
    Tensor c = Tensors.matrix(new Number[][] { { 1, 2 }, { 5, 5 }, { -3, -9 } });
    Tensor actual = Accumulate.of(c);
    Tensor expected = Tensors.matrix(new Number[][] { { 1, 2 }, { 6, 7 }, { 3, -2 } });
    assertEquals(expected, actual);
  }

  public void testScalarFail() {
    try {
      Accumulate.of(RealScalar.ONE);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }

  public void testScalarProdFail() {
    try {
      Accumulate.prod(RealScalar.ONE);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }
}
