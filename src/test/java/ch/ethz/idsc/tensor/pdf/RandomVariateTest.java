// code by jph
package ch.ethz.idsc.tensor.pdf;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

import ch.ethz.idsc.tensor.RationalScalar;
import ch.ethz.idsc.tensor.ScalarQ;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.alg.Dimensions;
import junit.framework.TestCase;

public class RandomVariateTest extends TestCase {
  public void testVarying() {
    Distribution distribution = NormalDistribution.standard();
    Set<Tensor> set = RandomVariate.of(distribution, 1000).stream().collect(Collectors.toSet());
    assertTrue(970 < set.size());
  }

  public void testSame() {
    Distribution distribution = NormalDistribution.standard();
    assertEquals( //
        RandomVariate.of(distribution, new Random(10), 1000), //
        RandomVariate.of(distribution, new Random(10), 1000) //
    );
  }

  public void testFormatArray() {
    Distribution distribution = DiscreteUniformDistribution.of(2, 11);
    Tensor array = RandomVariate.of(distribution, 3, 4, 5);
    assertEquals(Dimensions.of(array), Arrays.asList(3, 4, 5));
  }

  public void testFormatList() {
    Distribution distribution = DiscreteUniformDistribution.of(2, 11);
    List<Integer> list = Arrays.asList(3, 4, 5);
    Tensor array = RandomVariate.of(distribution, list);
    assertEquals(Dimensions.of(array), list);
  }

  public void testFormatList1() {
    Distribution distribution = BinomialDistribution.of(3, RationalScalar.of(1, 2));
    Tensor array = RandomVariate.of(distribution, 1);
    assertEquals(Dimensions.of(array), Arrays.asList(1));
    assertFalse(ScalarQ.of(array));
  }
}
