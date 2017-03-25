// code by jph
package ch.ethz.idsc.tensor.io;

import java.util.Random;

import ch.ethz.idsc.tensor.DoubleScalar;
import ch.ethz.idsc.tensor.RationalScalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import junit.framework.TestCase;

public class MathematicaFormatTest extends TestCase {
  public void testMathematica() {
    int n = 20;
    int m = 10;
    Random random = new Random();
    Tensor a = Tensors.matrix((i, j) -> //
    random.nextInt(3) == 0 ? //
        DoubleScalar.of(random.nextDouble()) : //
        RationalScalar.of(random.nextLong(), random.nextLong()), n, m);
    assertEquals(MathematicaFormat.of(a).count(), n); // count rows
    assertEquals(a, MathematicaFormat.parse(MathematicaFormat.of(a))); // full circle
  }
  // public void testComplex() {
  // Scalar s = ComplexScalar.of(3, 5);
  // MathematicaFormat.of(s).forEach(System.out::println);
  // }
}
