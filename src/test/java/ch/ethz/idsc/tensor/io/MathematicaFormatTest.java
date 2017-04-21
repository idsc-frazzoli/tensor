// code by jph
package ch.ethz.idsc.tensor.io;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Stream;

import ch.ethz.idsc.tensor.DoubleScalar;
import ch.ethz.idsc.tensor.RationalScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Scalars;
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

  private static void checkNonString(Tensor tensor) {
    Optional<Scalar> optional = tensor.flatten(-1) //
        .map(Scalar.class::cast) //
        .filter(Scalars::isStringScalar) //
        .findAny();
    boolean containsStringScalar = optional.isPresent();
    if (containsStringScalar)
      System.out.println(optional.get());
    assertFalse(containsStringScalar);
  }

  public void testStrings() {
    String[] strings = new String[] { //
        "{{3 + 2*I}, I,-I,-1.0348772853950305 + 0.042973906265653894*I, ", //
        " -1.0348772853950305 - 0.042973906265653894*I, {}, ", //
        " {3.141592653589793, {3, 1.4142135623730951}, 23846238476583465873465/", //
        "   234625348762534876523847652837645223864521}}" };
    Tensor tensor = MathematicaFormat.parse(Stream.of(strings));
    checkNonString(tensor);
    // System.out.println(tensor);
    // String ref = "[[3.0+2.0*I], -1.0348772853950305 - 0.042973906265653894*I, [], [3.141592653589793, [3, 1.4142135623730951],
    // 23846238476583465873465/234625348762534876523847652837645223864521]]";
  }

  public void testComplex() {
    String[] strings = new String[] { //
        "{{3 + I}, -1.0348772853950305 - 0.042973906265653894*I, {}, ", //
        " 0.1 + I, 0.1 - I, ", // <- these were manually added
        " 0. + 0.123*I, 0. - 123233.323123*I, {0. + 1982.6716245387552*I,", //
        "  {(81263581726538*I)/42921390881, 0. + 892.5158065769785*I}} " };
    Tensor tensor = MathematicaFormat.parse(Stream.of(strings));
    checkNonString(tensor);
  }

  public void testBasic() throws IOException {
    String string = getClass().getResource("/io/basic.mathematica").getPath();
    Tensor tensor = MathematicaFormat.parse(Files.lines(Paths.get(string)));
    // System.out.println(tensor);
    checkNonString(tensor);
  }

  public void testExponent() throws IOException {
    String string = getClass().getResource("/io/basic.mathematica").getPath();
    Tensor tensor = MathematicaFormat.parse(Files.lines(Paths.get(string)));
    // System.out.println(tensor);
    checkNonString(tensor);
  }
}
