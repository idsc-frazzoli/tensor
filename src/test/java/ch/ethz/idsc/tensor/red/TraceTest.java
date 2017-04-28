// code by jph
package ch.ethz.idsc.tensor.red;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.mat.Det;
import ch.ethz.idsc.tensor.mat.IdentityMatrix;
import ch.ethz.idsc.tensor.mat.MatrixPower;
import ch.ethz.idsc.tensor.sca.Power;
import junit.framework.TestCase;

public class TraceTest extends TestCase {
  // from wikipedia
  private static void checkOrder2(Tensor A) {
    Scalar rhs = Det.of(A);
    Scalar trA1 = Power.of(Trace.of(A), 2);
    Scalar trA2 = Trace.of(MatrixPower.of(A, 2));
    Scalar lhs = trA1.subtract(trA2).divide(RealScalar.of(2));
    assertEquals(rhs, lhs);
  }

  // from wikipedia
  private static void checkOrder3(Tensor A) {
    // Scalar rhs = Det.of(A);
    // Scalar trA1 = Power.of(Trace.of(A), 3);
    // Scalar trA2 = Trace.of(MatrixPower.of(A, 2)).multiply(Trace.of(A)).multiply(RealScalar.of(-3));
    // Scalar trA3 = Trace.of(MatrixPower.of(A, 3)).multiply(RealScalar.of(2));
    // Scalar lhs = trA1.add(trA2).add(trA3).divide(RealScalar.of(6));
    // System.out.println(trA1);
    // System.out.println(trA2);
    // System.out.println(trA3);
    // System.out.println(trA1.add(trA2).add(trA3));
    // assertEquals(rhs, lhs); // TODO this is not confirmed
  }

  public void testSimple() {
    Tensor eye = IdentityMatrix.of(10);
    Tensor red = Trace.of(eye, 0, 1);
    assertEquals(red, RealScalar.of(10));
  }

  public void testRelation() {
    Tensor A = Tensors.matrix(new Number[][] { //
        { +1, 2 }, //
        { -2, 3 } //
    });
    checkOrder2(A);
    checkOrder3(A);
  }
}
