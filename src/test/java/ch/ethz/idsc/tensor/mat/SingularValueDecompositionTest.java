// code by jph
package ch.ethz.idsc.tensor.mat;

import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

import ch.ethz.idsc.tensor.DoubleScalar;
import ch.ethz.idsc.tensor.RationalScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.alg.Array;
import ch.ethz.idsc.tensor.alg.Dimensions;
import ch.ethz.idsc.tensor.alg.Sort;
import ch.ethz.idsc.tensor.alg.Transpose;
import ch.ethz.idsc.tensor.lie.LieAlgebras;
import ch.ethz.idsc.tensor.sca.Chop;
import junit.framework.TestCase;

public class SingularValueDecompositionTest extends TestCase {
  static SingularValueDecomposition specialOps(Tensor A) {
    SingularValueDecomposition svd = SingularValueDecomposition.of(A);
    // System.out.println(svd.toInfoString());
    List<Integer> dims = Dimensions.of(A);
    // System.out.println("A " + dims);
    // final int M = dims.get(0);
    final int N = dims.get(1);
    final Tensor U = svd.getU();
    assertEquals(dims, Dimensions.of(U));
    final Tensor w = svd.values();
    // System.out.println("w " + w.dimensions());
    final Tensor V = svd.getV();
    // System.out.println("V " + V.dimensions());
    Tensor W = DiagonalMatrix.of(w);
    // System.out.println("UtU");
    Tensor UtU = Chop._12.of(Transpose.of(U).dot(U).subtract(IdentityMatrix.of(N)));
    assertEquals(UtU, Array.zeros(N, N));
    // System.out.println("VVt");
    Tensor VVt = Chop._12.of(V.dot(Transpose.of(V)).subtract(IdentityMatrix.of(N)));
    assertEquals(VVt, Array.zeros(N, N));
    // System.out.println("VtV");
    Tensor VtV = Chop._12.of(Transpose.of(V).dot(V).subtract(IdentityMatrix.of(N)));
    assertEquals(VtV, Array.zeros(N, N));
    // System.out.println("UWVt");
    Tensor UWVt = Chop._12.of(U.dot(W).dot(Transpose.of(V)).subtract(A));
    assertEquals(UWVt, Array.zeros(Dimensions.of(UWVt)));
    // System.out.println("UW_AV");
    Tensor UW_AV = Chop._12.of(U.dot(W).subtract(A.dot(V)));
    assertEquals(UW_AV, Array.zeros(Dimensions.of(UW_AV)));
    // System.out.println("AiA");
    // Tensor AiA = svd.pseudoInverse().dot(A).map(Scalars::chop);
    // System.out.println(AiA);
    // assertEquals(AiA, TensorBuild.zeros(AiA.dimensions()));
    // System.out.println(svd.toInfoString());
    assertFalse(w.flatten(-1).map(Scalar.class::cast) //
        .anyMatch(s -> s.number().doubleValue() < 0));
    if (MatrixRank.of(svd) < N) {
      Tensor res = A.dot(Transpose.of(NullSpace.of(svd)));
      assertEquals(Chop._12.of(res), Array.zeros(Dimensions.of(res)));
    }
    return svd;
  }

  public void testSvd1() {
    Random rnd = new Random();
    Tensor mat = Tensors.matrix((r, c) -> DoubleScalar.of(rnd.nextGaussian()), 8, 5);
    SingularValueDecomposition svd = specialOps(mat);
    assertEquals(MatrixRank.of(svd), 5);
  }

  public void testSvd2() {
    Random rnd = new Random();
    Tensor mat = Tensors.matrix((r, c) -> DoubleScalar.of(rnd.nextGaussian()), 10, 3);
    Tensor B = Tensors.matrixLong(new long[][] { //
        { 1, 2, 3 }, { 0, 0, 4 }, { 0, 0, 0 } });
    SingularValueDecomposition svd = specialOps(mat.dot(B));
    assertEquals(MatrixRank.of(svd), 2);
    specialOps(svd.getU());
    specialOps(svd.getV());
    specialOps(Transpose.of(PseudoInverse.of(svd)));
    specialOps(Transpose.of(NullSpace.of(svd)));
  }

  public void testSvd3() {
    Random rnd = new Random();
    Tensor mat = Tensors.matrix((r, c) -> DoubleScalar.of(rnd.nextGaussian()), 20, 4);
    Tensor B = Tensors.matrixLong(new long[][] { //
        { 1, 2, 3, -1 }, { 0, 0, 4, 2 }, { 0, 0, 0, 1 }, { 0, 0, 0, 0 } });
    Tensor A = mat.dot(B);
    SingularValueDecomposition svd = specialOps(A);
    assertEquals(MatrixRank.of(svd), 3);
    specialOps(svd.getU());
    specialOps(svd.getV());
    specialOps(Transpose.of(PseudoInverse.of(svd)));
    specialOps(Transpose.of(NullSpace.of(svd)));
  }

  public void testSvdNull() {
    int n = 20;
    Random rnd = new Random();
    Tensor mat = Tensors.matrix((r, c) -> DoubleScalar.of(rnd.nextGaussian()), n, 4);
    Tensor B = Tensors.matrixLong(new long[][] { //
        { 1, 2, 3, -1 }, { 0, 0, 4, 2 }, { 0, 0, 0, 1 }, { 0, 0, 0, 0 } });
    Tensor A = mat.dot(B);
    SingularValueDecomposition svd = specialOps(A);
    assertEquals(MatrixRank.of(svd), 3);
    Tensor nls = NullSpace.of(svd);
    Tensor nul = A.dot(nls.get(0));
    assertEquals(Chop._12.of(nul), Array.zeros(n));
  }

  // test may occasionally fail, depends on the numerical precision using in Chop
  public void testSvd4() {
    int n = 11;
    Random rnd = new Random();
    Tensor mat = Tensors.matrix((r, c) -> DoubleScalar.of(rnd.nextGaussian()), n, n);
    SingularValueDecomposition svd = specialOps(mat);
    Tensor dif = PseudoInverse.of(svd).subtract(Inverse.of(mat)).map(Chop._09);
    assertEquals(dif, Array.zeros(Dimensions.of(dif)));
    assertEquals(MatrixRank.of(svd), n);
    Tensor res = Chop._12.of(PseudoInverse.of(svd).dot(mat).subtract(IdentityMatrix.of(n)));
    assertEquals(res, Array.zeros(n, n));
  }

  public void testSvdR1() {
    Random rnd = new Random();
    Tensor mat = Tensors.matrix((r, c) -> RationalScalar.of(rnd.nextInt(100) - 50, rnd.nextInt(100) + 1), 15, 15);
    SingularValueDecomposition svd = specialOps(mat);
    Tensor dif = PseudoInverse.of(svd).subtract(Inverse.of(mat)).map(Chop._08);
    assertEquals(dif, Array.zeros(Dimensions.of(dif)));
  }

  public void testSvdR2() {
    Random rnd = new Random();
    Tensor mat = Tensors.matrix((r, c) -> RationalScalar.of(rnd.nextInt(100) - 50, rnd.nextInt(100) + 1), 20, 4);
    Tensor B = Tensors.matrix(new Scalar[][] { //
        // "{1,2,3,-1}"
        { RationalScalar.of(1, 1), RationalScalar.of(2, 1), RationalScalar.of(3, 1), RationalScalar.of(-1, 1) }, //
        { RationalScalar.of(0, 1), RationalScalar.of(0, 1), RationalScalar.of(4, 1), RationalScalar.of(2, 1) }, //
        { RationalScalar.of(0, 1), RationalScalar.of(0, 1), RationalScalar.of(0, 1), RationalScalar.of(1, 1) }, //
        { RationalScalar.of(0, 1), RationalScalar.of(0, 1), RationalScalar.of(0, 1), RationalScalar.of(0, 1) } });
    SingularValueDecomposition svd = specialOps(mat.dot(B));
    assertEquals(MatrixRank.of(svd), 3);
  }

  public void testSo3() {
    Tensor ad = LieAlgebras.so3();
    Tensor sk = ad.dot(Tensors.vector(1, 1, 1));
    SingularValueDecomposition svd = specialOps(sk);
    assertEquals(MatrixRank.of(svd), 2);
  }

  public void testFullConstant() {
    Tensor d = Tensors.matrix((i, j) -> RealScalar.of(1e-10), 10, 10);
    SingularValueDecomposition svd = specialOps(d);
    assertEquals(MatrixRank.of(svd), 1);
  }

  public void testHilbert1() {
    Tensor d = HilbertMatrix.of(200, 20);
    SingularValueDecomposition svd = specialOps(d);
    assertTrue(13 <= MatrixRank.of(svd));
  }

  public void testHilbert2() {
    Tensor d = HilbertMatrix.of(100, 10);
    SingularValueDecomposition svd = specialOps(d);
    assertEquals(10, MatrixRank.of(svd));
  }

  public void testJordan1() {
    Tensor d = DiagonalMatrix.of(Tensors.vector(1e-10, 1, 1, 1, 1e-10));
    IntStream.range(0, 4).forEach(j -> d.set(RealScalar.of(1e-10), j, j + 1));
    SingularValueDecomposition svd = specialOps(d);
    assertEquals(MatrixRank.of(svd), 5);
  }

  public void testJordan2() {
    Tensor d = DiagonalMatrix.of(Tensors.vector(1, 1, 1, 1, 1));
    IntStream.range(0, 4).forEach(j -> d.set(RealScalar.of(1e-10), j + 1, j));
    specialOps(d);
  }

  public void testScdR3() {
    int n = 10;
    int k = 8;
    Tensor mat = Array.zeros(n, n);
    mat.set(RationalScalar.of(1, 1), k - 4, k - 1);
    mat.set(RationalScalar.of(1, 1), k - 1, k - 4);
    SingularValueDecomposition svd = specialOps(mat);
    assertEquals(MatrixRank.of(svd), 2);
    assertEquals(Sort.of(svd.values()), Tensors.fromString("{0, 0, 0, 0, 0, 0, 0, 0, 1.0, 1.0}"));
  }

  public void testEye() {
    assertEquals(MatrixRank.usingSvd(IdentityMatrix.of(10)), 10);
    assertEquals(MatrixRank.usingSvd(DiagonalMatrix.of(Tensors.vector(1, 1, 1, 1, 0, 0))), 4);
  }
}
