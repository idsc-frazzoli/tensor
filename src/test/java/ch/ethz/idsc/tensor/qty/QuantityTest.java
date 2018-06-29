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

  public void testFromStringFail() {
    assertTrue(Scalars.fromString("-7[m][kg]") instanceof StringScalar);
    assertTrue(Scalars.fromString("-7[m]a") instanceof StringScalar);
    assertTrue(Scalars.fromString("-7[m*kg^-2") instanceof StringScalar);
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

  public void testSerializable() throws Exception {
    Quantity quantity = (Quantity) Scalars.fromString("-7+3*I[kg^-2*m*s]");
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

  public void testEquals() {
    assertFalse(Quantity.of(10, "m").equals("s"));
    assertFalse(Quantity.of(10, "m").equals(Quantity.of(2, "m")));
    assertFalse(Quantity.of(10, "m").equals(Quantity.of(10, "kg")));
  }

  public void testEqualsZero() {
    assertFalse(Quantity.of(0, "m").equals(RealScalar.ZERO));
  }

  public void testHashCode() {
    assertEquals( //
        Quantity.of(10.2, "m^-1*kg").hashCode(), //
        Quantity.of(10.2, "kg*m^-1").hashCode());
  }

  public void testEmpty() {
    Scalar q1 = Quantity.of(3, "m*s");
    Scalar q2 = Quantity.of(7, "s*m");
    Scalar s3 = q1.divide(q2);
    // System.out.println(s3);
    assertTrue(s3 instanceof RationalScalar);
    assertTrue(q1.under(q2) instanceof RationalScalar);
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

  public void testNullFail1() {
    try {
      Quantity.of((Number) null, Unit.of("s"));
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }

  public void testNullFail2() {
    try {
      Quantity.of((Number) null, "s");
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
    try {
      Quantity.of(123, (String) null);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }
  // public void testNullFail() {
  // try {
  // Quantity.of(RealScalar.ZERO, (Unit) null);
  // assertTrue(false);
  // } catch (Exception exception) {
  // // ---
  // }
  // }
}
