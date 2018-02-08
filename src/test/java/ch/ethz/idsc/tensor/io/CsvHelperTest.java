// code by jph
package ch.ethz.idsc.tensor.io;

import ch.ethz.idsc.tensor.ComplexScalar;
import ch.ethz.idsc.tensor.DecimalScalar;
import ch.ethz.idsc.tensor.DoubleScalar;
import ch.ethz.idsc.tensor.RationalScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.qty.Quantity;
import ch.ethz.idsc.tensor.sca.Round;
import junit.framework.TestCase;

public class CsvHelperTest extends TestCase {
  public void testFraction() {
    Scalar scalar = CsvHelper.FUNCTION.apply(RationalScalar.of(1, 2));
    assertEquals(scalar.toString(), "0.5");
  }

  public void testInteger() {
    Scalar scalar = CsvHelper.FUNCTION.apply(RealScalar.of(-2345891274545L));
    assertEquals(scalar.toString(), "-2345891274545");
  }

  public void testString1() {
    Scalar s = StringScalar.of("here!");
    assertEquals(CsvHelper.FUNCTION.apply(s).toString(), "\"here!\"");
  }

  public void testString2() {
    String string = "\"here!\"";
    Scalar s = StringScalar.of(string);
    assertEquals(CsvHelper.FUNCTION.apply(s).toString(), string);
  }

  public void testDecimal() {
    Scalar scalar = (Scalar) DoubleScalar.of(0.25).map(Round._6);
    assertTrue(scalar instanceof DecimalScalar);
    scalar = CsvHelper.FUNCTION.apply(scalar);
    assertTrue(scalar instanceof DoubleScalar);
  }

  public void testQuotes() {
    Scalar inQuotes = StringScalar.of("\"here\"");
    assertEquals(CsvHelper.wrap(StringScalar.of("here")), inQuotes);
    assertEquals(CsvHelper.wrap(inQuotes), inQuotes);
  }

  public void testSingleInQuotes() {
    Scalar inQuotes = StringScalar.of("\"a\"");
    assertEquals(CsvHelper.wrap(StringScalar.of("a")), inQuotes);
    assertEquals(CsvHelper.wrap(inQuotes), inQuotes);
  }

  public void testEmpty() {
    Scalar inQuotes = StringScalar.of("\"\"");
    assertEquals(CsvHelper.wrap(StringScalar.of("")), inQuotes);
    assertEquals(CsvHelper.wrap(inQuotes), inQuotes);
  }

  public void testComplexFail() {
    try {
      CsvHelper.FUNCTION.apply(ComplexScalar.of(3, 4));
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }

  public void testQuantityFail() {
    try {
      CsvHelper.FUNCTION.apply(Quantity.of(3, "s"));
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }

  public void testFailSingleQuote() {
    CsvHelper.requireQuotesFree("");
    try {
      CsvHelper.wrap(StringScalar.of("\""));
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }

  public void testFail() {
    try {
      CsvHelper.wrap(StringScalar.of("\"here\"\""));
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
    try {
      CsvHelper.wrap(StringScalar.of("here\""));
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
    try {
      CsvHelper.wrap(StringScalar.of("\"here"));
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }
}
