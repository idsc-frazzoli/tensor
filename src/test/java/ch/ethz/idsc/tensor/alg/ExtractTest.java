// code by jph
package ch.ethz.idsc.tensor.alg;

import java.util.Arrays;
import java.util.Collections;

import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import junit.framework.TestCase;

public class ExtractTest extends TestCase {
  public void testEmpty() {
    assertEquals(Extract.of(Tensors.empty(), Collections.emptyList()), Tensors.empty());
    Tensor tensor = Tensors.vector(2, 3, 4);
    assertEquals(Extract.of(tensor, Collections.emptyList()), Tensors.empty());
  }

  public void testFail() {
    try {
      Tensor tensor = Tensors.vector(2, 3, 4);
      Extract.of(tensor, Arrays.asList(-1));
    } catch (Exception exception) {
      // ---
    }
    try {
      Tensor tensor = Tensors.vector(2, 3, 4);
      Extract.of(tensor, Arrays.asList(2, 3));
    } catch (Exception exception) {
      // ---
    }
  }
}
