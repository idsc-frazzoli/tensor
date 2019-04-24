// code by jph
package ch.ethz.idsc.tensor.lie;

import java.util.Arrays;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.alg.Dimensions;
import ch.ethz.idsc.tensor.mat.HilbertMatrix;
import ch.ethz.idsc.tensor.opt.Pi;
import ch.ethz.idsc.tensor.sca.Chop;
import junit.framework.TestCase;

public class Cross2DTest extends TestCase {
  public void testSimple() {
    // Cross[{1, 2}] == {-2, 1}
    assertEquals(Cross.of(Tensors.vector(1, 2)), Tensors.vector(-2, 1));
  }

  public void testRotation() {
    Tensor x = Tensors.vector(1, 2);
    Tensor mat = RotationMatrix.of(Pi.HALF);
    assertTrue(Chop._10.close(Cross.of(x), mat.dot(x)));
  }

  public void testApply() {
    Tensor tensor = Tensor.of(HilbertMatrix.of(10, 2).stream().map(Cross::of));
    assertEquals(Dimensions.of(tensor), Arrays.asList(10, 2));
  }

  public void testFail() {
    try {
      Cross.of(RotationMatrix.of(RealScalar.ONE));
      fail();
    } catch (Exception exception) {
      // ---
    }
  }

  public void testFail2() {
    try {
      Cross.of(Tensors.vector(1, 2, 3));
      fail();
    } catch (Exception exception) {
      // ---
    }
  }

  public void testFailNull() {
    try {
      Cross.of(null);
      fail();
    } catch (Exception exception) {
      // ---
    }
  }
}
