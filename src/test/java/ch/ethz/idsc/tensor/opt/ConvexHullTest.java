// code by jph
package ch.ethz.idsc.tensor.opt;

import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.lie.LieAlgebras;
import ch.ethz.idsc.tensor.pdf.Distribution;
import ch.ethz.idsc.tensor.pdf.RandomVariate;
import ch.ethz.idsc.tensor.pdf.UniformDistribution;
import junit.framework.TestCase;

public class ConvexHullTest extends TestCase {
  public void testEmpty() {
    assertEquals(ConvexHull.of(Tensors.empty()), Tensors.empty());
  }

  public void testSingle() {
    Tensor v = Tensors.of(Tensors.vector(-1.3, 2.5));
    assertEquals(ConvexHull.of(v), v);
  }

  public void testDuo() {
    Tensor v = Tensors.of( //
        Tensors.vector(-1.3, 2.5), //
        Tensors.vector(1, 10) //
    );
    Tensor hul = ConvexHull.of(v);
    // System.out.println(hul);
    assertEquals(hul, v);
  }

  public void testSimple() {
    Tensor points = Tensors.matrix(new Number[][] { //
        { -1, 2 }, //
        { 1, -1 }, //
        { 3, -1 }, //
        { 1, 1 }, //
        { 2, 2 }, //
        { 2, 2 }, //
        { 2, 2 }, //
        { 0, -1 }, //
        { 0, -1 }, //
        { 2, -1 }, //
        { 2, -1 }, //
        { 2, 2 }, //
        { 2, 2 }, //
        { 2, 2 } }); //
    Tensor actual = ConvexHull.of(points.unmodifiable());
    Tensor expected = Tensors.fromString("{{0, -1}, {3, -1}, {2, 2}, {-1, 2}}");
    assertEquals(expected, actual);
  }

  public void testFail() {
    Distribution distribution = UniformDistribution.unit();
    try {
      ConvexHull.of(RandomVariate.of(distribution, 5, 2, 3));
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
    try {
      ConvexHull.of(LieAlgebras.sl3());
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
    try {
      ConvexHull.of(Tensors.fromString("{{{1},2}}"));
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
    try {
      ConvexHull.of(Tensors.fromString("{{2,3},{{1},2}}"));
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }
}
