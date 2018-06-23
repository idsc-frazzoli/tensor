// code by jph
package ch.ethz.idsc.tensor.alg;

import java.util.stream.IntStream;

import ch.ethz.idsc.tensor.RationalScalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.io.Serialization;
import ch.ethz.idsc.tensor.pdf.BinomialDistribution;
import ch.ethz.idsc.tensor.pdf.Distribution;
import ch.ethz.idsc.tensor.pdf.NormalDistribution;
import ch.ethz.idsc.tensor.pdf.RandomVariate;
import junit.framework.TestCase;

public class OrderingTest extends TestCase {
  public void testVector() {
    Tensor vector = Tensors.vector(4, 2, 3, 0, 1);
    int[] array = Ordering.INCREASING.of(vector);
    Tensor ascending = Tensor.of( //
        IntStream.range(0, array.length).mapToObj(index -> vector.Get(array[index])));
    assertEquals(ascending, Sort.of(vector));
  }

  public void testRandom() {
    Distribution d = BinomialDistribution.of(12, RationalScalar.of(1, 3));
    Tensor vector = RandomVariate.of(d, 1000);
    int[] array = Ordering.INCREASING.of(vector);
    Tensor ascending = Tensor.of( //
        IntStream.range(0, array.length).mapToObj(index -> vector.Get(array[index])));
    assertEquals(ascending, Sort.of(vector));
  }

  public void testNormal() {
    Distribution d = NormalDistribution.standard();
    Tensor vector = RandomVariate.of(d, 1000);
    int[] array = Ordering.INCREASING.of(vector);
    Tensor ascending = Tensor.of( //
        IntStream.range(0, array.length).mapToObj(index -> vector.Get(array[index])));
    assertEquals(ascending, Sort.of(vector));
  }

  public void testEnum() {
    assertEquals(Ordering.valueOf("INCREASING"), Ordering.INCREASING);
    assertEquals(Ordering.valueOf("DECREASING"), Ordering.DECREASING);
  }

  public void testSerializable() throws Exception {
    Ordering a = Ordering.DECREASING;
    Ordering b = Serialization.copy(a);
    assertEquals(a, b);
  }
}
