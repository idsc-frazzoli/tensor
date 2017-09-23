// code by jph
package ch.ethz.idsc.tensor.qty;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

import ch.ethz.idsc.tensor.ComplexScalar;
import ch.ethz.idsc.tensor.DecimalScalar;
import ch.ethz.idsc.tensor.RationalScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Scalars;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.alg.Dimensions;
import ch.ethz.idsc.tensor.io.CsvFormat;
import ch.ethz.idsc.tensor.io.Serialization;
import junit.framework.TestCase;

public class QuantityTest extends TestCase {
  public void testSimple() {
    assertTrue(Quantity.fromString("-7[m*kg^-2]") instanceof Quantity);
    assertTrue(Quantity.fromString("3 [ m ]") instanceof Quantity);
    assertTrue(Quantity.fromString("3 [ m *rad ]  ") instanceof Quantity);
    assertFalse(Quantity.fromString(" 3  ") instanceof Quantity);
    assertFalse(Quantity.fromString(" 3 [] ") instanceof Quantity);
  }

  public void testFromStringFail() {
    try {
      Quantity.fromString("-7[m][kg]");
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
    try {
      Quantity.fromString("-7[m]a");
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
    try {
      Quantity.fromString("-7[m*kg^-2");
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }

  public void testDecimal() {
    Quantity quantity = (Quantity) Quantity.fromString("-7.23459823746593784659387465`13.0123[m*kg^-2]");
    assertTrue(quantity.value() instanceof DecimalScalar);
  }

  public void testParseFail() {
    try {
      Quantity.of(3.14, "^2");
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
    try {
      Quantity.of(3.14, "m^2a");
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
    try {
      Quantity.of(3.14, "m^");
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
    try {
      Quantity.of(3.14, "m[^2");
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
    try {
      Quantity.of(3.14, "m]^2");
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }

  public void testNestFail() {
    Scalar q1 = Quantity.of(3.14, "m");
    try {
      Quantity.of(q1, "s");
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }

  public void testValue() {
    Quantity quantity = (Quantity) Quantity.fromString("-7+3*I[kg^-2*m*s]");
    Scalar scalar = quantity.value();
    assertEquals(scalar, ComplexScalar.of(-7, 3));
  }

  public void testUnitString() {
    Quantity quantity = (Quantity) Quantity.fromString("-7+3*I[kg^-2*m*s]");
    String string = quantity.unit().toString();
    assertEquals(string, "kg^-2*m*s");
  }

  public void testEmptyPass() {
    assertEquals(Quantity.of(3.14, ""), RealScalar.of(3.14));
  }

  public void testSerializable() throws Exception {
    Quantity quantity = (Quantity) Quantity.fromString("-7+3*I[kg^-2*m*s]");
    Quantity copy = Serialization.copy(quantity);
    assertEquals(quantity, copy);
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

  public void testEmpty() {
    Scalar q1 = Quantity.of(3, "m*s");
    Scalar q2 = Quantity.of(7, "s*m");
    assertTrue(q1.divide(q2) instanceof RationalScalar);
    assertTrue(q1.under(q2) instanceof RationalScalar);
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
