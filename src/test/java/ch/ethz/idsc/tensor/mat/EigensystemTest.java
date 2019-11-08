// code by jph
package ch.ethz.idsc.tensor.mat;

import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.alg.Transpose;
import ch.ethz.idsc.tensor.pdf.Distribution;
import ch.ethz.idsc.tensor.pdf.RandomVariate;
import ch.ethz.idsc.tensor.pdf.UniformDistribution;
import ch.ethz.idsc.tensor.qty.Quantity;
import ch.ethz.idsc.tensor.sca.Chop;
import ch.ethz.idsc.tensor.sca.N;
import junit.framework.TestCase;

public class EigensystemTest extends TestCase {
  public void testDecomposition() {
    Distribution distribution = UniformDistribution.of(-2, 2);
    Tensor matrix = RandomVariate.of(distribution, 3, 3);
    Tensor tensor = Transpose.of(matrix).add(matrix);
    Eigensystem eigensystem = Eigensystem.ofSymmetric(tensor);
    Tensor a = eigensystem.vectors();
    Tensor values = eigensystem.values();
    Chop._12.requireClose(tensor, Inverse.of(a).dot(values.pmul(a)));
    Chop._12.requireClose(tensor, LinearSolve.of(a, values.pmul(a)));
  }

  public void testQuantity() {
    Tensor matrix = Tensors.fromString("{{10[m], -2[m]}, {-2[m], 4[m]}}");
    assertTrue(SymmetricMatrixQ.of(matrix));
    {
      Eigensystem eigensystem = Eigensystem.ofSymmetric(matrix);
      assertTrue(eigensystem.values().Get(0) instanceof Quantity);
      assertTrue(eigensystem.values().Get(1) instanceof Quantity);
    }
    {
      Eigensystem eigensystem = Eigensystem.ofSymmetric(N.DOUBLE.of(matrix));
      assertTrue(eigensystem.values().Get(0) instanceof Quantity);
      assertTrue(eigensystem.values().Get(1) instanceof Quantity);
    }
  }

  public void testQuantityMixed() {
    Tensor matrix = Tensors.fromString("{{10[m^2], 2[m*kg]}, {2[m*kg], 4[kg^2]}}");
    assertTrue(SymmetricMatrixQ.of(matrix));
    try {
      Eigensystem.ofSymmetric(matrix);
      fail();
    } catch (Exception exception) {
      // ---
    }
  }

  public void testEmptyFail() {
    try {
      Eigensystem.ofSymmetric(Tensors.empty());
      fail();
    } catch (Exception exception) {
      // ---
    }
  }

  public void testNonSymmetricFail() {
    try {
      Eigensystem.ofSymmetric(Tensors.fromString("{{1, 2}, {3, 4}}"));
      fail();
    } catch (Exception exception) {
      // ---
    }
  }
}
