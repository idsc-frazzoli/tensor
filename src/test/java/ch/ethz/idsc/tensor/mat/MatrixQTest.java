// code by jph
package ch.ethz.idsc.tensor.mat;

import ch.ethz.idsc.tensor.ComplexScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Tensors;
import junit.framework.TestCase;

public class MatrixQTest extends TestCase {
  public void testScalar() {
    assertFalse(MatrixQ.of(RealScalar.ONE));
    assertFalse(MatrixQ.of(ComplexScalar.I));
  }

  public void testVector() {
    assertFalse(MatrixQ.of(Tensors.vector(2, 3, 1)));
  }

  public void testMatrix() {
    assertTrue(MatrixQ.of(Tensors.fromString("{{1}}")));
    assertTrue(MatrixQ.of(Tensors.fromString("{{1,1,3},{7,2,9}}")));
    assertFalse(MatrixQ.of(Tensors.fromString("{{1,1},{7,2,9}}")));
  }
}
