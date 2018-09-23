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
    assertEquals(RamerDouglasPeucker.of(RealScalar.of(1)).apply(mat), mat);
  }

  public void testPoints1() {
    Tensor mat = Tensors.matrix(new Number[][] { { 1, 1 } });
    assertEquals(RamerDouglasPeucker.of(RealScalar.of(1)).apply(mat), mat);
  }

  public void testPoints2() {
    Tensor mat = Tensors.matrix(new Number[][] { { 1, 1 }, { 5, 2 } });
    assertEquals(RamerDouglasPeucker.of(RealScalar.of(1)).apply(mat), mat);
  }

  public void testPoints3() {
    Tensor mat = Tensors.matrix(new Number[][] { { 1, 1 }, { 3, 2 }, { 5, 2 } });
    assertEquals(RamerDouglasPeucker.of(RealScalar.of(1)).apply(mat), //
        Tensors.matrixInt(new int[][] { { 1, 1 }, { 5, 2 } }));
    assertEquals(RamerDouglasPeucker.of(RealScalar.of(.1)).apply(mat), mat);
  }

  public void testRandom() {
    int n = 20;
    Tensor mat = Tensors.vector(i -> Tensors.vector(i, RandomVariate.of(NormalDistribution.standard()).number().doubleValue()), n);
    Tensor res = RamerDouglasPeucker.of(RealScalar.of(1)).apply(mat);
    Tensor col = res.get(Tensor.ALL, 0);
    assertEquals(col, Sort.of(col));
    assertTrue(col.length() < n);
  }

  public void testEpsilonFail() {
    try {
      RamerDouglasPeucker.of(RealScalar.of(-.1));
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }

  public void testFail() {
    try {
      RamerDouglasPeucker.of(RealScalar.of(.1)).apply(Tensors.fromString("{{{1},2}}"));
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
    try {
      RamerDouglasPeucker.of(RealScalar.of(.1)).apply(LieAlgebras.sl2());
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
    try {
      RamerDouglasPeucker.of(RealScalar.of(.1)).apply(Array.zeros(3, 2, 4));
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
    try {
      RamerDouglasPeucker.of(RealScalar.of(.1)).apply(IdentityMatrix.of(3));
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }
}
