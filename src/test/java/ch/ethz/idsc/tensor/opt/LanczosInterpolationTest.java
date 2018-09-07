// code by jph
package ch.ethz.idsc.tensor.opt;

import java.util.Arrays;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.alg.Dimensions;
import ch.ethz.idsc.tensor.alg.Range;
import ch.ethz.idsc.tensor.io.ResourceData;
import ch.ethz.idsc.tensor.lie.LieAlgebras;
import ch.ethz.idsc.tensor.pdf.DiscreteUniformDistribution;
import ch.ethz.idsc.tensor.pdf.Distribution;
import ch.ethz.idsc.tensor.pdf.RandomVariate;
import ch.ethz.idsc.tensor.pdf.UniformDistribution;
import ch.ethz.idsc.tensor.sca.Chop;
import ch.ethz.idsc.tensor.sca.Clip;
import ch.ethz.idsc.tensor.sca.Increment;
import junit.framework.TestCase;

public class LanczosInterpolationTest extends TestCase {
  public void testVector() {
    Tensor vector = Tensors.vector(-1, 0, 3, 2, 0, -4, 2);
    for (int size = 1; size < 5; ++size) {
      Interpolation interpolation = LanczosInterpolation.of(vector, size);
      for (int index = 0; index < vector.length(); ++index) {
        Scalar scalar = interpolation.Get(Tensors.vector(index));
        assertTrue(Chop._14.close(vector.Get(index), scalar));
      }
    }
  }

  public void testGetEmpty() {
    Interpolation interpolation = LanczosInterpolation.of(LieAlgebras.so3().unmodifiable());
    Tensor tensor = interpolation.get(Tensors.empty());
    tensor.set(t -> t.append(RealScalar.ONE), Tensor.ALL);
  }

  public void testImage() {
    String string = "/io/gray15x9.png";
    Tensor tensor = ResourceData.of(string);
    assertEquals(Dimensions.of(tensor), Arrays.asList(9, 15));
    Interpolation interpolation = LanczosInterpolation.of(tensor, 2);
    Tensor result = interpolation.get(Tensors.vector(6.2));
    assertEquals(Dimensions.of(result), Arrays.asList(15));
    Scalar scalar = interpolation.Get(Tensors.vector(4.4, 7.2));
    assertTrue(Chop._14.close(scalar, RealScalar.of(105.27240539882584)));
  }

  public void testImage3() {
    String string = "/io/gray15x9.png";
    Tensor tensor = ResourceData.of(string);
    assertEquals(Dimensions.of(tensor), Arrays.asList(9, 15));
    Interpolation interpolation = LanczosInterpolation.of(tensor);
    Tensor result = interpolation.get(Tensors.vector(6.2));
    assertEquals(Dimensions.of(result), Arrays.asList(15));
    Scalar scalar = interpolation.Get(Tensors.vector(4.4, 7.2));
    assertTrue(Chop._14.close(scalar, RealScalar.of(94.24810834850828)));
  }

  public void testUseCase() {
    Tensor tensor = Range.of(1, 11);
    Interpolation interpolation = LanczosInterpolation.of(tensor);
    Distribution distribution = DiscreteUniformDistribution.of(0, (tensor.length() - 1) * 3 + 1);
    for (int count = 0; count < 30; ++count) {
      Scalar index = RandomVariate.of(distribution).divide(RealScalar.of(3));
      Scalar scalar = interpolation.At(index);
      Scalar diff = Increment.ONE.apply(index).subtract(scalar);
      Clip.function(0, 0.5).requireInside(diff);
      assertEquals(scalar, interpolation.get(Tensors.of(index)));
      assertEquals(scalar, interpolation.at(index));
    }
  }

  public void testInvalidFail() {
    Tensor vector = Tensors.vector(-1, 0, 3, 2, 0, -4, 2);
    try {
      LanczosInterpolation.of(vector, -1);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }

  public void test1D() {
    Interpolation interpolation = LanczosInterpolation.of(Tensors.vector(10, 20, 30, 40));
    StaticHelper.checkMatch(interpolation);
    StaticHelper.checkMatchExact(interpolation);
    StaticHelper.getScalarFail(interpolation);
  }

  public void test2D() {
    Distribution distribution = UniformDistribution.unit();
    Interpolation interpolation = LanczosInterpolation.of(RandomVariate.of(distribution, 3, 5));
    StaticHelper.checkMatch(interpolation);
    StaticHelper.checkMatchExact(interpolation);
    StaticHelper.getScalarFail(interpolation);
  }
}
