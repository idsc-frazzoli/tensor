// code by jph
package ch.ethz.idsc.tensor.red;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Scalars;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import junit.framework.TestCase;

public class NormTest extends TestCase {
  public void testOneInfNorm1() {
    Tensor a = Tensors.vector(3, -4);
    assertEquals(Norm._1.of(a), Scalars.fromString("7"));
    assertEquals(Norm.Infinity.of(a), Scalars.fromString("4"));
  }

  public void testOneInfNorm2() {
    Tensor a = Tensors.vector(1, 2);
    Tensor b = Tensors.vector(3, 4);
    Tensor c = Tensors.of(a, b);
    assertEquals(Norm._1.of(c), Scalars.fromString("6"));
    assertEquals(Norm.Infinity.of(c), Scalars.fromString("7"));
  }

  public void testOneInfNorm3() {
    Tensor a = Tensors.vector(1, 2, 8);
    Tensor b = Tensors.vector(3, 4, 2);
    Tensor c = Tensors.of(a, b);
    assertEquals(Norm._1.of(c), Scalars.fromString("10"));
    assertEquals(Norm.Infinity.of(c), Scalars.fromString("11"));
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
      assertEquals(Norm._1.of(z), z);
      assertEquals(Norm._2.of(z), z);
      assertEquals(Norm.Infinity.of(z), z);
    }
    {
      Tensor v = Tensors.of(z);
      assertEquals(Norm._1.of(v), z);
      assertEquals(Norm._2.of(v), z);
      assertEquals(Norm.Infinity.of(v), z);
    }
  }

  public void testOdd() {
    Tensor tensor = Tensors.vector(2.3, 1.0, 3.2);
    Scalar n = Norm.ofVector(tensor, 1.5);
    // 4.7071
    assertEquals(n, RealScalar.of(4.707100665786122));
  }
}
