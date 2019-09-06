// code by jph
package ch.ethz.idsc.tensor.alg;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.mat.IdentityMatrix;
import ch.ethz.idsc.tensor.sca.Clip;
import ch.ethz.idsc.tensor.sca.Clips;
import junit.framework.TestCase;

public class LastTest extends TestCase {
  public void testScalarReturn() {
    Scalar scalar = Last.of(Range.of(1, 4));
    assertEquals(scalar, RealScalar.of(3));
  }

  public void testUseCase() {
    Clip clip = Clips.interval(RealScalar.of(2), Last.of(Range.of(1, 4)));
    clip.requireInside(RealScalar.of(3));
  }

  public void testLast() {
    assertEquals(Last.of(Tensors.vector(3, 2, 6, 4)), RealScalar.of(4));
  }

  public void testMatrix() {
    assertEquals(Last.of(IdentityMatrix.of(4)), UnitVector.of(4, 3));
  }

  public void testFailEmpty() {
    try {
      Last.of(Tensors.empty());
      fail();
    } catch (Exception exception) {
      // ---
    }
  }

  public void testFailScalar() {
    try {
      Last.of(RealScalar.of(99));
      fail();
    } catch (Exception exception) {
      // ---
    }
  }
}
