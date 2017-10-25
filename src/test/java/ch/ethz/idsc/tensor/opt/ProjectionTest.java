// code by jph
package ch.ethz.idsc.tensor.opt;

import ch.ethz.idsc.tensor.ExactScalarQ;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.alg.UnitVector;
import junit.framework.TestCase;

public class ProjectionTest extends TestCase {
  public void testReal() {
    Tensor p1 = Projection.of(Tensors.vector(1, 1, 1), Tensors.vector(1, 0, 0));
    assertEquals(p1, UnitVector.of(3, 0));
    Tensor p2 = Projection.of(Tensors.vector(5, 6, 7), Tensors.vector(1, 1, 1));
    assertEquals(p2, Tensors.vector(6, 6, 6));
    assertTrue(ExactScalarQ.all(p2));
  }

  public void testComplex() {
    Tensor p2 = Projection.of(Tensors.fromString("{5, I, 7}"), Tensors.vector(1, 1, 1));
    assertEquals(Tensors.fromString("{4 + I/3, 4 + I/3, 4 + I/3}"), p2);
    assertTrue(ExactScalarQ.all(p2));
  }

  public void testZero() {
    try {
      Projection.on(Tensors.vector(0, 0, 0));
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
    try {
      Projection.on(Tensors.vector(0.0, 0, 0));
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }
}
