// code by jph
package ch.ethz.idsc.tensor.opt;

import java.util.stream.Stream;

import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.alg.Array;
import ch.ethz.idsc.tensor.alg.Join;
import ch.ethz.idsc.tensor.lie.LieAlgebras;
import ch.ethz.idsc.tensor.pdf.Distribution;
import ch.ethz.idsc.tensor.pdf.NormalDistribution;
import ch.ethz.idsc.tensor.pdf.RandomVariate;
import ch.ethz.idsc.tensor.pdf.UniformDistribution;
import ch.ethz.idsc.tensor.qty.Quantity;
import ch.ethz.idsc.tensor.sca.Chop;
import junit.framework.TestCase;

public class ConvexHullTest extends TestCase {
  public void testEmpty() {
    assertEquals(ConvexHull.of(Tensors.empty()), Tensors.empty());
  }

  public void testSingle() {
    Tensor v = Tensors.of(Tensors.vector(-1.3, 2.5));
    assertEquals(ConvexHull.of(v), v);
  }

  public void testSingleCopies() {
    Tensor vec = Tensors.vector(-1.3, 2.5);
    Tensor v = Array.of(l -> vec, 4);
    assertEquals(ConvexHull.of(v), Tensors.of(vec));
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

  public void testExample() {
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

  public void testQuantity() {
    Scalar qs1 = Quantity.of(1, "m");
    Scalar qs2 = Quantity.of(4, "m");
    Scalar qs3 = Quantity.of(2, "m");
    Scalar qs4 = Quantity.of(-3, "m");
    Tensor ve1 = Tensors.of(qs1, qs2);
    Tensor ve2 = Tensors.of(qs3, qs4);
    Tensor mat = Tensors.of(ve2, ve1);
    Tensor hul = ConvexHull.of(mat);
    assertEquals(hul, mat);
  }

  public void testChallenge() {
    double variance = 1e-10;
    Tensor cube = Tensors.fromString("{{0, 0}, {1, 0}, {1, 1}, {0, 1}}");
    Distribution distribution = NormalDistribution.of(0.5, variance);
    Tensor join = Join.of(cube, RandomVariate.of(distribution, 300, 2));
    Tensor hull = ConvexHull.of(join);
    assertEquals(hull, cube);
  }

  // this issue appeared first in owly
  // due to the introduction of chop._12 the issue of clustering in the
  // epsilon range seems to be resolved at least for interior points
  public void testChallenge2() {
    Tensor cube = Tensors.fromString("{{0, 0}, {1, 0}, {1, 1}, {0, 1}}");
    double variance = 1e-15;
    Distribution distribution = NormalDistribution.of(0.5, variance);
    Tensor joined = Join.of(cube, RandomVariate.of(distribution, 300, 2));
    Tensor hull = ConvexHull.of(joined);
    assertEquals(hull, cube);
  }

  public void testStream() {
    ConvexHull.of(Stream.empty(), Chop._10);
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
      ConvexHull.of(LieAlgebras.sl2());
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

  public void testFailMore() {
    Tensor bad1 = Tensors.fromString("{{1,2},{3,4,5}}");
    try {
      ConvexHull.of(bad1);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
    Tensor bad2 = Tensors.fromString("{{1,2,3},{3,4}}");
    try {
      ConvexHull.of(bad2);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }
}
