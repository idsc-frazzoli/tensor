// code by jph
package ch.ethz.idsc.tensor;

import junit.framework.TestCase;

public class TensorsPredicateTest extends TestCase {
  public void testIsEmpty() {
    assertFalse(Tensors.isEmpty(RealScalar.ONE));
    assertTrue(Tensors.isEmpty(Tensors.empty()));
    assertTrue(Tensors.isEmpty(Tensors.vector()));
    assertFalse(Tensors.isEmpty(Tensors.vector(1, 2, 3)));
  }

  public void testNonEmpty() {
    assertTrue(Tensors.nonEmpty(RealScalar.ONE));
    assertFalse(Tensors.nonEmpty(Tensors.empty()));
    assertFalse(Tensors.nonEmpty(Tensors.vector()));
    assertTrue(Tensors.nonEmpty(Tensors.vector(1, 2, 3)));
  }

  public void testIsUnmodifiable() {
    Tensor canwrite = Tensors.vector(1, 2, 3);
    Tensor readonly = canwrite.unmodifiable();
    assertFalse(Tensors.isUnmodifiable(canwrite));
    assertTrue(Tensors.isUnmodifiable(readonly));
    assertFalse(Tensors.isUnmodifiable(readonly.copy()));
  }
}
