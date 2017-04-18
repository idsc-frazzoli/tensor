// code by jph
package ch.ethz.idsc.tensor.mat;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.alg.Reverse;
import junit.framework.TestCase;

public class DetTest extends TestCase {
  public void testDet1() {
    Tensor m = Tensors.matrix(new Number[][] { //
        { +2, 3, 4 }, //
        { +0, 0, 1 }, //
        { -5, 3, 4 } });
    assertEquals(Det.of(m), RealScalar.of(-21));
  }

  public void testDet2() {
    Tensor m = Tensors.matrix(new Number[][] { //
        { -2, 3, 4 }, //
        { +0, 0, 1 }, //
        { -5, 3, 4 } });
    assertEquals(Det.of(m), RealScalar.of(-9));
  }

  public void testDet3() {
    Tensor m = Tensors.matrix(new Number[][] { //
        { -2, 3, +4 }, //
        { +0, 2, -1 }, //
        { -5, 3, +4 } });
    assertEquals(Det.of(m), RealScalar.of(33));
  }

  public void testId() {
    for (int n = 1; n < 10; ++n)
      assertEquals(Det.of(IdentityMatrix.of(n)), RealScalar.ONE);
  }

  public void testReversedId() {
    Tensor actual = Tensors.vector(7, 1, -1, -1, 1, 1, -1, -1, 1, 1, -1, -1, 1, 1, -1, -1, 1, 1);
    for (int n = 1; n < 10; ++n)
      assertEquals(Det.of(Reverse.of(IdentityMatrix.of(n))), actual.Get(n));
  }

  public void testDet4() {
    Tensor m = Tensors.matrix(new Number[][] { //
        { -2, 3, +4, 0 }, //
        { +0, 2, -1, 2 }, //
        { -5, 3, +4, 1 }, //
        { +0, 2, -1, 0 } //
    });
    assertEquals(Det.of(m), RealScalar.of(-66));
    m.set(RealScalar.of(9), 3, 0);
    assertEquals(Det.of(m), RealScalar.of(33));
  }
}
