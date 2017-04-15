// code by jph
package ch.ethz.idsc.tensor.red;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Scalars;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.ZeroScalar;
import junit.framework.TestCase;

public class NormTest extends TestCase {
  // public void testVector2Squared() {
  // Tensor A = Tensors.of(DoubleComplexScalar.of(1, 2), DoubleScalar.of(1.5));
  //// assertEquals(Norm._2.of(A), Scalars.fromString("2.6925824035672523"));
  // }
  public void testOneInfNorm() {
    {
      Tensor a = Tensors.of(Scalars.fromString("3"), Scalars.fromString("-4"));
      assertEquals(Norm._1.of(a), Scalars.fromString("7"));
      assertEquals(Norm.Infinity.of(a), Scalars.fromString("4"));
    }
    {
      Tensor a = Tensors.of(Scalars.fromString("1"), Scalars.fromString("2"));
      Tensor b = Tensors.of(Scalars.fromString("3"), Scalars.fromString("4"));
      Tensor c = Tensors.of(a, b);
      assertEquals(Norm._1.of(c), Scalars.fromString("6"));
      assertEquals(Norm.Infinity.of(c), Scalars.fromString("7"));
    }
    {
      Tensor a = Tensors.of(Scalars.fromString("1"), Scalars.fromString("2"), Scalars.fromString("8"));
      Tensor b = Tensors.of(Scalars.fromString("3"), Scalars.fromString("4"), Scalars.fromString("2"));
      Tensor c = Tensors.of(a, b);
      assertEquals(Norm._1.of(c), Scalars.fromString("10"));
      assertEquals(Norm.Infinity.of(c), Scalars.fromString("11"));
    }
  }

  public void testCornerCases() {
    final Tensor z = ZeroScalar.get();
    {
      assertEquals(Norm._1.of(Tensors.empty()), z);
      assertEquals(Norm._2.of(Tensors.empty()), z);
      assertEquals(Norm.Infinity.of(Tensors.empty()), z);
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
    Tensor tensor = Tensors.vectorDouble(2.3, 1.0, 3.2);
    Scalar n = Norm.ofVector(tensor, 1.5);
    // 4.7071
    assertEquals(n, RealScalar.of(4.707100665786122));
  }
}
