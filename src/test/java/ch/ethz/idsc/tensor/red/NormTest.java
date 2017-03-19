// code by jph
package ch.ethz.idsc.tensor.red;

import ch.ethz.idsc.tensor.ComplexScalar;
import ch.ethz.idsc.tensor.DoubleScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalars;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.ZeroScalar;
import junit.framework.TestCase;

public class NormTest extends TestCase {
  public void testScalar() {
    assertEquals(Norm._2.of(Scalars.fromString("0")), ZeroScalar.get());
    assertEquals(Norm._2.of(Scalars.fromString("-3.90512")), Scalars.fromString("3.90512"));
    assertEquals(Norm._2.of(Scalars.fromString("-3/7")), Scalars.fromString("3/7"));
    RealScalar rs = Norm._2.of(ComplexScalar.of(RealScalar.of(1), RealScalar.of(2))); // <- sqrt(5)
    assertEquals(rs, Scalars.fromString("2.23606797749979"));
    assertEquals(Norm._2squared.of(Scalars.fromString("-3/7")), Scalars.fromString("9/49"));
  }

  public void testVector1() {
    Tensor A = Tensors.vectorDouble(new double[] { 2, 1.5, 3 });
    assertEquals(Norm._2.of(A), Scalars.fromString("3.905124837953327"));
  }

  public void testVector2() {
    Tensor A = Tensors.of(ComplexScalar.of( //
        RealScalar.of(1), RealScalar.of(2)), DoubleScalar.of(1.5));
    assertEquals(Norm._2.of(A), Scalars.fromString("2.692582403567252"));
    Tensor a = Tensors.vectorInt(2, 3, 4);
    assertEquals(Norm._2squared.of(a), a.dot(a));
  }

  // public void testVector2Squared() {
  // Tensor A = Tensors.of(DoubleComplexScalar.of(1, 2), DoubleScalar.of(1.5));
  //// assertEquals(Norm._2.of(A), Scalars.fromString("2.6925824035672523"));
  // }
  public void testOneInfNorm() {
    {
      Tensor a = Tensors.of(Scalars.fromString("3"), Scalars.fromString("-4"));
      assertEquals(Norm._1.of(a), Scalars.fromString("7"));
      assertEquals(Norm.inf.of(a), Scalars.fromString("4"));
    }
    {
      Tensor a = Tensors.of(Scalars.fromString("1"), Scalars.fromString("2"));
      Tensor b = Tensors.of(Scalars.fromString("3"), Scalars.fromString("4"));
      Tensor c = Tensors.of(a, b);
      assertEquals(Norm._1.of(c), Scalars.fromString("6"));
      assertEquals(Norm.inf.of(c), Scalars.fromString("7"));
    }
    {
      Tensor a = Tensors.of(Scalars.fromString("1"), Scalars.fromString("2"), Scalars.fromString("8"));
      Tensor b = Tensors.of(Scalars.fromString("3"), Scalars.fromString("4"), Scalars.fromString("2"));
      Tensor c = Tensors.of(a, b);
      assertEquals(Norm._1.of(c), Scalars.fromString("10"));
      assertEquals(Norm.inf.of(c), Scalars.fromString("11"));
    }
  }

  public void testCornerCases() {
    final Tensor z = ZeroScalar.get();
    {
      assertEquals(Norm._1.of(Tensors.empty()), z);
      assertEquals(Norm._2.of(Tensors.empty()), z);
      assertEquals(Norm.inf.of(Tensors.empty()), z);
    }
    {
      assertEquals(Norm._1.of(z), z);
      assertEquals(Norm._2.of(z), z);
      assertEquals(Norm.inf.of(z), z);
    }
    {
      Tensor v = Tensors.of(z);
      assertEquals(Norm._1.of(v), z);
      assertEquals(Norm._2.of(v), z);
      assertEquals(Norm.inf.of(v), z);
    }
  }
}
