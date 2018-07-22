// code by jph
package ch.ethz.idsc.tensor.mat;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.lie.LieAlgebras;
import ch.ethz.idsc.tensor.lie.Rodrigues;
import ch.ethz.idsc.tensor.pdf.Distribution;
import ch.ethz.idsc.tensor.pdf.NormalDistribution;
import ch.ethz.idsc.tensor.pdf.RandomVariate;
import junit.framework.TestCase;

public class UnitaryMatrixQTest extends TestCase {
  public void testExample2d() {
    Tensor matrix = Tensors.fromString("{{1, I}, {I, 1}}").multiply(RealScalar.of(Math.sqrt(0.5)));
    assertTrue(UnitaryMatrixQ.of(matrix));
  }

  public void testExample3d() {
    Tensor matrix = Tensors.fromString("{{0.7071067811865476, 0.7071067811865476, 0.}, {-0.7071067811865476* I, 0.7071067811865476 *I, 0.}, {0., 0., I}}");
    assertTrue(UnitaryMatrixQ.of(matrix));
  }

  public void testFourier() {
    assertTrue(UnitaryMatrixQ.of(FourierMatrix.of(11)));
  }

  public void testRodriques() {
    Distribution dis = NormalDistribution.standard();
    for (int c = 0; c < 20; ++c) {
      Tensor matrix = Rodrigues.exp(RandomVariate.of(dis, 3));
      assertTrue(UnitaryMatrixQ.of(matrix));
    }
  }

  public void testOthers() {
    assertFalse(UnitaryMatrixQ.of(Tensors.fromString("{{1,2},{I,I}}")));
    assertFalse(UnitaryMatrixQ.of(RealScalar.of(3)));
    assertFalse(UnitaryMatrixQ.of(Tensors.vector(1, 2, 3)));
    assertFalse(UnitaryMatrixQ.of(LieAlgebras.so3()));
  }
}
