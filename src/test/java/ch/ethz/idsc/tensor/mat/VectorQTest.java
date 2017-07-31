// code by jph
package ch.ethz.idsc.tensor.mat;

import ch.ethz.idsc.tensor.ComplexScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.alg.VectorQ;
import ch.ethz.idsc.tensor.lie.LieAlgebras;
import junit.framework.TestCase;

public class VectorQTest extends TestCase {
  public void testScalar() {
    assertFalse(VectorQ.of(RealScalar.ONE));
    assertFalse(VectorQ.of(ComplexScalar.I));
  }

  public void testVector() {
    assertTrue(VectorQ.of(Tensors.empty()));
    assertTrue(VectorQ.of(Tensors.vector(2, 3, 1)));
  }

  public void testVectorAndLength() {
    assertTrue(VectorQ.ofLength(Tensors.empty(), 0));
    assertTrue(VectorQ.ofLength(Tensors.vector(2, 3, 1), 3));
  }

  public void testMisc() {
    assertFalse(VectorQ.of(Tensors.fromString("{{1}}")));
    assertFalse(VectorQ.of(Tensors.fromString("{{1,1,3},{7,2,9}}")));
    assertFalse(VectorQ.of(Tensors.fromString("{{1,1},{7,2,9}}")));
  }

  public void testAd() {
    assertFalse(VectorQ.of(LieAlgebras.so3()));
  }
}
