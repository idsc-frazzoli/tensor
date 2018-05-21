// code by jph
package ch.ethz.idsc.tensor.mat;

import ch.ethz.idsc.tensor.ExactScalarQ;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.qty.Quantity;
import junit.framework.TestCase;

public class DiagonalMatrixTest extends TestCase {
  public void testIdentity() {
    Tensor matrix = DiagonalMatrix.with(Tensors.vector(1, 1, 1, 1, 1, 1, 1, 1, 1, 1));
    assertEquals(IdentityMatrix.of(10), matrix);
  }

  public void testMisc1() {
    Tensor matrix = DiagonalMatrix.of(-2, 3, -4);
    assertEquals(Det.of(matrix).number(), 2 * 3 * 4);
    assertTrue(ExactScalarQ.all(matrix));
  }

  public void testDiagonalMatrix() {
    Tensor m1 = DiagonalMatrix.with(Tensors.vectorDouble(12, 3.2, .32));
    Tensor m2 = DiagonalMatrix.of(12, 3.2, .32);
    assertEquals(m1, m2);
  }

  public void testMisc2() {
    Tensor matrix = DiagonalMatrix.of( //
        RealScalar.of(-2), RealScalar.of(3), RealScalar.of(-4));
    assertTrue(ExactScalarQ.all(matrix));
    assertEquals(Det.of(matrix).number(), 2 * 3 * 4);
  }

  public void testMisc3() {
    try {
      Tensor tensor = RealScalar.of(-2);
      DiagonalMatrix.with(tensor);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }

  public void testQuantity() {
    Scalar qs1 = Quantity.of(1, "m");
    Scalar qs2 = Quantity.of(2, "s");
    Tensor vec = Tensors.of(qs1, qs2);
    Tensor matrix = DiagonalMatrix.with(vec);
    assertTrue(ExactScalarQ.all(matrix));
    assertEquals(matrix.toString(), "{{1[m], 0[m]}, {0[s], 2[s]}}");
  }

  public void testFailScalar() {
    Tensor matrix = DiagonalMatrix.of(RealScalar.ONE);
    assertEquals(matrix.toString(), "{{1}}");
    try {
      DiagonalMatrix.with(RealScalar.ONE);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }

  public void testFailNonVector() {
    try {
      DiagonalMatrix.with(Tensors.fromString("{1,2,{3}}"));
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }

  public void testFailEmpty() {
    try {
      DiagonalMatrix.with(Tensors.empty());
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }

  public void testFailScalarEmpty() {
    try {
      DiagonalMatrix.of(new Scalar[] {});
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }

  public void testFailNumberEmpty() {
    try {
      DiagonalMatrix.of(new Number[] {});
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }
}
