// code by jph
package ch.ethz.idsc.tensor.mat;

import java.util.Arrays;

import ch.ethz.idsc.tensor.ExactScalarQ;
import ch.ethz.idsc.tensor.MachineNumberQ;
import ch.ethz.idsc.tensor.RationalScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.alg.Array;
import ch.ethz.idsc.tensor.alg.Dimensions;
import ch.ethz.idsc.tensor.alg.Normalize;
import ch.ethz.idsc.tensor.alg.Reverse;
import ch.ethz.idsc.tensor.alg.Transpose;
import ch.ethz.idsc.tensor.lie.LieAlgebras;
import ch.ethz.idsc.tensor.qty.Quantity;
import ch.ethz.idsc.tensor.qty.QuantityTensor;
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
      _checkZeros(N.DOUBLE.of(Array.zeros(n, n)));
    }
  }

  public void testRowReduce() {
    Tensor m = Tensors.fromString("{{1, 2, 3, 4}, {5, 6, 7, 8}, {9, 10, 11, 12}, {13, 14, 15, 16}}");
    Tensor r = NullSpace.of(m);
    for (Tensor v : r)
      assertEquals(m.dot(v), Array.zeros(4));
    assertEquals(Dimensions.of(r), Arrays.asList(2, 4));
    assertFalse(MachineNumberQ.any(r));
    assertTrue(ExactScalarQ.all(r));
  }

  public void testZeros2() {
    Tensor m = Array.zeros(5, 5);
    Tensor r = NullSpace.of(m);
    assertEquals(r, IdentityMatrix.of(5));
    assertFalse(MachineNumberQ.any(r));
    assertTrue(ExactScalarQ.all(r));
  }

  public void testIdentity() {
    Tensor m = IdentityMatrix.of(5);
    Tensor r = NullSpace.of(m);
    assertEquals(r, Tensors.empty());
    assertFalse(MachineNumberQ.any(r));
    assertTrue(ExactScalarQ.all(r));
  }

  public void testIdentityReversed() {
    Tensor m = Reverse.of(IdentityMatrix.of(5));
    Tensor r = NullSpace.of(m);
    assertEquals(r, Tensors.empty());
    assertFalse(MachineNumberQ.any(r));
    assertTrue(ExactScalarQ.all(r));
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
    assertFalse(MachineNumberQ.any(nul));
    assertTrue(ExactScalarQ.all(nul));
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
    assertFalse(MachineNumberQ.any(nul));
    assertTrue(ExactScalarQ.all(nul));
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
    assertFalse(MachineNumberQ.any(nul));
    assertTrue(ExactScalarQ.all(nul));
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
    assertFalse(MachineNumberQ.any(nul));
    assertTrue(ExactScalarQ.all(nul));
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
    assertFalse(MachineNumberQ.any(nul));
    assertTrue(ExactScalarQ.all(nul));
  }

  public void testMatsim() {
    Tensor matrix = Tensors.matrixDouble(new double[][] { //
        { 1.0, -0.2, -0.8 }, //
        { -0.2, 1.0, -0.8 }, //
        { -0.2, -0.8, 1.0 } });
    Tensor nullspace = NullSpace.of(matrix);
    assertEquals(Dimensions.of(nullspace), Arrays.asList(1, 3));
    assertTrue(Chop._14.close(nullspace.get(0), Normalize.of(Tensors.vector(1, 1, 1))) //
        || Chop._14.close(nullspace.get(0), Normalize.of(Tensors.vector(-1, -1, -1))));
  }

  public void testQuantity() {
    Tensor mat = Tensors.of(QuantityTensor.of(Tensors.vector(1, 2), "m"));
    Tensor nul = NullSpace.of(mat);
    assertEquals(nul, Tensors.fromString("{{1, -1/2}}"));
    assertFalse(MachineNumberQ.any(nul));
    assertTrue(ExactScalarQ.all(nul));
  }

  public void testQuantityMixed() {
    Tensor mat = Tensors.of( //
        Tensors.of(Quantity.of(-2, "m"), Quantity.of(1, "kg"), Quantity.of(3, "s")));
    Tensor nul = NullSpace.of(mat);
    assertTrue(Chop.NONE.allZero(mat.dot(Transpose.of(nul))));
  }

  public void testQuantityMixed2() {
    Tensor mat = Tensors.of( //
        Tensors.of(Quantity.of(-2, "m"), Quantity.of(1, "kg"), Quantity.of(3, "s")), //
        Tensors.of(Quantity.of(-4, "m"), Quantity.of(2, "kg"), Quantity.of(6, "s")), //
        Tensors.of(Quantity.of(+1, "m"), Quantity.of(3, "kg"), Quantity.of(1, "s")) //
    );
    Tensor nul = NullSpace.of(mat);
    assertEquals(Dimensions.of(nul), Arrays.asList(1, 3));
    assertTrue(Chop.NONE.allZero(mat.dot(Transpose.of(nul))));
  }

  public void testQuantityNumeric() {
    // currently not supported
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
      NullSpace.of(LieAlgebras.sl2());
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }
}
