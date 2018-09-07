// code by jph
package ch.ethz.idsc.tensor.opt;

import java.util.Optional;

import ch.ethz.idsc.tensor.ExactScalarQ;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.lie.LieAlgebras;
import ch.ethz.idsc.tensor.pdf.Distribution;
import ch.ethz.idsc.tensor.pdf.RandomVariate;
import ch.ethz.idsc.tensor.pdf.UniformDistribution;
import ch.ethz.idsc.tensor.sca.Chop;
import junit.framework.TestCase;

public class SphereFitTest extends TestCase {
  public void testLinearSolve() {
    Tensor _points = Tensors.of( //
        Tensors.vector(1, 0), //
        Tensors.vector(0, 0), //
        Tensors.vector(0, 1)).unmodifiable();
    for (Tensor shift : Tensors.fromString("{{0,0},{0,-5/3},{10,0},{20,20}}")) {
      Tensor points = Tensor.of(_points.stream().map(point -> point.add(shift)));
      Optional<Tensor> optional = SphereFit.of(points);
      Tensor center = optional.get().get(0);
      Tensor radius = optional.get().Get(1);
      assertEquals(center, Tensors.vector(0.5, 0.5).add(shift));
      assertTrue(ExactScalarQ.all(center));
      assertTrue(Chop._13.close(radius, RealScalar.of(Math.sqrt(0.5))));
    }
  }

  public void testLeastSquare() {
    Tensor _points = Tensors.of( //
        Tensors.vector(1, 0), //
        Tensors.vector(0, 0), //
        Tensors.vector(0, 1), //
        Tensors.vector(0.8, 0.7));
    for (Tensor shift : Tensors.fromString("{{0,0},{0,-5/3},{10,0},{20,20}}")) {
      Tensor points = Tensor.of(_points.stream().map(point -> point.add(shift)));
      Optional<Tensor> optional = SphereFit.of(points);
      Tensor center = optional.get().get(0);
      Tensor radius = optional.get().Get(1);
      assertTrue(Chop._13.close(center, Tensors.vector(0.39894957983193285, 0.4067226890756306).add(shift)));
      assertTrue(Chop._09.close(radius, RealScalar.of(0.6342832218291473)));
    }
  }

  public void testLowRank() {
    Tensor _points = Tensors.of( //
        Tensors.vector(0, 0), //
        Tensors.vector(0, 0), //
        Tensors.vector(0, 1)).unmodifiable();
    for (Tensor shift : Tensors.fromString("{{0,0},{0,-5/3},{10,0},{20,20}}")) {
      Tensor points = Tensor.of(_points.stream().map(point -> point.add(shift)));
      Optional<Tensor> optional = SphereFit.of(points);
      assertFalse(optional.isPresent());
    }
  }

  public void testRank() {
    Distribution distribution = UniformDistribution.unit();
    for (int dim = 3; dim < 5; ++dim)
      for (int count = 1; count < 10; ++count) {
        Tensor points = RandomVariate.of(distribution, count, dim);
        Optional<Tensor> optional = SphereFit.of(points);
        assertEquals(optional.isPresent(), dim < count);
      }
  }

  public void testSingle() {
    Tensor points = Tensors.of(Tensors.vector(1, 2, 3));
    Optional<Tensor> optional = SphereFit.of(points);
    assertFalse(optional.isPresent()); // because a single point is co-linear
  }

  public void testFailEmpty() {
    try {
      SphereFit.of(Tensors.empty());
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }

  public void testFailScalar() {
    try {
      SphereFit.of(RealScalar.ONE);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }

  public void testFailRank3() {
    try {
      SphereFit.of(LieAlgebras.so3());
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }
}
