// code by jph
package ch.ethz.idsc.tensor.red;

import ch.ethz.idsc.tensor.ComplexScalar;
import ch.ethz.idsc.tensor.RationalScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.alg.TensorMap;
import ch.ethz.idsc.tensor.mat.HilbertMatrix;
import ch.ethz.idsc.tensor.pdf.UniformDistribution;
import junit.framework.TestCase;

public class VarianceTest extends TestCase {
  public void testVariance() {
    Tensor A = Tensors.vector(1, 2, 5, 7);
    assertEquals(Mean.of(A), RationalScalar.of(15, 4));
    assertEquals(Variance.ofVector(A), RationalScalar.of(91, 12));
  }

  public void testVariance2() {
    Tensor A = Tensors.of( //
        Tensors.vector(1, 2, 5, 7), //
        Tensors.vector(1, 2, 5) //
    );
    Tensor b = TensorMap.of(Variance::ofVector, A, 1);
    Tensor c = Tensors.fromString("{91/12,13/3}");
    assertEquals(b, c);
  }

  public void testComplex() {
    Tensor vector = Tensors.of(ComplexScalar.of(1, 7), ComplexScalar.of(2, -3), ComplexScalar.of(3, 2));
    Tensor v = Variance.ofVector(vector);
    assertEquals(v, RealScalar.of(26));
  }

  public void testDistribution() {
    assertEquals(Variance.of(UniformDistribution.unit()), RationalScalar.of(1, 12));
  }

  public void testFailScalar() {
    try {
      Variance.ofVector(RealScalar.ONE);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }

  public void testFailLength() {
    try {
      Variance.ofVector(Tensors.empty());
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
    try {
      Variance.ofVector(Tensors.vector(3));
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }

  public void testFailMatrix() {
    try {
      Variance.ofVector(HilbertMatrix.of(5));
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }
}
