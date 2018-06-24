// code by jph
package ch.ethz.idsc.tensor;

import ch.ethz.idsc.tensor.alg.TensorMap;
import junit.framework.TestCase;

public class PmulTest extends TestCase {
  public void testVectorMatrixEx1() {
    Tensor mat = Tensors.fromString("{{1,2,3},{4,5,6}}");
    Tensors.vector(-2, 2).pmul(mat);
    Tensor factor = Tensors.vector(-2, 2, 2);
    Tensor rep = TensorMap.of(row -> row.pmul(factor), mat, 1);
    assertEquals(rep, Tensors.fromString("{{-2, 4, 6}, {-8, 10, 12}}"));
  }

  public void testVectorMatrixEx2() {
    Tensor a = Tensors.of( //
        Tensors.vectorLong(1, 2, 3), //
        Tensors.vectorLong(3, -1, -1));
    Tensor b = Tensors.vectorLong(3, 2, 2);
    Tensor c = Tensors.of( //
        Tensors.vectorLong(3, 4, 6), //
        Tensors.vectorLong(9, -2, -2));
    Tensor r = Tensor.of(a.flatten(0).map(row -> row.pmul(b)));
    assertEquals(r, c);
  }

  public void testVectorMatrix() {
    Tensor a = Tensors.of( //
        Tensors.vectorLong(new long[] { 1, 2, 3 }), //
        Tensors.fromString("{3,-1,-1}"));
    Tensor b = Tensors.vectorLong(3, 2);
    Tensor c = Tensors.of( //
        Tensors.vectorLong(3, 6, 9), //
        Tensors.vectorLong(6, -2, -2));
    assertEquals(b.pmul(a), c);
  }

  public void testMatrixMatrix() {
    Tensor a = Tensors.of( //
        Tensors.vectorLong(1, 2, 3), //
        Tensors.vectorLong(3, -1, -1));
    Tensor c = Tensors.of( //
        Tensors.vectorLong(3, 4, 6), //
        Tensors.vectorLong(-9, -2, -2));
    Tensor r = Tensors.fromString("{{3, 8, 18}, {-27, 2, 2}}");
    assertEquals(a.pmul(c), r);
  }

  public void testFail() {
    try {
      Tensors.vector(1, 2, 3).pmul(Tensors.vector(1, 2, 3, 4));
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }
}
