// code by jph
package ch.ethz.idsc.tensor.red;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalars;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import junit.framework.TestCase;

public class NormTest extends TestCase {
  public void testOneInfNorm1() {
    Tensor a = Tensors.vector(3, -4);
    assertEquals(Norm._1.of(a), Scalars.fromString("7"));
    assertEquals(Norm.INFINITY.of(a), Scalars.fromString("4"));
  }

  public void testOneInfNorm2() {
    Tensor a = Tensors.vector(1, 2);
    Tensor b = Tensors.vector(3, 4);
    Tensor c = Tensors.of(a, b);
    assertEquals(Norm._1.of(c), Scalars.fromString("6"));
    assertEquals(Norm.INFINITY.of(c), Scalars.fromString("7"));
  }

  public void testOneInfNorm3() {
    Tensor a = Tensors.vector(1, 2, 8);
    Tensor b = Tensors.vector(3, 4, 2);
    Tensor c = Tensors.of(a, b);
    assertEquals(Norm._1.ofMatrix(c), Scalars.fromString("10"));
    assertEquals(Norm.INFINITY.ofMatrix(c), Scalars.fromString("11"));
  }

  public void testCornerCases() {
    final Tensor z = RealScalar.ZERO;
    try {
      Norm._1.of(Tensors.empty());
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
    {
      Tensor v = Tensors.of(z);
      assertEquals(Norm._1.of(v), z);
      assertEquals(Norm._2.of(v), z);
      assertEquals(Norm.INFINITY.of(v), z);
    }
  }
}
