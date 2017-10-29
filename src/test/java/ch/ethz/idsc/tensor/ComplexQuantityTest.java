// code by jph
package ch.ethz.idsc.tensor;

import ch.ethz.idsc.tensor.qty.Quantity;
import junit.framework.TestCase;

public class ComplexQuantityTest extends TestCase {
  public void testPolar() {
    Scalar abs = Quantity.of(2, "V*m^-1");
    Scalar q = ComplexScalar.fromPolar(abs, RealScalar.ONE);
    assertTrue(q instanceof Quantity);
    Scalar modulus = q.abs();
    assertEquals(modulus, abs);
  }

  public void testUnder1() {
    Scalar c = ComplexScalar.of(2, 3);
    Scalar q = Quantity.of(1, "V");
    Scalar cuq = c.under(q);
    assertTrue(cuq instanceof Quantity);
    Scalar qdc = q.divide(c);
    assertTrue(qdc instanceof Quantity);
    Scalar crq = c.reciprocal().multiply(q);
    assertTrue(crq instanceof Quantity);
    assertEquals(cuq, crq);
    assertEquals(cuq, qdc);
  }

  public void testUnder2() {
    Scalar c = ComplexScalar.of(2, 3);
    Scalar q = Quantity.of(1, "V");
    Scalar quc = q.under(c);
    assertTrue(quc instanceof Quantity);
    Scalar cdq = c.divide(q);
    assertTrue(cdq instanceof Quantity);
    Scalar qrc = q.reciprocal().multiply(c);
    assertTrue(qrc instanceof Quantity);
    assertEquals(quc, qrc);
    assertEquals(quc, cdq);
  }

  public void testUnder3() {
    Scalar q1 = Quantity.of(ComplexScalar.of(2, 3), "m");
    Scalar q2 = Quantity.of(ComplexScalar.of(-1, 7), "V");
    Scalar quc = q1.under(q2);
    assertTrue(quc instanceof Quantity);
    Scalar cdq = q2.divide(q1);
    assertTrue(cdq instanceof Quantity);
    Scalar qrc = q1.reciprocal().multiply(q2);
    assertTrue(qrc instanceof Quantity);
    assertEquals(quc, qrc);
    assertEquals(quc, cdq);
  }

  public void testUnder4() {
    Scalar q1 = Quantity.of(ComplexScalar.of(2, 3), "m");
    Scalar q2 = Quantity.of(ComplexScalar.of(-1, 7), "m");
    Scalar quc = q1.under(q2);
    assertTrue(quc instanceof ComplexScalar);
    Scalar cdq = q2.divide(q1);
    assertTrue(cdq instanceof ComplexScalar);
    Scalar qrc = q1.reciprocal().multiply(q2);
    assertTrue(qrc instanceof ComplexScalar);
    assertEquals(quc, qrc);
    assertEquals(quc, cdq);
  }

  public void testPlusQuantity() {
    Scalar c = ComplexScalar.of(2, 3);
    Scalar q = Quantity.of(0, "V");
    Scalar p = c.add(q);
    assertTrue(p instanceof ComplexScalar);
  }

  public void testPlusQuantityFail() {
    Scalar c = ComplexScalar.of(2, 3);
    Scalar q = Quantity.of(1, "V");
    try {
      c.add(q);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }

  public void testQuantityFail() {
    try {
      ComplexScalar.of(Quantity.of(3, "m"), RealScalar.ONE);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
    try {
      ComplexScalar.of(RealScalar.ONE, Quantity.of(3, "m"));
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }
}
