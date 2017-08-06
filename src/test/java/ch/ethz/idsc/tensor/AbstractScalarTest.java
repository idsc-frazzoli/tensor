// code by jph
package ch.ethz.idsc.tensor;

import ch.ethz.idsc.tensor.mat.HilbertMatrix;
import ch.ethz.idsc.tensor.mat.SymmetricMatrixQ;
import junit.framework.TestCase;

public class AbstractScalarTest extends TestCase {
  public void testMap() {
    Tensor c = Tensors.fromString("{{1}, {4}, {4}}");
    Tensor a = Tensors.vector(1, 2, 3);
    Tensor b = Tensors.vector(4).unmodifiable();
    a = a.map(s -> b);
    a.set(RealScalar.ONE, 0, 0); // requires copy() in AbstractScalar::map
    assertEquals(a, c);
  }

  public void testSimple1() {
    Tensor c = Tensors.fromString("{{1}, {4}, {4}}");
    Tensor a = Tensors.vector(1, 2, 3);
    Tensor b = Tensors.vector(4).unmodifiable();
    a.set(s -> b, Tensor.ALL);
    a.set(RealScalar.ONE, 0, 0);
    assertEquals(a, c);
  }

  public void testSetAll() {
    Tensor matrix = HilbertMatrix.of(5);
    matrix.set(Tensor::negate, Tensor.ALL, 1);
    matrix.set(Tensor::negate, 3, Tensor.ALL);
    matrix.set(Tensor::negate, 1);
    matrix.set(Tensor::negate, Tensor.ALL, 3);
    assertTrue(SymmetricMatrixQ.of(matrix));
  }
}
