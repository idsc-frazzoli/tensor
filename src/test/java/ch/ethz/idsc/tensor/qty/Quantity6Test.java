// code by jph
package ch.ethz.idsc.tensor.qty;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.function.Function;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Scalars;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.alg.Dimensions;
import ch.ethz.idsc.tensor.io.CsvFormat;
import ch.ethz.idsc.tensor.sca.Chop;
import ch.ethz.idsc.tensor.sca.Cos;
import ch.ethz.idsc.tensor.sca.Cosh;
import ch.ethz.idsc.tensor.sca.ScalarUnaryOperator;
import ch.ethz.idsc.tensor.sca.Sin;
import ch.ethz.idsc.tensor.sca.Sinh;
import junit.framework.TestCase;

public class Quantity6Test extends TestCase {
  private static void _check(Scalar value, ScalarUnaryOperator suo, Function<Double, Double> f) {
    Scalar s1 = Quantity.of(value, "rad");
    Scalar result = suo.apply(s1);
    Scalar actual = RealScalar.of(f.apply(value.number().doubleValue()));
    assertEquals(result, actual);
  }

  public void testTrig() {
    for (Tensor _value : Tensors.vector(-2.323, -1, -.3, 0, .2, 1.2, 3., 4.456)) {
      Scalar value = _value.Get();
      _check(value, Sin::of, Math::sin);
      _check(value, Cos::of, Math::cos);
      _check(value, Sinh::of, Math::sinh);
      _check(value, Cosh::of, Math::cosh);
    }
  }

  public void testTrigDegree() {
    Scalar a = Quantity.of(180, "deg");
    assertTrue(Chop._13.close(Sin.of(a), RealScalar.ZERO));
    assertTrue(Chop._13.close(Cos.of(a), RealScalar.ONE.negate()));
  }

  public void testTrigFail() {
    try {
      Sin.of(Quantity.fromString("1.2[m]"));
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }

  public void testExactIntFail() {
    try {
      Scalar scalar = Quantity.of(10, "m");
      Scalars.intValueExact(scalar);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }

  public void testImport() throws Exception {
    String path = getClass().getResource("/io/qty/quantity0.csv").getPath();
    Tensor tensor = CsvFormat.parse(Files.lines(Paths.get(path)), string -> Tensors.fromString(string, Quantity::fromString));
    assertEquals(Dimensions.of(tensor), Arrays.asList(2, 2));
    assertTrue(tensor.Get(0, 0) instanceof Quantity);
    assertTrue(tensor.Get(0, 1) instanceof Quantity);
    assertTrue(tensor.Get(1, 0) instanceof Quantity);
    assertTrue(tensor.Get(1, 1) instanceof RealScalar);
  }
}
