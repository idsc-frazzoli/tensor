// code by jph
package ch.ethz.idsc.tensor.mat;

import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.alg.Array;
import ch.ethz.idsc.tensor.alg.Transpose;
import junit.framework.TestCase;

public class MatrixRankTest extends TestCase {
  public void testRank() {
    assertEquals(MatrixRank.usingRowReduce(Tensors.of(Tensors.vector(0, 0, 0))), 0);
    assertEquals(MatrixRank.usingRowReduce(Tensors.of(Tensors.vector(0, 1, 0))), 1);
    assertEquals(MatrixRank.usingRowReduce(Tensors.of( //
        Tensors.vector(0, 1, 0), Tensors.vector(0, 1, 0))), 1);
    assertEquals(MatrixRank.usingRowReduce(Tensors.of( //
        Tensors.vector(0, 1, 0), Tensors.vector(0, 1, 1))), 2);
    assertEquals(MatrixRank.of(Tensors.of( //
        Tensors.vector(0, 1, 0), Tensors.vector(0, 1, 1))), 2);
  }

  public void testNumeric() {
    Tensor m = Tensors.of( //
        Tensors.vector(0, 1, 0), Tensors.vector(0, 1, 1e-40));
    assertEquals(MatrixRank.usingRowReduce(m), 2);
    assertEquals(MatrixRank.usingSvd(m), 1);
    assertEquals(MatrixRank.of(m), 1); // <- numeric
  }

  public void testNumeric2() {
    Tensor m = Transpose.of(Tensors.of( //
        Tensors.vector(0, 1, 0), Tensors.vector(0, 1, 1e-40)));
    assertEquals(MatrixRank.usingRowReduce(m), 2);
    assertEquals(MatrixRank.usingSvd(m), 1);
    assertEquals(MatrixRank.of(m), 1); // <- numeric
  }

  public void testNumeric3() {
    Tensor m = Tensors.fromString("{{0,1.0,0},{0,1,1/1000000000000000000000000000000000000}}");
    assertEquals(MatrixRank.usingRowReduce(m), 2);
    assertEquals(MatrixRank.usingSvd(m), 1);
    assertEquals(MatrixRank.of(m), 1); // <- numeric
  }

  public void testExact() {
    Tensor m = Tensors.fromString("{{0,1,0},{0,1,1/1000000000000000000000000000000000000}}");
    assertEquals(MatrixRank.usingRowReduce(m), 2);
    assertEquals(MatrixRank.usingSvd(m), 1); // <- numeric
    assertEquals(MatrixRank.of(m), 2); // <- exact
  }

  public void testZeros() {
    Tensor m = Array.zeros(9, 5);
    SingularValueDecomposition svd = SingularValueDecomposition.of(m);
    int rank = MatrixRank.of(svd);
    assertEquals(rank, 0);
  }
}
