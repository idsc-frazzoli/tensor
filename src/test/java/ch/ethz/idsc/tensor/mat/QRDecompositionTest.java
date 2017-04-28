// code by jph
package ch.ethz.idsc.tensor.mat;

import java.util.Random;
import java.util.function.Function;

import ch.ethz.idsc.tensor.ComplexScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.ZeroScalar;
import ch.ethz.idsc.tensor.alg.Array;
import ch.ethz.idsc.tensor.alg.Dimensions;
import ch.ethz.idsc.tensor.sca.Chop;
import junit.framework.TestCase;

public class QRDecompositionTest extends TestCase {
  private static QRDecomposition specialOps(Tensor A) {
    Function<Scalar, Scalar> chop = Chop.below(1e-10);
    QRDecomposition qr = QRDecomposition.of(A);
    Tensor Q = qr.getQ();
    Tensor Qi = qr.getInverseQ();
    Tensor R = qr.getR();
    // System.out.println(Pretty.of(R));
    // System.out.println(Pretty.of(Q));
    assertEquals(A.subtract(Q.dot(R)).map(chop), Array.zeros(Dimensions.of(A)));
    Tensor err = Q.dot(Qi).subtract(IdentityMatrix.of(A.length()));
    // System.out.println(Pretty.of(Chop.of(err)));
    assertEquals(err.map(chop), Array.zeros(Dimensions.of(err)));
    Scalar qrDet = Det.of(Q).multiply(Det.of(R));
    assertEquals(qrDet.subtract(Det.of(A)).map(chop), ZeroScalar.get());
    return qr;
  }

  public void testExampleP32() {
    Tensor A = Tensors.matrix(new Number[][] { //
        { -1, -1, 1 }, //
        { 1, 3, 3 }, //
        { -1, -1, 5 }, //
        { 1, 3, 7 } });
    specialOps(A);
  }

  public void testRandomReal() {
    Random rnd = new Random();
    Tensor A = Tensors.matrix((i, j) -> RealScalar.of(rnd.nextDouble()), 5, 3);
    specialOps(A);
  }

  public void testRandomReal2() {
    Random rnd = new Random();
    Tensor A = Tensors.matrix((i, j) -> RealScalar.of(rnd.nextDouble()), 3, 5);
    specialOps(A);
  }

  public void testRandomReal5() {
    Random rnd = new Random();
    Tensor A = Tensors.matrix((i, j) -> RealScalar.of(rnd.nextDouble()), 5, 5);
    specialOps(A);
  }

  public void testDiag() {
    Tensor A = DiagonalMatrix.of(Tensors.vector(2, 3, 4));
    specialOps(A);
  }

  public void testDiag2() {
    Tensor A = DiagonalMatrix.of(2, -3, 0, 0, -1e-10, 0, 4e20);
    specialOps(A);
  }

  public void testZeros() {
    Tensor A = Array.zeros(4, 3);
    specialOps(A);
  }

  public void testRandomComplex1() {
    Random rnd = new Random();
    Tensor A = Tensors.matrix((i, j) -> ComplexScalar.of(rnd.nextGaussian(), rnd.nextGaussian()), 5, 3);
    specialOps(A);
  }

  public void testRandomComplex2() {
    Random rnd = new Random();
    Tensor A = Tensors.matrix((i, j) -> ComplexScalar.of(rnd.nextGaussian(), rnd.nextGaussian()), 5, 5);
    specialOps(A);
  }

  public void testHilbert() {
    Tensor matrix = HilbertMatrix.of(4, 7);
    specialOps(matrix);
    QRDecomposition qr = QRDecomposition.of(matrix);
    assertEquals(qr.getR().get(1, 0), ZeroScalar.get());
    assertEquals(qr.getR().get(2, 0), ZeroScalar.get());
    assertEquals(qr.getR().get(2, 1), ZeroScalar.get());
    assertEquals(qr.getR().get(3, 0), ZeroScalar.get());
    assertEquals(qr.getR().get(3, 1), ZeroScalar.get());
    assertEquals(qr.getR().get(3, 2), ZeroScalar.get());
  }
}
