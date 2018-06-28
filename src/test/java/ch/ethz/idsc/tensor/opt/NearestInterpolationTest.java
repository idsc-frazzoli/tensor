// code by jph
package ch.ethz.idsc.tensor.opt;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.io.Serialization;
import ch.ethz.idsc.tensor.pdf.Distribution;
import ch.ethz.idsc.tensor.pdf.RandomVariate;
import ch.ethz.idsc.tensor.pdf.UniformDistribution;
import junit.framework.TestCase;

public class NearestInterpolationTest extends TestCase {
  public void testEmpty() {
    Interpolation interpolation = NearestInterpolation.of(Tensors.empty());
    assertEquals(interpolation.get(Tensors.empty()), Tensors.empty());
  }

  public void testStandard() {
    Interpolation interpolation = NearestInterpolation.of(Tensors.vector(10, 20, 30, 40));
    assertEquals(interpolation.get(Tensors.vector(2.8)), RealScalar.of(40));
    assertEquals(interpolation.get(Tensors.vector(1.1)), RealScalar.of(20));
  }

  public void testSerialize() throws Exception {
    Serialization.copy(NearestInterpolation.of(Tensors.vector(9, 1, 8, 3, 4)));
  }

  public void testNested() {
    Tensor index = Tensors.fromString("{2.3}");
    Interpolation interpolation = MappedInterpolation.of(Tensors.vector(10, 20, 30, 40), s -> index);
    interpolation.get(Tensors.vector(1));
  }

  public void testNestedFail() {
    Tensor index = Tensors.fromString("{{2.3}}");
    Interpolation interpolation = MappedInterpolation.of(Tensors.vector(10, 20, 30, 40), s -> index);
    try {
      interpolation.get(Tensors.vector(1));
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }

  public void test1D() {
    Interpolation interpolation = NearestInterpolation.of(Tensors.vector(10, 20, 30, 40));
    StaticHelper.checkMatch(interpolation);
    StaticHelper.checkMatchExact(interpolation);
    StaticHelper.getScalarFail(interpolation);
  }

  public void test2D() {
    Distribution distribution = UniformDistribution.unit();
    Interpolation interpolation = NearestInterpolation.of(RandomVariate.of(distribution, 3, 5));
    StaticHelper.checkMatch(interpolation);
    StaticHelper.checkMatchExact(interpolation);
    StaticHelper.getScalarFail(interpolation);
  }

  public void testFailNull() {
    try {
      NearestInterpolation.of(null);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }
}
