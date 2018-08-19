// code by jph
package ch.ethz.idsc.tensor.red;

import ch.ethz.idsc.tensor.ComplexScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.lie.LieAlgebras;
import ch.ethz.idsc.tensor.mat.Det;
import ch.ethz.idsc.tensor.mat.Eigensystem;
import ch.ethz.idsc.tensor.mat.HilbertMatrix;
import ch.ethz.idsc.tensor.mat.IdentityMatrix;
import ch.ethz.idsc.tensor.mat.MatrixPower;
import ch.ethz.idsc.tensor.mat.SquareMatrixQ;
import ch.ethz.idsc.tensor.sca.Chop;
import ch.ethz.idsc.tensor.sca.Power;
import junit.framework.TestCase;

public class TraceTest extends TestCase {
  // from wikipedia
  private static Scalar _tr2Formula(Tensor A) {
    assertTrue(SquareMatrixQ.of(A));
    Scalar trA1 = Power.of(Trace.of(A), 2);
    Scalar trA2 = Trace.of(MatrixPower.of(A, 2));
    return trA1.subtract(trA2).divide(RealScalar.of(2));
  }

  // from wikipedia
  public void testViete() {
    Tensor matrix = Tensors.fromString("{{60, 30, 20}, {30, 20, 15}, {20, 15, 12}}");
    Eigensystem eig = Eigensystem.ofSymmetric(matrix);
    assertTrue(Chop._10.close(Trace.of(matrix), Total.of(eig.values()))); // 1. Viete
    assertTrue(Chop._10.close(Det.of(matrix), Total.prod(eig.values()))); // 3. Viete
    {
      Scalar l1 = eig.values().Get(0);
      Scalar l2 = eig.values().Get(1);
      Scalar l3 = eig.values().Get(2);
      Scalar res = _tr2Formula(matrix);
      Tensor vector = Tensors.of(l1.multiply(l2), l2.multiply(l3), l3.multiply(l1));
      Tensor cmp = Total.of(vector);
      assertTrue(Chop._10.close(cmp, res)); // 2. Viete
    }
  }

  public void testIdentityMatrix() {
    for (int n = 3; n < 8; ++n)
      assertEquals(Trace.of(IdentityMatrix.of(n), 0, 1), RealScalar.of(n));
  }

  public void testMatrix1X1() {
    assertEquals(Trace.of(Tensors.fromString("{{3+2*I}}")), ComplexScalar.of(3, 2));
  }

  public void testEmpty() {
    try {
      Trace.of(Tensors.empty()); // mathematica gives 0 == Tr[{}]
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
    try {
      Trace.of(Tensors.fromString("{{}}")); // mathematica gives 0 == Tr[{{}}]
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }

  public void testDimensionsFail() {
    try {
      Trace.of(RealScalar.ONE);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
    try {
      Trace.of(Tensors.vector(1, 2, 3));
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
    try {
      Trace.of(LieAlgebras.sl2());
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }

  public void testParamFail() {
    try {
      Trace.of(HilbertMatrix.of(3, 3), 0, 0);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }

  public void testFormatFail() {
    try {
      Trace.of(HilbertMatrix.of(3, 4), 0, 1);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }
}
