// code by jph
package ch.ethz.idsc.tensor.alg;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import junit.framework.TestCase;

public class ListCorrelateOperatorTest extends TestCase {
  public void testNarrow1() {
    Tensor kernel = Tensors.vector(2, 1, 3);
    Tensor tensor = Tensors.vector(4, 5);
    try {
      ListCorrelate.of(kernel, tensor);
      fail();
    } catch (Exception exception) {
      // ---
    }
  }

  public void testNarrow2() {
    Tensor kernel = Tensors.fromString("{{1, 2, 3}}");
    Tensor tensor = Tensors.fromString("{{1, 2}}");
    try {
      ListCorrelate.of(kernel, tensor);
      fail();
    } catch (Exception exception) {
      // ---
    }
  }

  public void testNarrow3() {
    Tensor kernel = Tensors.fromString("{{1, 2, 3}, {2, 3, 4}}");
    Tensor tensor = Tensors.fromString("{{1, 2, 3}}");
    try {
      ListCorrelate.of(kernel, tensor);
      fail();
    } catch (Exception exception) {
      // ---
    }
  }

  public void testScalarFail() {
    Tensor kernel = RealScalar.ZERO;
    Tensor tensor = RealScalar.ONE;
    try {
      ListCorrelate.of(kernel, tensor);
      fail();
    } catch (Exception exception) {
      // ---
    }
  }

  public void testRankFail() {
    Tensor kernel = Tensors.vector(1, -1);
    Tensor matrix = Tensors.matrixInt(new int[][] { //
        { 2, 1, 3, 0, 1 }, //
        { 0, 1, -1, 3, 3 } });
    try {
      ListCorrelate.of(kernel, matrix);
      fail();
    } catch (Exception exception) {
      // ---
    }
  }

  public void testNullFail() {
    try {
      ListCorrelate.with(null);
      fail();
    } catch (Exception exception) {
      // ---
    }
  }

  /***************************************************/
  public void testConvolveNullFail() {
    try {
      ListConvolve.with(null);
      fail();
    } catch (Exception exception) {
      // ---
    }
  }
}
