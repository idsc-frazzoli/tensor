// code by jph
package ch.ethz.idsc.tensor.alg;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.pdf.Distribution;
import ch.ethz.idsc.tensor.pdf.NormalDistribution;
import ch.ethz.idsc.tensor.pdf.RandomVariate;
import junit.framework.TestCase;

public class TransposeExtTest extends TestCase {
  public void testSimple1() {
    Distribution distribution = NormalDistribution.standard();
    Tensor randn = RandomVariate.of(distribution, 3, 4, 5);
    assertEquals(Transpose.of(randn, 0, 1, 2), randn);
    Tensor trans = Transpose.of(randn, 1, 0, 2);
    assertEquals(trans, Transpose.of(randn));
  }

  public void testSimple2() {
    Distribution distribution = NormalDistribution.standard();
    Tensor randn = RandomVariate.of(distribution, 6, 5, 8);
    long tic;
    // ---
    tic = System.nanoTime();
    Tensor array = usingArray(randn, 1, 2, 0);
    // System.out.println((System.nanoTime() - tic) * 1e-9);
    // ---
    tic = System.nanoTime();
    Tensor trans = Transpose.of(randn, 1, 2, 0);
    // System.out.println((System.nanoTime() - tic) * 1e-9);
    // ---
    assertEquals(trans, array);
    tic = tic + 0;
  }

  private static List<Integer> inverse(List<Integer> list, Integer... sigma) {
    assertEquals(list.size(), sigma.length);
    return IntStream.of(Ordering.INCREASING.of(Tensors.vector(sigma))) //
        .boxed().map(list::get).collect(Collectors.toList());
  }

  private static List<Integer> permute(List<Integer> list, Integer... sigma) {
    return Stream.of(sigma).map(list::get).collect(Collectors.toList());
  }

  private static Tensor usingArray(Tensor tensor, Integer... sigma) {
    return Array.of( //
        list -> tensor.get(permute(list, sigma)), //
        inverse(Dimensions.of(tensor), sigma));
  }
}
