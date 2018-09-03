// code by jph
package ch.ethz.idsc.tensor.mat;

import ch.ethz.idsc.tensor.ComplexScalar;
import ch.ethz.idsc.tensor.RationalScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.alg.Array;
import ch.ethz.idsc.tensor.sca.Chop;
import junit.framework.TestCase;

public class LeastSquaresTest extends TestCase {
  public void testEasy() {
    Tensor m = Tensors.matrix( //
        (i, j) -> i.equals(j) ? RationalScalar.of(1, 1) : RealScalar.ZERO, 4, 3);
    assertEquals(MatrixRank.of(m), 3);
    Tensor b = Tensors.vector(1, 1, 1, 1);
    Tensor x1 = LeastSquares.of(m, b);
    assertEquals(x1, Tensors.vector(1, 1, 1));
    // Tensor x2 = LeastSquares.usingSvd(m, b);
    // assertEquals(x1, x2);
  }

  public void testFullRank() {
    Tensor m = Tensors.matrix( //
        (i, j) -> RationalScalar.of(2 * i + 2 + j, 1 + 9 * i + j), 4, 3);
    Tensor b = Tensors.vector(1, 1, 1, 1);
    Tensor x1 = LeastSquares.of(m, b);
    Tensor x2 = LeastSquares.usingSvd(m, b);
    Tensor d1 = x1.subtract(x2).map(Chop._10);
    assertEquals(d1, Array.zeros(3));
  }

  public void testLowRank() {
    Tensor m = Tensors.matrix( //
        (i, j) -> RationalScalar.of(2 * i + j, 9 + j), 4, 3);
    assertEquals(MatrixRank.of(m), 2);
    Tensor b = Tensors.vector(1, 1, 1, 1);
    Tensor x2 = LeastSquares.usingSvd(m, b);
    assertEquals(Chop._12.of(m.dot(x2).subtract(b)), b.multiply(RealScalar.ZERO));
  }

  public void testFullRankComplex() {
    Tensor m = Tensors.matrix( //
        (i, j) -> ComplexScalar.of( //
            RealScalar.of(i), RationalScalar.of(2 * i + 2 + j, 1 + 9 * i + j)),
        4, 3);
    assertEquals(MatrixRank.of(m), 3);
    Tensor b = Tensors.vector(1, 1, 1, 1);
    Tensor x1 = LeastSquares.of(m, b);
    Tensor d1 = m.dot(x1).subtract(b);
    Tensor s1 = Tensors.fromString(
        "{726086997/5793115330933+44069346/5793115330933*I, 67991764500/5793115330933+4521379500/5793115330933*I,-22367102670/827587904419-1672185060/827587904419*I, 11662258824/827587904419+1019978082/827587904419*I}");
    assertEquals(d1, s1);
  }

  public void testFullRankComplex2() {
    Tensor m = Tensors.matrix( //
        (i, j) -> ComplexScalar.of( //
            RealScalar.of(18 * i + j * 100), RationalScalar.of(2 * i + 2 + j, 1 + 9 * i + j)),
        4, 3);
    Tensor b = Tensors.fromString("{2+3*I, 1-8*I, 99-100*I, 2/5}");
    Tensor x1 = LeastSquares.of(m, b);
    @SuppressWarnings("unused")
    Tensor d1 = m.dot(x1).subtract(b);
    // 112.45647858810342
    // 112.45619362133209
    // 114.01661102428717
    // 114.00823633112584
    // 81.36251932398088
    // 81.3565431234251
    // System.out.println(Norm._2.of(d1));
    // System.out.println(d1);
    // Tensor s1 = Tensors.fromString(
    // "[726086997/5793115330933+44069346/5793115330933*I, 67991764500/5793115330933+4521379500/5793115330933*I,
    // -22367102670/827587904419-1672185060/827587904419*I, 11662258824/827587904419+1019978082/827587904419*I]");
    // assertEquals(d1, s1);
  }
}
