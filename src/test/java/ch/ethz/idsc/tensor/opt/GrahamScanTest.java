// code by jph
package ch.ethz.idsc.tensor.opt;

import ch.ethz.idsc.tensor.Scalars;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.sca.Sign;
import junit.framework.TestCase;

public class GrahamScanTest extends TestCase {
  public void testColinear() {
    assertTrue(Scalars.isZero(GrahamScan.ccw( //
        Tensors.vector(1, 0), //
        Tensors.vector(2, 0), //
        Tensors.vector(1.5, 0))));
    assertTrue(Scalars.isZero(GrahamScan.ccw( //
        Tensors.vector(1, 0), //
        Tensors.vector(2, 0), //
        Tensors.vector(3, 0))));
    assertTrue(Scalars.isZero(GrahamScan.ccw( //
        Tensors.vector(1, 0), //
        Tensors.vector(2, 0), //
        Tensors.vector(-1, 0))));
  }

  public void testCcw() {
    assertTrue(Sign.isPositive(GrahamScan.ccw( //
        Tensors.vector(1, 0), //
        Tensors.vector(2, 0), //
        Tensors.vector(1, 10))));
    assertTrue(Sign.isPositive(GrahamScan.ccw( //
        Tensors.vector(1, 0), //
        Tensors.vector(2, 0), //
        Tensors.vector(3, 1))));
    assertTrue(Sign.isPositive(GrahamScan.ccw( //
        Tensors.vector(1, 0), //
        Tensors.vector(2, 0), //
        Tensors.vector(-1, 1))));
  }

  public void testCw() {
    assertTrue(Sign.isNegative(GrahamScan.ccw( //
        Tensors.vector(1, 0), //
        Tensors.vector(2, 0), //
        Tensors.vector(1, -10))));
    assertTrue(Sign.isNegative(GrahamScan.ccw( //
        Tensors.vector(1, 0), //
        Tensors.vector(2, 0), //
        Tensors.vector(3, -1))));
    assertTrue(Sign.isNegative(GrahamScan.ccw( //
        Tensors.vector(1, 0), //
        Tensors.vector(2, 0), //
        Tensors.vector(-1, -1))));
  }
}
