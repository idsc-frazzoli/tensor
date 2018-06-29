// code by jph
package ch.ethz.idsc.tensor;

import java.util.Arrays;

import ch.ethz.idsc.tensor.mat.HilbertMatrix;
import ch.ethz.idsc.tensor.mat.SymmetricMatrixQ;
import junit.framework.TestCase;

public class AbstractScalarTest extends TestCase {
  public void testMap() {
    Tensor c = Tensors.fromString("{{1}, {4}, {4}}");
    Tensor a = Tensors.vector(1, 2, 3);
    Tensor b = Tensors.vector(4).unmodifiable();
    a = a.map(s -> b);
    a.set(RealScalar.ONE, 0, 0); // requires copy() in AbstractScalar::map
    assertEquals(a, c);
  }

  public void testSet() {
    Tensor a = Tensors.vector(1, 2, 3);
    Tensor b = Tensors.vector(4).unmodifiable();
    a.set(s -> b, Tensor.ALL);
    a.set(RealScalar.ONE, 0, 0);
    Tensor c = Tensors.fromString("{{1}, {4}, {4}}");
    assertEquals(a, c);
  }

  public void testSetAll() {
    Tensor matrix = HilbertMatrix.of(5);
    matrix.set(Tensor::negate, Tensor.ALL, 1);
    matrix.set(Tensor::negate, 3, Tensor.ALL);
    matrix.set(Tensor::negate, 1);
    matrix.set(Tensor::negate, Tensor.ALL, 3);
    assertTrue(SymmetricMatrixQ.of(matrix));
  }

  public void testGetFail() {
    RealScalar.ONE.get();
    RealScalar.ONE.Get();
    try {
      RealScalar.ONE.Get(1);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
    try {
      RealScalar.ONE.get(Arrays.asList(1));
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }

  public void testSetFail() {
    try {
      RealScalar.ONE.set(RealScalar.ZERO);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
    try {
      RealScalar.ONE.set(s -> RealScalar.ZERO);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }

  public void testAppendFail() {
    try {
      RealScalar.ONE.append(RealScalar.ZERO);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }

  public void testExtractFail() {
    try {
      RealScalar.ONE.extract(1, 2);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }

  public void testBlockFail() {
    try {
      RealScalar.ONE.block(Arrays.asList(1), Arrays.asList(1));
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }
}
