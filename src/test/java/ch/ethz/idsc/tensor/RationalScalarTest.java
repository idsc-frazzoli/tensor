// code by jph
package ch.ethz.idsc.tensor;

import ch.ethz.idsc.tensor.mat.LinearSolve;
import ch.ethz.idsc.tensor.red.Total;
import junit.framework.TestCase;

public class RationalScalarTest extends TestCase {
  public void testAdd() {
    {
      Scalar a = RationalScalar.of(3, 17);
      Scalar b = RationalScalar.of(4, 21);
      Scalar c = RationalScalar.of(131, 357);
      assertEquals(a.add(b), c);
      Tensor d = Tensors.of(a, b);
      assertEquals(Total.of(d), c);
    }
    {
      Tensor v = Tensors.of(DoubleScalar.of(2), RationalScalar.of(3, 2));
      Scalar s = RationalScalar.of(3 + 2 * 2, 2);
      assertEquals(Total.of(v), DoubleScalar.of(s.number().doubleValue()));
    }
  }

  public void testInvert() {
    Scalar a = RationalScalar.of(3, -17);
    assertEquals(a.invert(), RationalScalar.of(-17, 3));
  }

  public void testNegate() {
    Scalar a = RationalScalar.of(3, 17);
    assertEquals(a.negate(), RationalScalar.of(3, -17));
  }

  public void testTensor() {
    Tensor a = Tensors.of( //
        RationalScalar.of(1, 2), //
        RationalScalar.of(3, 5), //
        RationalScalar.of(2, 7) //
    );
    Tensor b = Tensors.of( //
        RationalScalar.of(5, 13), //
        RationalScalar.of(0, 5), //
        RationalScalar.of(-3, 2) //
    );
    assertEquals(a.dot(b), RationalScalar.of(-43, 182));
  }

  public void testMultiply() {
    Tensor a = Tensors.of( //
        RationalScalar.of(1, 2), //
        RationalScalar.of(3, 5), //
        RationalScalar.of(2, 7) //
    );
    assertEquals(a.add(a), a.multiply(RationalScalar.of(2, 1)));
  }

  public void testSolve() {
    Tensor a1 = Tensors.of( //
        RationalScalar.of(1, 2), //
        RationalScalar.of(3, 5) //
    );
    Tensor a2 = Tensors.of( //
        RationalScalar.of(5, 2), //
        RationalScalar.of(19, 5) //
    );
    Tensor b = Tensors.of( //
        RationalScalar.of(8, 9), //
        RationalScalar.of(-3, 11) //
    );
    Tensor A = Tensors.of(a1, a2);
    Tensor sol = LinearSolve.of(A, b);
    Tensor x = Tensors.of( //
        RationalScalar.of(1753, 198), //
        RationalScalar.of(-2335, 396) //
    );
    assertEquals(sol, x);
  }

  public void testEquals() {
    assertEquals(RationalScalar.of(0, 1), ZeroScalar.get());
    assertEquals(RationalScalar.of(0, 1), DoubleScalar.of(0));
    assertEquals(ZeroScalar.get(), RationalScalar.of(0, 1));
    assertEquals(DoubleScalar.of(0), RationalScalar.of(0, 1));
  }

  public void testNumber() {
    Scalar r = RealScalar.of(48962534765312235L);
    assertEquals(r.number().getClass(), Long.class);
    @SuppressWarnings("unused")
    long nothing = (Long) r.number();
  }
}
