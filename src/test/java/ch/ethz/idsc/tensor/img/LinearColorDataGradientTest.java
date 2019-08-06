// code by jph
package ch.ethz.idsc.tensor.img;

import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.alg.Subdivide;
import ch.ethz.idsc.tensor.pdf.DiscreteUniformDistribution;
import ch.ethz.idsc.tensor.pdf.RandomVariate;
import junit.framework.TestCase;

public class LinearColorDataGradientTest extends TestCase {
  public void testRandom() {
    ColorDataGradient colorDataGradient = //
        LinearColorDataGradient.of(RandomVariate.of(DiscreteUniformDistribution.of(0, 256), 123, 4));
    Subdivide.of(0, 1, 10).map(colorDataGradient);
  }

  public void testSingle() {
    ColorDataGradient colorDataGradient = //
        LinearColorDataGradient.of(Tensors.fromString("{{1, 2, 3, 4}}"));
    Subdivide.of(0, 1, 10).map(colorDataGradient);
  }

  public void testCornerCaseLo() {
    try {
      LinearColorDataGradient.of(Tensors.fromString("{{0, 0, 0, 0}, {-0.1, 0, 0, 0}}"));
      fail();
    } catch (Exception exception) {
      // ---
    }
  }

  public void testCornerCase() {
    try {
      LinearColorDataGradient.of(Tensors.fromString("{{0, 0, 0, 0}, {256, 256, 256, 256}}"));
      fail();
    } catch (Exception exception) {
      // ---
    }
  }

  public void testRangeFail() {
    try {
      LinearColorDataGradient.of(Tensors.fromString("{{1, 2, 3, 4}, {1, 2, 3, 257}}"));
      fail();
    } catch (Exception exception) {
      // ---
    }
  }

  public void testVectorFail() {
    try {
      LinearColorDataGradient.of(Tensors.vector(1, 2, 3, 4));
      fail();
    } catch (Exception exception) {
      // ---
    }
  }

  public void testEmptyFail() {
    try {
      LinearColorDataGradient.of(Tensors.empty());
      fail();
    } catch (Exception exception) {
      // ---
    }
  }
}
