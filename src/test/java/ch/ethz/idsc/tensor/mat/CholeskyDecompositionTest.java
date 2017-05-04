// code by jph
package ch.ethz.idsc.tensor.mat;

import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.ZeroScalar;
import ch.ethz.idsc.tensor.alg.Array;
import ch.ethz.idsc.tensor.sca.Chop;
import ch.ethz.idsc.tensor.sca.N;
import junit.framework.TestCase;

public class CholeskyDecompositionTest extends TestCase {
  static CholeskyDecomposition checkDecomp(Tensor A) {
    int n = A.length();
    CholeskyDecomposition cd = CholeskyDecomposition.of(A);
    // System.out.println(Pretty.of(cd.getL()));
    // System.out.println(Pretty.of(cd.getD()));
    Tensor res = cd.getL().dot(cd.getD().pmul(ConjugateTranspose.of(cd.getL())));
    assertEquals(Chop.of(A.subtract(res)), Array.zeros(n, n));
    assertEquals(Chop.of(cd.det().subtract(Det.of(A))), ZeroScalar.get());
    return cd;
  }

  public void testRosetta1() {
    // +5 0 0
    // +3 3 0
    // -1 1 3
    // {{5, 3, -1}, {0, 3, 1}, {0, 0, 3}}
    checkDecomp(Tensors.matrix(new Number[][] { //
        { 25, 15, -5 }, //
        { 15, 18, 0 }, //
        { -5, 0, 11 } //
    }));
  }

  public void testWikiEn() {
    CholeskyDecomposition cd = //
        checkDecomp(Tensors.matrix(new Number[][] { //
            { 4, 12, -16 }, //
            { 12, 37, -43 }, //
            { -16, -43, 98 } //
        }));
    Tensor ltrue = Tensors.matrix(new Number[][] { //
        { 1, 0, 0 }, //
        { 3, 1, 0 }, //
        { -4, 5, 1 } //
    });
    assertEquals(cd.getL(), ltrue);
    assertEquals(cd.getD(), Tensors.vector(4, 1, 9));
  }

  public void testMathematica1() {
    checkDecomp(Tensors.matrix(new Number[][] { //
        { 2, 1 }, //
        { 1, 2 } //
    }));
  }

  public void testMathematica2() {
    checkDecomp(Tensors.fromString("{{4, 3, 2, 1}, {3, 4, 3, 2}, {2, 3, 4, 3}, {+1, 2, 3, 4}}"));
    checkDecomp(Tensors.fromString("{{4, 3, 2, I}, {3, 4, 3, 2}, {2, 3, 4, 3}, {-I, 2, 3, 4}}"));
    checkDecomp(Tensors.fromString("{{4, 3, 2, I}, {3, 4, 3, 2}, {2, 3, 4, 3}, {-I, 2, 3, 0}}"));
  }

  public void testFail1() {
    try {
      checkDecomp(Tensors.fromString("{{4, 2}, {1, 4}}"));
    } catch (Exception exception) {
      // ---
    }
  }

  public void testFail2() {
    try {
      checkDecomp(Tensors.fromString("{{4, I}, {I, 4}}"));
    } catch (Exception exception) {
      // ---
    }
  }

  public void testHilbert1() {
    checkDecomp(HilbertMatrix.of(10));
  }

  public void testHilbertN1() {
    checkDecomp(N.of(HilbertMatrix.of(16)));
  }

  public void testZeros1() {
    checkDecomp(Array.zeros(1, 1));
    checkDecomp(Array.zeros(5, 5));
  }
}
