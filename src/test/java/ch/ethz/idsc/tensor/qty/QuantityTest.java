// code by jph
package ch.ethz.idsc.tensor.qty;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

import ch.ethz.idsc.tensor.ComplexScalar;
import ch.ethz.idsc.tensor.DecimalScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Scalars;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.alg.Dimensions;
import ch.ethz.idsc.tensor.io.CsvFormat;
import ch.ethz.idsc.tensor.io.StringScalar;
import junit.framework.TestCase;

public class QuantityTest extends TestCase {
  public void testFromString() {
    assertTrue(Scalars.fromString("-7[m*kg^-2]") instanceof Quantity);
    assertTrue(Scalars.fromString("3 [ m ]") instanceof Quantity);
    assertTrue(Scalars.fromString("3 [ m *rad ]  ") instanceof Quantity);
    assertFalse(Scalars.fromString(" 3  ") instanceof Quantity);
    assertFalse(Scalars.fromString(" 3 [] ") instanceof Quantity);
  }

  public void testNumberUnit() {
    Unit unit = Unit.of("m*s^-1");
    Scalar scalar = Quantity.of(3, unit);
    assertEquals(scalar, Scalars.fromString("3[m*s^-1]"));
    assertEquals(scalar, Quantity.of(3, "m*s^-1"));
    assertEquals(scalar, Quantity.of(RealScalar.of(3), "m*s^-1"));
    assertEquals(scalar, Quantity.of(RealScalar.of(3), unit));
  }

  private static void _check(String string) {
    Scalar scalar = Scalars.fromString(string);
    assertEquals(scalar.getClass(), StringScalar.class);
    assertEquals(scalar.toString(), string);
  }

  public void testStringScalar() {
    _check("-7[m][kg]");
    _check("-7[m]a");
    _check("-7[m*kg^-2");
    _check("1abc[m]");
    _check("1abc[]");
  }

  public void testFromStringComplex() {
    Quantity quantity = (Quantity) Scalars.fromString("(-7+3*I)[kg^-2*m*s]");
    Scalar scalar = quantity.value();
    assertEquals(scalar, ComplexScalar.of(-7, 3));
  }

  public void testDecimal() {
    Quantity quantity = (Quantity) Scalars.fromString("-7.23459823746593784659387465`13.0123[m*kg^-2]");
    assertTrue(quantity.value() instanceof DecimalScalar);
  }

  public void testParseFail() {
    try {
      Quantity.of(3.14, "^2");
      fail();
    } catch (Exception exception) {
      // ---
    }
    try {
      Quantity.of(3.14, "m^2a");
      fail();
    } catch (Exception exception) {
      // ---
    }
    try {
      Quantity.of(3.14, "m^");
      fail();
    } catch (Exception exception) {
      // ---
    }
    try {
      Quantity.of(3.14, "m[^2");
      fail();
    } catch (Exception exception) {
      // ---
    }
    try {
      Quantity.of(3.14, "m]^2");
      fail();
    } catch (Exception exception) {
      // ---
    }
  }

  public void testNestFail() {
    Scalar q1 = Quantity.of(3.14, "m");
    try {
      Quantity.of(q1, "s");
      fail();
    } catch (Exception exception) {
      // ---
    }
  }

  public void testNestEmptyFail() {
    try {
      Quantity.of(Quantity.of(2, "s"), "");
      fail();
    } catch (Exception exception) {
      // ---
    }
  }

  public void testValue() {
    Quantity quantity = (Quantity) Scalars.fromString("-7+3*I[kg^-2*m*s]");
    Scalar scalar = quantity.value();
    assertEquals(scalar, ComplexScalar.of(-7, 3));
  }

  public void testUnitString() {
    Quantity quantity = (Quantity) Scalars.fromString("-7+3*I[kg^-2*m*s]");
    String string = quantity.unit().toString();
    assertEquals(string, "kg^-2*m*s");
  }

  public void testEmptyPass() {
    assertEquals(Quantity.of(3.14, ""), RealScalar.of(3.14));
  }

  public void testImport() throws Exception {
    String path = getClass().getResource("/io/qty/quantity0.csv").getPath();
    Tensor tensor = CsvFormat.parse( //
        Files.readAllLines(Paths.get(path)).stream(), //
        string -> Tensors.fromString(string));
    assertEquals(Dimensions.of(tensor), Arrays.asList(2, 2));
    assertTrue(tensor.Get(0, 0) instanceof Quantity);
    assertTrue(tensor.Get(0, 1) instanceof Quantity);
    assertTrue(tensor.Get(1, 0) instanceof Quantity);
    assertTrue(tensor.Get(1, 1) instanceof RealScalar);
  }

  public void testStringScalarFail() {
    Unit unit = Unit.of("a");
    try {
      Quantity.of(StringScalar.of("123"), unit);
      fail();
    } catch (Exception exception) {
      // ---
    }
  }

  public void testNullFail1() {
    try {
      Quantity.of((Number) null, Unit.of("s"));
      fail();
    } catch (Exception exception) {
      // ---
    }
  }

  public void testNullFail2() {
    try {
      Quantity.of((Number) null, "s");
      fail();
    } catch (Exception exception) {
      // ---
    }
    try {
      Quantity.of(123, (String) null);
      fail();
    } catch (Exception exception) {
      // ---
    }
  }
  // public void testNullFail() {
  // try {
  // Quantity.of(RealScalar.ZERO, (Unit) null);
  // fail();
  // } catch (Exception exception) {
  // // ---
  // }
  // }
}
