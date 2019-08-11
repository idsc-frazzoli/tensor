// code by jph
package ch.ethz.idsc.tensor.mat;

import java.util.Arrays;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.alg.Dimensions;
import ch.ethz.idsc.tensor.pdf.RandomVariate;
import ch.ethz.idsc.tensor.pdf.UniformDistribution;
import junit.framework.TestCase;

public class ConjugateTransposeTest extends TestCase {
  public void testExample1() {
    Tensor m1 = Tensors.fromString("{{1, 5+I}, {2, 3}}");
    Tensor m2 = Tensors.fromString("{{1, 2}, {5-I, 3}}");
    assertEquals(ConjugateTranspose.of(m1), m2);
  }

  public void testExample2() {
    Tensor m1 = Tensors.fromString("{{1+2*I, 5+I}, {2, 3}}");
    Tensor m2 = Tensors.fromString("{{1-2*I, 2}, {5-I, 3}}");
    assertEquals(ConjugateTranspose.of(m1), m2);
  }

  public void testRank3() {
    Tensor tensor = ConjugateTranspose.of(RandomVariate.of(UniformDistribution.unit(), 2, 3, 4));
    assertEquals(Dimensions.of(tensor), Arrays.asList(3, 2, 4));
  }

  public void testScalarFail() {
    try {
      ConjugateTranspose.of(RealScalar.ONE);
      fail();
    } catch (Exception exception) {
      // ---
    }
  }

  public void testVectorFail() {
    try {
      ConjugateTranspose.of(Tensors.vector(1, 2, 3));
      fail();
    } catch (Exception exception) {
      // ---
    }
  }
}
