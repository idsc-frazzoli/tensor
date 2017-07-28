// code by jph
package ch.ethz.idsc.tensor.mat;

import java.util.Arrays;

import ch.ethz.idsc.tensor.RationalScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.alg.Array;
import ch.ethz.idsc.tensor.alg.Dimensions;
import ch.ethz.idsc.tensor.alg.Normalize;
import ch.ethz.idsc.tensor.alg.Reverse;
import ch.ethz.idsc.tensor.lie.LieAlgebras;
import ch.ethz.idsc.tensor.sca.Chop;
import ch.ethz.idsc.tensor.sca.N;
import junit.framework.TestCase;

public class NullSpaceTest extends TestCase {
  private static void _checkZeros(Tensor zeros) {
    int n = zeros.length();
    Tensor nul = NullSpace.usingSvd(zeros);
    assertEquals(Dimensions.of(nul), Arrays.asList(n, n));
    assertTrue(nul.get(0, 0) instanceof RationalScalar);
    assertEquals(nul, IdentityMatrix.of(n));
  }

  public void testZerosUsingSvd() {
    for (int n = 1; n < 10; ++n) {
      _checkZeros(Array.zeros(n, n));
      _checkZeros(N.of(Array.zeros(n, n)));
    }
  }

  public void testRowReduce() {
    Tensor m = Tensors.fromString("{{1, 2, 3, 4}, {5, 6, 7, 8}, {9, 10, 11, 12}, {13, 14, 15, 16}}");
    Tensor r = NullSpace.of(m);
    // System.out.println(r);
    // assertEquals(r, Tensors.fromString("{{1, 0, -3, 2}, {0, 1, -2, 1}}"));
    for (Tensor v : r)
      assertEquals(m.dot(v), Array.zeros(4));
    assertEquals(Dimensions.of(r), Arrays.asList(2, 4));
    assertFalse(StaticHelper.anyMachineNumberQ(r));
  }

  public void testZeros2() {
    Tensor m = Array.zeros(5, 5);
    Tensor r = NullSpace.of(m);
    assertEquals(r, IdentityMatrix.of(5));
    assertFalse(StaticHelper.anyMachineNumberQ(r));
  }

  public void testIdentity() {
    Tensor m = IdentityMatrix.of(5);
    Tensor r = NullSpace.of(m);
    assertEquals(r, Tensors.empty());
    assertFalse(StaticHelper.anyMachineNumberQ(r));
  }

  public void testIdentityReversed() {
    Tensor m = Reverse.of(IdentityMatrix.of(5));
    Tensor r = NullSpace.of(m);
    assertEquals(r, Tensors.empty());
    assertFalse(StaticHelper.anyMachineNumberQ(r));
  }

  public void testWikipediaKernel() {
    Tensor A = Tensors.matrix(new Number[][] { //
        { 1, 0, -3, 0, 2, -8 }, //
        { 0, 1, 5, 0, -1, 4 }, //
        { 0, 0, 0, 1, 7, -9 }, //
        { 0, 0, 0, 0, 0, 0 } //
    });
    Tensor nul = NullSpace.of(A);
    for (Tensor v : nul)
      assertEquals(A.dot(v), Array.zeros(4));
    assertEquals(Dimensions.of(nul), Arrays.asList(3, 6));
    assertFalse(StaticHelper.anyMachineNumberQ(nul));
  }

  public void testSome1() {
    Tensor A = Tensors.matrix(new Number[][] { //
        { -1, -2, -1 }, //
        { -3, 1, 5 }, //
        { 3, 6, 3 }, //
        { 1, 2, 1 } //
    });
    Tensor nul = NullSpace.of(A);
    // System.out.println(nul);
    for (Tensor v : nul)
      assertEquals(A.dot(v), Array.zeros(4));
    assertEquals(Dimensions.of(nul), Arrays.asList(1, 3));
    assertFalse(StaticHelper.anyMachineNumberQ(nul));
  }

  public void testSome2() {
    Tensor A = Tensors.matrix(new Number[][] { //
        { 1, 0, -3, 0, 2, -8 }, //
        { 0, 0, 1, 0, -1, 4 }, //
        { 0, 0, 0, 1, 7, -9 }, //
        { 0, 0, 0, 0, 0, 0 } //
    });
    Tensor nul = NullSpace.of(A);
    for (Tensor v : nul)
      assertEquals(A.dot(v), Array.zeros(4));
    assertEquals(Dimensions.of(nul), Arrays.asList(3, 6));
    assertFalse(StaticHelper.anyMachineNumberQ(nul));
  }

  public void testSome3() {
    Tensor A = Tensors.matrix(new Number[][] { //
        { 0, 0, 0, 0, 0, 0 }, //
        { 0, 0, 1, 0, -1, 4 }, //
        { 0, 0, 0, 0, 1, -9 }, //
        { 1, 9, -3, 1, 2, -8 } //
    });
    Tensor nul = NullSpace.of(A);
    for (Tensor v : nul)
      assertEquals(A.dot(v), Array.zeros(4));
    assertEquals(Dimensions.of(nul), Arrays.asList(3, 6));
    assertFalse(StaticHelper.anyMachineNumberQ(nul));
  }

  public void testComplex() {
    // {{17/101-32/101*I, 0, 1, -99/101+20/101*I},
    // {106/505-253/505*I, 1, 0, -89/101+19/101*I}}
    Tensor m = Tensors.fromString("{{1+3*I, 2, 3, 4+I}, {5, 6+I, 7, 8}}");
    Tensor nul = NullSpace.of(m);
    // {{1, 0, 17/13+32/13*I, -23/13-28/13*I},
    // {0, 1, -98/65+9/65*I, 37/65-16/65*I}}
    assertEquals(Dimensions.of(nul), Arrays.asList(2, 4));
    for (Tensor v : nul)
      assertEquals(m.dot(v), Array.zeros(2));
    assertFalse(StaticHelper.anyMachineNumberQ(nul));
    // System.out.println(Put.string(nul));
  }

  public void testMatsim() {
    Tensor matrix = Tensors.matrixDouble(new double[][] { //
        { 1.0, -0.2, -0.8 }, //
        { -0.2, 1.0, -0.8 }, //
        { -0.2, -0.8, 1.0 } });
    Tensor nullspace = NullSpace.of(matrix);
    assertEquals(Dimensions.of(nullspace), Arrays.asList(1, 3));
    assertTrue(Chop._12.allZero(nullspace.get(0).subtract(Normalize.of(Tensors.vector(1, 1, 1)))));
  }

  public void testIsNumeric() {
    assertTrue(StaticHelper.anyMachineNumberQ(Tensors.vector(1, 1, 1.)));
    assertFalse(StaticHelper.anyMachineNumberQ(Tensors.vector(1, 1, 1)));
  }

  public void testFail() {
    try {
      NullSpace.of(RealScalar.ONE);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
    try {
      NullSpace.of(Tensors.vector(1, 2, 3, 1));
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
    try {
      NullSpace.of(LieAlgebras.sl3());
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }
}
