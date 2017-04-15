// code by jph
package ch.ethz.idsc.tensor.alg;

import java.util.Arrays;

import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.io.MathematicaFormat;
import junit.framework.TestCase;

public class ListConvolveTest extends TestCase {
  public void testVector1() {
    Tensor kernel = Tensors.vector(0, -1, 3);
    Tensor tensor = ArrayPad.of(Tensors.vector(1, 6, 0, 0, -1), // 
        Arrays.asList(kernel.length() - 1), Arrays.asList(kernel.length() - 1));
    Tensor result = ListConvolve.of(kernel, tensor);
    Tensor actual = Tensors.vector(0, -1, -3, 18, 0, 1, -3);
    assertEquals(result, actual);
  }

  public void testMatrix() {
    Tensor kernel = MathematicaFormat.parse("{{2, 1, 3}, {0, 1, -1}}");
    Tensor tensor = MathematicaFormat.parse("{{0, 0, 1, 0, -2, 1, 2}, {2, 0, 1, 0, -2, 1, 2}, {3, 2, 3, 3, 45, 3, 2}}");
    Tensor result = ListConvolve.of(kernel, tensor);
    Tensor actual = MathematicaFormat.parse("{{8, 2, -2, -2, 2}, {15, 16, 101, 58, 145}}");
    assertEquals(result, actual);
  }
}
