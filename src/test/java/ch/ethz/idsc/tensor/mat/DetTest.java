// code by jph
package ch.ethz.idsc.tensor.mat;

import java.math.BigInteger;

import ch.ethz.idsc.tensor.ComplexScalar;
import ch.ethz.idsc.tensor.RationalScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Scalars;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.alg.Array;
import ch.ethz.idsc.tensor.alg.Reverse;
import ch.ethz.idsc.tensor.io.ResourceData;
import ch.ethz.idsc.tensor.lie.LieAlgebras;
import ch.ethz.idsc.tensor.sca.N;
import junit.framework.TestCase;

public class DetTest extends TestCase {
  public void testEmpty() {
    Tensor m = Tensors.matrix(new Number[][] { {} });
    // this is consistent with Mathematica
    // Mathematica throws an exception
    // System.out.println("here!");
    try {
      Det.of(m);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }

  public void testDet1() {
    Tensor m = Tensors.matrix(new Number[][] { //
        { +2, 3, 4 }, //
        { +0, 0, 1 }, //
        { -5, 3, 4 } });
    assertEquals(Det.of(m), RealScalar.of(-21));
  }

  public void testDet2() {
    Tensor m = Tensors.matrix(new Number[][] { //
        { -2, 3, 4 }, //
        { +0, 0, 1 }, //
        { -5, 3, 4 } });
    assertEquals(Det.of(m), RealScalar.of(-9));
  }

  public void testDet3() {
    Tensor m = Tensors.matrix(new Number[][] { //
        { -2, 3, +4 }, //
        { +0, 2, -1 }, //
        { -5, 3, +4 } });
    assertEquals(Det.of(m), RealScalar.of(33));
  }

  public void testId() {
    for (int n = 1; n < 10; ++n)
      assertEquals(Det.of(IdentityMatrix.of(n)), RealScalar.ONE);
  }

  public void testReversedId() {
    Tensor actual = Tensors.vector(0, 1, -1, -1, 1, 1, -1, -1, 1, 1, -1, -1, 1, 1, -1, -1, 1, 1);
    for (int n = 1; n < 10; ++n) {
      Tensor mat = Reverse.of(IdentityMatrix.of(n));
      Scalar det = Det.of(mat);
      assertEquals(det, actual.Get(n));
    }
  }

  public void testDet4() {
    Tensor m = Tensors.matrix(new Number[][] { //
        { -2, 3, +4, 0 }, //
        { +0, 2, -1, 2 }, //
        { -5, 3, +4, 1 }, //
        { +0, 2, -1, 0 } //
    });
    assertEquals(Det.of(m), RealScalar.of(-66));
    m.set(RealScalar.of(9), 3, 0);
    assertEquals(Det.of(m), RealScalar.of(33));
  }

  public void testNonSquare() {
    Tensor m = Tensors.matrix(new Number[][] { //
        { -2, 3, +4, 0 }, //
        { +0, 2, -1, 2 }, //
    });
    assertEquals(Det.of(m), RealScalar.of(0));
  }

  public void testNonSquare2() {
    Tensor m = Tensors.matrix(new Number[][] { //
        { -2, 3, +4 }, //
        { +0, 2, -1 }, //
        { -5, 3, +4 }, //
        { +0, 2, -1 } //
    });
    assertEquals(Det.of(m), RealScalar.of(0));
  }

  public void testComplex1() {
    Tensor re = Tensors.matrix(new Number[][] { //
        { 0, 0, 3 }, //
        { -2, 0, 0 }, //
        { -3, 0, 2 } //
    });
    Tensor im = Tensors.matrix(new Number[][] { //
        { 0, 0, -2 }, //
        { -1, 9, 0 }, //
        { 8, 0, 1 } //
    });
    Tensor m = re.add(im.multiply(ComplexScalar.I));
    assertEquals(Det.of(m), ComplexScalar.of(270, -63));
  }

  public void testComplex2() {
    Tensor re = Tensors.matrix(new Number[][] { //
        { 5, 0, 3 }, //
        { -2, 0, 0 }, //
        { -3, 0, 2 } //
    });
    Tensor im = Tensors.matrix(new Number[][] { //
        { -9, 0, -2 }, //
        { -1, 9, 0 }, //
        { 8, 0, 1 } //
    });
    Tensor m = re.add(im.multiply(ComplexScalar.I));
    assertEquals(Det.of(m), ComplexScalar.of(387, 108));
  }

  public void testComplex3() {
    Tensor re = Tensors.matrix(new Number[][] { //
        { 5, 0, 3 }, //
        { -2, 0, 0 }, //
        { -3, -4, 2 } //
    });
    Tensor im = Tensors.matrix(new Number[][] { //
        { -9, 0, -2 }, //
        { -1, 9, 0 }, //
        { 8, -2, 1 } //
    });
    Tensor m = re.add(im.multiply(ComplexScalar.I));
    assertEquals(Det.of(m), ComplexScalar.of(421, 120));
  }

  public void testSingular() {
    Tensor m = Array.zeros(5, 5);
    assertEquals(Det.of(m), RealScalar.ZERO);
  }

  // https://ch.mathworks.com/help/matlab/ref/det.html
  public void testMatlabEx() {
    Tensor matrix = ResourceData.of("/mat/det0-matlab.csv");
    Scalar det = Det.of(matrix);
    assertEquals(det, RealScalar.ZERO);
    // ---
    // Matlab gives num == 1.0597e+05 !
    // Mathematica gives num == 44934.8 !
    Scalar num1 = Det.of(N.DOUBLE.of(matrix)); // indeed, our algo is no different:
    // System.out.println(num1);
    // num == 105968.67122221774
    num1.toString(); // to eliminate warning
    Scalar num2 = Det.withoutAbs(N.DOUBLE.of(matrix)); // indeed, our algo is no different:
    // System.out.println(num2);
    // num == 105968.67122221774
    num2.toString(); // to eliminate warning
  }

  public void testHilbert() {
    Scalar det = Det.of(HilbertMatrix.of(8));
    assertEquals(det, RationalScalar.of( //
        BigInteger.ONE, new BigInteger("365356847125734485878112256000000")));
  }

  public void testHilbert2() {
    Scalar det = Det.withoutAbs(HilbertMatrix.of(8));
    assertEquals(det, Scalars.fromString("1/365356847125734485878112256000000"));
  }

  public void testFailMatrixQ() {
    Tensor table = Tensors.fromString("{{1,2,3},{4,5}}");
    try {
      Det.of(table);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }

  public void testFailRank3() {
    try {
      Det.of(LieAlgebras.sl2());
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }
}
