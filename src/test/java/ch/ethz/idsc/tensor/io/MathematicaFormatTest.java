// code by jph
package ch.ethz.idsc.tensor.io;

import java.util.Random;
import java.util.stream.Stream;

import ch.ethz.idsc.tensor.DoubleScalar;
import ch.ethz.idsc.tensor.RationalScalar;
import ch.ethz.idsc.tensor.StringScalar;
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

  public void testStrings() {
    String[] strings = new String[] { //
        "{{3 + 2*I}, -1.0348772853950305 + 0.042973906265653894*I, ", //
        " -1.0348772853950305 - 0.042973906265653894*I, {}, ", //
        " {3.141592653589793, {3, 1.4142135623730951}, 23846238476583465873465/", //
        "   234625348762534876523847652837645223864521}}" };
    Tensor tensor = MathematicaFormat.parse(Stream.of(strings));
    boolean containsStringScalar = tensor.flatten(-1) //
        .filter(scalar -> scalar instanceof StringScalar).findAny().isPresent();
    assertFalse(containsStringScalar);
    // System.out.println(tensor);
    // String ref = "[[3.0+2.0*I], -1.0348772853950305 - 0.042973906265653894*I, [], [3.141592653589793, [3, 1.4142135623730951],
    // 23846238476583465873465/234625348762534876523847652837645223864521]]";
  }
}
