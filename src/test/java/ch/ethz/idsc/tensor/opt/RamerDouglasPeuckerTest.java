// code by jph
package ch.ethz.idsc.tensor.opt;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.alg.Array;
import ch.ethz.idsc.tensor.alg.Sort;
import ch.ethz.idsc.tensor.lie.LieAlgebras;
import ch.ethz.idsc.tensor.mat.IdentityMatrix;
import ch.ethz.idsc.tensor.pdf.NormalDistribution;
import ch.ethz.idsc.tensor.pdf.RandomVariate;
import junit.framework.TestCase;

public class RamerDouglasPeuckerTest extends TestCase {
  public void testEmpty() {
    Tensor mat = Tensors.empty();
    assertEquals(RamerDouglasPeucker.of(mat, RealScalar.of(1)), mat);
  }

  public void testPoints1() {
    Tensor mat = Tensors.matrix(new Number[][] { { 1, 1 } });
    assertEquals(RamerDouglasPeucker.of(mat, RealScalar.of(1)), mat);
  }

  public void testPoints2() {
    Tensor mat = Tensors.matrix(new Number[][] { { 1, 1 }, { 5, 2 } });
    assertEquals(RamerDouglasPeucker.of(mat, RealScalar.of(1)), mat);
  }

  public void testPoints3() {
    Tensor mat = Tensors.matrix(new Number[][] { { 1, 1 }, { 3, 2 }, { 5, 2 } });
    assertEquals(RamerDouglasPeucker.of(mat, RealScalar.of(1)), //
        Tensors.matrixInt(new int[][] { { 1, 1 }, { 5, 2 } }));
    assertEquals(RamerDouglasPeucker.of(mat, RealScalar.of(.1)), mat);
  }

  public void testRandom() {
    int n = 20;
    Tensor mat = Tensors.vector(i -> Tensors.vector(i, RandomVariate.of(NormalDistribution.standard()).number().doubleValue()), n);
    Tensor res = RamerDouglasPeucker.of(mat, RealScalar.of(1));
    Tensor col = res.get(Tensor.ALL, 0);
    assertEquals(col, Sort.of(col));
    assertTrue(col.length() < n);
  }

  public void testFail() {
    try {
      RamerDouglasPeucker.of(Tensors.fromString("{{{1},2}}"), RealScalar.of(.1));
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
    try {
      RamerDouglasPeucker.of(LieAlgebras.sl2(), RealScalar.of(.1));
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
    try {
      RamerDouglasPeucker.of(Array.zeros(3, 2, 4), RealScalar.of(.1));
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
    try {
      RamerDouglasPeucker.of(IdentityMatrix.of(3), RealScalar.of(.1));
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }
}
