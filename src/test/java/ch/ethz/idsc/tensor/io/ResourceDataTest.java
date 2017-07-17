// code by jph
package ch.ethz.idsc.tensor.io;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import ch.ethz.idsc.tensor.Scalars;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.alg.Dimensions;
import ch.ethz.idsc.tensor.opt.Interpolation;
import ch.ethz.idsc.tensor.opt.LinearInterpolation;
import junit.framework.TestCase;

public class ResourceDataTest extends TestCase {
  public void testColorschemeClassic() throws IOException {
    Tensor tensor = ResourceData.of("/colorscheme/classic.csv");
    assertNotNull(tensor);
    List<Integer> list = Dimensions.of(tensor);
    assertEquals(list, Arrays.asList(256, 4));
    Interpolation interpolation = LinearInterpolation.of(tensor);
    Tensor result = interpolation.get(Tensors.vector(255));
    assertEquals(result, Tensors.vector(255, 237, 237, 255));
    try {
      interpolation.get(Tensors.vector(256));
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }

  public void testPrimes() throws IOException {
    Tensor primes = ResourceData.of("/number/primes.vector");
    assertNotNull(primes);
    List<Integer> dimensions = Dimensions.of(primes);
    assertEquals(dimensions.size(), 1);
    assertEquals(primes.Get(5), Scalars.fromString("13"));
  }

  public void testFail() {
    try {
      ResourceData.of("/number/exists.fail");
      assertTrue(false);
    } catch (IOException exception) {
      // ---
    }
  }
}
