// code by jph
package ch.ethz.idsc.tensor.mat;

import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Scalars;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.alg.Dimensions;
import ch.ethz.idsc.tensor.alg.Transpose;
import ch.ethz.idsc.tensor.pdf.Distribution;
import ch.ethz.idsc.tensor.pdf.NormalDistribution;
import ch.ethz.idsc.tensor.pdf.RandomVariate;
import ch.ethz.idsc.tensor.red.VectorAngle;
import junit.framework.TestCase;

public class OrthogonalizeTest extends TestCase {
  private static void _check(Tensor matrix) {
    Tensor q = Orthogonalize.of(matrix);
    assertEquals(Dimensions.of(matrix), Dimensions.of(q));
    assertTrue(OrthogonalMatrixQ.of(q));
  }

  public void testSimple1() {
    Tensor matrix = Tensors.fromString("{{1, 0, 1}}");
    assertFalse(OrthogonalMatrixQ.of(matrix));
    _check(matrix);
  }

  public void testSimple2() {
    Tensor matrix = Tensors.fromString("{{1, 0, 1}, {1, 1, 1}}");
    assertFalse(OrthogonalMatrixQ.of(matrix));
    _check(matrix);
  }

  public void testSimple3() {
    Tensor v0 = Tensors.fromString("{1, 0, 1}");
    Tensor v1 = Tensors.fromString("{0, 1, 0}");
    Tensor matrix = Tensors.of(v0, v1);
    assertFalse(OrthogonalMatrixQ.of(matrix));
    _check(matrix);
    Tensor q = Orthogonalize.of(matrix);
    assertTrue(Scalars.isZero(VectorAngle.of(q.get(0), v0)));
    assertTrue(Scalars.isZero(VectorAngle.of(q.get(1), v1)));
  }

  public void testRandom() {
    Distribution distribution = NormalDistribution.standard();
    Tensor matrix = RandomVariate.of(distribution, 10, 10);
    _check(matrix);
  }

  public void testSpan() {
    Tensor v0 = Tensors.vector(1, 1, 1);
    Tensor matrix = Tensors.of(v0);
    _check(matrix);
    Tensor q = Orthogonalize.of(matrix);
    Scalar a1 = VectorAngle.of(q.get(0), v0);
    assertTrue(Scalars.isZero(a1));
  }

  public void testComplex() {
    Tensor matrix = Tensors.fromString("{{1, 0, 1+2*I}, {-3*I, 1, 1}}");
    Tensor orth = Orthogonalize.of(matrix);
    assertTrue(UnitaryMatrixQ.of(orth));
  }

  public void testFailVector() {
    try {
      Orthogonalize.of(Tensors.vector(1, 2, 3, 4));
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }

  public void testFailMatrix() {
    try {
      Tensor matrix = Transpose.of(Tensors.fromString("{{1, 0, 1}, {1, 1, 1}}"));
      Orthogonalize.of(matrix);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }
}
