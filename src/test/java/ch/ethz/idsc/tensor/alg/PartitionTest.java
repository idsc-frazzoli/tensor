// code by jph
package ch.ethz.idsc.tensor.alg;

import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import junit.framework.TestCase;

public class PartitionTest extends TestCase {
  public void testPartition() {
    Tensor s = Partition.of(Tensors.vector(1, 2, 3), 3);
    Tensor r = Tensors.fromString("{{1,2,3}}");
    assertEquals(s, r);
  }
}
