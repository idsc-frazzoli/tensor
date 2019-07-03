// code by jph
package ch.ethz.idsc.tensor.alg;

import ch.ethz.idsc.tensor.DoubleScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.mat.HilbertMatrix;
import ch.ethz.idsc.tensor.opt.TensorUnaryOperator;
import ch.ethz.idsc.tensor.red.Norm;
import junit.framework.TestCase;

public class NormalizeFailTest extends TestCase {
  public void testEmpty() {
    try {
      Normalize.with(Norm._2::ofVector).apply(Tensors.empty());
      fail();
    } catch (Exception exception) {
      // ---
    }
  }

  public void testZeros() {
    try {
      Normalize.with(Norm._2::ofVector).apply(Array.zeros(10));
      fail();
    } catch (Exception exception) {
      // ---
    }
  }

  public void testFail1() {
    TensorUnaryOperator normalize = Normalize.with(Norm._1::ofVector);
    try {
      normalize.apply(Tensors.vector(0, 0, 0, 0));
      fail();
    } catch (Exception exception) {
      // ---
    }
  }

  public void testNormalizePositiveInfinity() {
    Tensor vector = Tensors.of(DoubleScalar.POSITIVE_INFINITY, RealScalar.ONE);
    try {
      Normalize.with(Norm._2::ofVector).apply(vector);
      fail();
    } catch (Exception exception) {
      // ---
    }
    try {
      NormalizeUnlessZero.with(Norm._2::ofVector).apply(vector);
      fail();
    } catch (Exception exception) {
      // ---
    }
  }

  public void testNormalizeNegativeInfinity() {
    Tensor vector = Tensors.of(DoubleScalar.NEGATIVE_INFINITY, RealScalar.ONE, DoubleScalar.POSITIVE_INFINITY);
    try {
      Normalize.with(Norm._2::ofVector).apply(vector);
      fail();
    } catch (Exception exception) {
      // ---
    }
  }

  public void testNormalizeNaN() {
    Tensor vector = Tensors.of(RealScalar.ONE, DoubleScalar.INDETERMINATE, RealScalar.ONE);
    try {
      Normalize.with(Norm._2::ofVector).apply(vector);
      fail();
    } catch (Exception exception) {
      // ---
    }
  }

  public void testScalarFail() {
    try {
      Normalize.with(Norm._2::ofVector).apply(RealScalar.ONE);
      fail();
    } catch (Exception exception) {
      // ---
    }
  }

  public void testMatrixFail() {
    try {
      Normalize.with(Norm._2::ofVector).apply(Tensors.fromString("{{1, 2}, {3, 4, 5}}"));
      fail();
    } catch (Exception exception) {
      // ---
    }
    try {
      Normalize.with(Norm._2::ofVector).apply(HilbertMatrix.of(3));
      fail();
    } catch (Exception exception) {
      // ---
    }
  }
}
