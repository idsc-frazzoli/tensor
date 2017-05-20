// code by jph
package ch.ethz.idsc.tensor.alg;

import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import junit.framework.TestCase;

public class DifferencesTest extends TestCase {
  public void testSimple1() {
    Tensor dif = Differences.of(Tensors.vector(3, 2, 9));
    assertEquals(dif, Tensors.vector(-1, 7));
  }

  public void testSimple2() {
    Tensor dif = Differences.of(Tensors.of(Tensors.vector(3, 2, 9)));
    assertEquals(dif, Tensors.empty());
  }

  public void testSimple3() {
    Tensor dif = Differences.of(Tensors.of( //
        Tensors.vector(3, 2, 9), //
        Tensors.vector(9, 3, 1) //
    ));
    assertEquals(dif, Tensors.fromString("{{6, 1, -8}}"));
  }
}
